import { useEffect, useState } from 'react'
import { Card, Col, Row, Statistic, Typography, Alert, Spin } from 'antd'
import {
  AppstoreOutlined,
  EnvironmentOutlined,
  FileTextOutlined,
  SendOutlined,
  ShopOutlined,
  TeamOutlined,
  UserOutlined,
  ClockCircleOutlined,
  CheckCircleOutlined,
} from '@ant-design/icons'

import { getProducts } from '../../../api/productApi'
import { getCustomers } from '../../../api/customerApi'
import { getVendors } from '../../../api/vendorApi'
import { getWarehouses } from '../../../api/warehouseApi'
import { locationApi } from '../../../api/locationApi'
import { getUsers } from '../../../api/userApi'
import { purchaseRequestApi } from '../../../api/purchaseRequestApi'
import { getRfqs } from '../../../api/rfqApi'

const { Title, Text } = Typography

type DashboardCard = {
  title: string
  value: number
  icon: React.ReactNode
}

export default function DashboardPage() {
  const [cards, setCards] = useState<DashboardCard[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    async function loadDashboard() {
      try {
        setLoading(true)
        setError(null)

        const [
          products,
          customers,
          vendors,
          warehouses,
          locations,
          users,
          purchaseRequests,
          rfqDrafts,
          rfqSent,
          rfqResponsesReceived,
        ] = await Promise.all([
          getProducts(),

          getCustomers(),

          getVendors({
            page: 0,
            size: 1,
          }),

          getWarehouses({
            page: 0,
            size: 1,
          }),

          locationApi.getAll({
            page: 0,
            size: 1,
          }),

          getUsers(),

          purchaseRequestApi.getAll(),

          getRfqs({
            page: 0,
            size: 1,
            status: 'DRAFT',
          }),

          getRfqs({
            page: 0,
            size: 1,
            status: 'SENT',
          }),

          getRfqs({
            page: 0,
            size: 1,
            status: 'RESPONSES_RECEIVED',
          }),
        ])

        const pendingApprovalCount =
          purchaseRequests.filter(
            (request) =>
              request.status === 'PENDING_APPROVAL',
          ).length

        const processingCount =
          purchaseRequests.filter(
            (request) =>
              request.status === 'PROCESSING',
          ).length

        const dashboardCards: DashboardCard[] = [
          {
            title: 'Total Products',
            value: products.length,
            icon: <AppstoreOutlined />,
          },
          {
            title: 'Total Customers',
            value: customers.length,
            icon: <TeamOutlined />,
          },
          {
            title: 'Total Vendors',
            value: vendors.totalElements,
            icon: <ShopOutlined />,
          },
          {
            title: 'Total Warehouses',
            value: warehouses.totalElements,
            icon: <EnvironmentOutlined />,
          },
          {
            title: 'Total Locations',
            value: locations.totalElements,
            icon: <EnvironmentOutlined />,
          },
          {
            title: 'Total Users',
            value: users.length,
            icon: <UserOutlined />,
          },
          {
            title: 'Purchase Requests',
            value: purchaseRequests.length,
            icon: <FileTextOutlined />,
          },
          {
            title: 'Pending Purchase Requests',
            value: pendingApprovalCount,
            icon: <ClockCircleOutlined />,
          },
          {
            title: 'Processing Purchase Requests',
            value: processingCount,
            icon: <CheckCircleOutlined />,
          },
          {
            title: 'Draft RFQs',
            value: rfqDrafts.totalElements,
            icon: <FileTextOutlined />,
          },
          {
            title: 'Sent RFQs',
            value: rfqSent.totalElements,
            icon: <SendOutlined />,
          },
          {
            title: 'RFQs Awaiting Responses',
            value: rfqResponsesReceived.totalElements,
            icon: <ClockCircleOutlined />,
          },
        ]

        setCards(
          dashboardCards.filter(
            (card) => card.value > 1,
          ),
        )
      } catch (err) {
        setError(
          err instanceof Error
            ? err.message
            : 'Failed to load dashboard data.',
        )
      } finally {
        setLoading(false)
      }
    }

    loadDashboard()
  }, [])

  return (
    <div>
      <Title level={2}>Dashboard</Title>

      <Text type="secondary">
        Overview of your inventory system.
      </Text>

      {error && (
        <Alert
          type="error"
          message="Failed to load dashboard"
          description={error}
          showIcon
          style={{ marginTop: 24 }}
        />
      )}

      {loading ? (
        <div
          style={{
            display: 'flex',
            justifyContent: 'center',
            padding: '48px 0',
          }}
        >
          <Spin size="large" />
        </div>
      ) : (
        <Row
          gutter={[16, 16]}
          style={{ marginTop: 24 }}
        >
          {cards.map((card) => (
            <Col
              xs={24}
              sm={12}
              lg={6}
              key={card.title}
            >
              <Card>
                <Statistic
                  title={card.title}
                  value={card.value}
                  prefix={card.icon}
                />
              </Card>
            </Col>
          ))}
        </Row>
      )}
    </div>
  )
}
