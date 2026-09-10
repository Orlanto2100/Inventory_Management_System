import { useState } from 'react'
import {
  Button,
  Card,
  Col,
  Form,
  Input,
  Modal,
  Row,
  Space,
  Table,
  Typography,
} from 'antd'
import {
  DeleteOutlined,
  EditOutlined,
  PlusOutlined,
  SearchOutlined,
} from '@ant-design/icons'
import type { ColumnsType } from 'antd/es/table'

const { Title, Text } = Typography

type Customer = {
  id: number
  name: string
  phone: string
  email: string
  address: string
}

type CustomerFormValues = {
  name: string
  phone: string
  email: string
  address: string
}

const initialCustomers: Customer[] = [
  {
    id: 1,
    name: 'John Doe',
    phone: '09123456789',
    email: 'john@example.com',
    address: 'Yangon',
  },
  {
    id: 2,
    name: 'ABC Store',
    phone: '09876543210',
    email: 'abcstore@example.com',
    address: 'Mandalay',
  },
  {
    id: 3,
    name: 'Michael Trading',
    phone: '09234567890',
    email: 'michael@example.com',
    address: 'Naypyidaw',
  },
  {
    id: 4,
    name: 'Golden Food',
    phone: '09555555555',
    email: '',
    address: 'Bago',
  },
]

export default function CustomerPage() {
  const [customers, setCustomers] =
    useState<Customer[]>(initialCustomers)

  const [searchText, setSearchText] = useState('')

  const [modalOpen, setModalOpen] = useState(false)

  const [editingCustomer, setEditingCustomer] =
    useState<Customer | null>(null)

  const [form] =
    Form.useForm<CustomerFormValues>()

  const filteredCustomers =
    customers.filter((customer) => {
      const search =
        searchText.toLowerCase().trim()

      if (!search) {
        return true
      }

      return (
        customer.name
          .toLowerCase()
          .includes(search) ||
        customer.phone
          .toLowerCase()
          .includes(search) ||
        customer.email
          .toLowerCase()
          .includes(search) ||
        customer.address
          .toLowerCase()
          .includes(search)
      )
    })

  const openCreateModal = () => {
    setEditingCustomer(null)

    form.resetFields()

    setModalOpen(true)
  }

  const openEditModal = (
    customer: Customer,
  ) => {
    setEditingCustomer(customer)

    form.setFieldsValue({
      name: customer.name,
      phone: customer.phone,
      email: customer.email,
      address: customer.address,
    })

    setModalOpen(true)
  }

  const closeModal = () => {
    setModalOpen(false)
    setEditingCustomer(null)
    form.resetFields()
  }

  const handleSubmit = async () => {
    const values =
      await form.validateFields()

    if (editingCustomer) {
      setCustomers(
        (currentCustomers) =>
          currentCustomers.map(
            (customer) =>
              customer.id ===
              editingCustomer.id
                ? {
                    ...customer,
                    ...values,
                  }
                : customer,
          ),
      )
    } else {
      const newCustomer: Customer = {
        id: Date.now(),
        ...values,
      }

      setCustomers(
        (currentCustomers) => [
          ...currentCustomers,
          newCustomer,
        ],
      )
    }

    closeModal()
  }

  const handleDelete = (
    customer: Customer,
  ) => {
    Modal.confirm({
      title: 'Delete customer?',
      content: (
        <>
          Are you sure you want to delete{' '}
          <strong>
            {customer.name}
          </strong>
          ?
        </>
      ),
      okText: 'Delete',
      okType: 'danger',
      cancelText: 'Cancel',
      onOk: () => {
        setCustomers(
          (currentCustomers) =>
            currentCustomers.filter(
              (item) =>
                item.id !==
                customer.id,
            ),
        )
      },
    })
  }

  const columns: ColumnsType<Customer> = [
    {
      title: 'Customer',
      dataIndex: 'name',
      key: 'name',
      sorter: (a, b) =>
        a.name.localeCompare(b.name),
      render: (name: string) => (
        <Text strong>
          {name}
        </Text>
      ),
    },
    {
      title: 'Phone',
      dataIndex: 'phone',
      key: 'phone',
    },
    {
      title: 'Email',
      dataIndex: 'email',
      key: 'email',
      render: (email: string) =>
        email || '—',
    },
    {
      title: 'Address',
      dataIndex: 'address',
      key: 'address',
    },
    {
      title: 'Actions',
      key: 'actions',
      align: 'right',
      fixed: 'right',
      render: (_, customer) => (
        <Space size="small">
          <Button
            type="text"
            icon={<EditOutlined />}
            onClick={() =>
              openEditModal(customer)
            }
          />

          <Button
            type="text"
            danger
            icon={<DeleteOutlined />}
            onClick={() =>
              handleDelete(customer)
            }
          />
        </Space>
      ),
    },
  ]

  return (
    <div>
      {/* Page heading */}
      <div>
        <Title
          level={2}
          style={{
            margin: 0,
            color: '#263238',
          }}
        >
          Customers
        </Title>

        <Text type="secondary">
          Manage customers in your
          inventory system.
        </Text>
      </div>

      {/* Customer table */}
      <Card
        style={{
          marginTop: 24,
          background: '#f7f8fa',
        }}
      >
        {/* Toolbar */}
        <Row
          gutter={[12, 12]}
          align="middle"
          justify="space-between"
          style={{
            marginBottom: 20,
          }}
        >
          <Col
            xs={24}
            lg={18}
          >
            <Input
              placeholder="Search customers..."
              prefix={
                <SearchOutlined />
              }
              value={searchText}
              onChange={(event) =>
                setSearchText(
                  event.target.value,
                )
              }
              allowClear
              style={{
                maxWidth: 400,
              }}
            />
          </Col>

          <Col
            xs={24}
            lg={6}
            style={{
              textAlign: 'right',
            }}
          >
            <Button
              type="primary"
              icon={<PlusOutlined />}
              onClick={
                openCreateModal
              }
            >
              Add Customer
            </Button>
          </Col>
        </Row>

        {/* Table */}
        <Table
          rowKey="id"
          columns={columns}
          dataSource={
            filteredCustomers
          }
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) =>
              `Total ${total} customers`,
          }}
          scroll={{
            x: 700,
          }}
        />
      </Card>

      {/* Create / Edit modal */}
      <Modal
        title={
          editingCustomer
            ? 'Edit Customer'
            : 'Add Customer'
        }
        open={modalOpen}
        onCancel={closeModal}
        onOk={handleSubmit}
        okText={
          editingCustomer
            ? 'Update'
            : 'Create'
        }
        destroyOnHidden
      >
        <Form
          form={form}
          layout="vertical"
          style={{
            marginTop: 24,
          }}
        >
          <Form.Item
            label="Customer Name"
            name="name"
            rules={[
              {
                required: true,
                message:
                  'Please enter the customer name',
              },
            ]}
          >
            <Input
              placeholder="e.g. John Doe"
            />
          </Form.Item>

          <Form.Item
            label="Phone"
            name="phone"
            rules={[
              {
                required: true,
                message:
                  'Please enter the phone number',
              },
            ]}
          >
            <Input
              placeholder="e.g. 09123456789"
            />
          </Form.Item>

          <Form.Item
            label="Email"
            name="email"
            rules={[
              {
                type: 'email',
                message:
                  'Please enter a valid email address',
              },
            ]}
          >
            <Input
              placeholder="e.g. john@example.com"
            />
          </Form.Item>

          <Form.Item
            label="Address"
            name="address"
            rules={[
              {
                required: true,
                message:
                  'Please enter the address',
              },
            ]}
          >
            <Input.TextArea
              rows={3}
              placeholder="Enter customer address"
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}