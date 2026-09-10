import { Card, Col, Row, Statistic, Typography } from 'antd'
import {
  AppstoreOutlined,
  ShoppingCartOutlined,
  TeamOutlined,
  WarningOutlined,
} from '@ant-design/icons'

const { Title, Text } = Typography

export default function DashboardPage() {
  return (
    <div>
      <Title level={2}>Dashboard</Title>
      <Text type="secondary">
        Overview of your inventory system.
      </Text>

      <Row gutter={[16, 16]} style={{ marginTop: 24 }}>
        <Col xs={24} sm={12} lg={6}>
          <Card>
            <Statistic
              title="Total Products"
              value={120}
              prefix={<AppstoreOutlined />}
            />
          </Card>
        </Col>

        <Col xs={24} sm={12} lg={6}>
          <Card>
            <Statistic
              title="Total Customers"
              value={32}
              prefix={<TeamOutlined />}
            />
          </Card>
        </Col>

        <Col xs={24} sm={12} lg={6}>
          <Card>
            <Statistic
              title="Total Orders"
              value={45}
              prefix={<ShoppingCartOutlined />}
            />
          </Card>
        </Col>

        <Col xs={24} sm={12} lg={6}>
          <Card>
            <Statistic
              title="Low Stock Items"
              value={8}
              prefix={<WarningOutlined />}
            />
          </Card>
        </Col>
      </Row>

      <Row gutter={[16, 16]} style={{ marginTop: 16 }}>
        <Col xs={24} lg={12}>
          <Card title="Recent Activity">
            <p>New product added: Cheeseburger</p>
            <p>Purchase order #1024 created</p>
            <p>Stock updated for Burger Bun</p>
            <p>New customer registered</p>
          </Card>
        </Col>

        <Col xs={24} lg={12}>
          <Card title="Low Stock">
            <p>Burger Bun — 5 remaining</p>
            <p>Cheese — 8 remaining</p>
            <p>Beef Patty — 3 remaining</p>
            <p>Lettuce — 6 remaining</p>
          </Card>
        </Col>
      </Row>
    </div>
  )
}