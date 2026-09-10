import { useState } from 'react'
import {
  Button,
  Card,
  Col,
  Form,
  Input,
  Modal,
  Row,
  Select,
  Space,
  Table,
  Tag,
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

type User = {
  id: number
  username: string
  email: string
  role: 'ADMIN' | 'MANAGER' | 'STAFF'
  status: 'Active' | 'Inactive'
}

type UserFormValues = {
  username: string
  email: string
  password?: string
  role: User['role']
  status: User['status']
}

const initialUsers: User[] = [
  {
    id: 1,
    username: 'admin',
    email: 'admin@example.com',
    role: 'ADMIN',
    status: 'Active',
  },
  {
    id: 2,
    username: 'manager',
    email: 'manager@example.com',
    role: 'MANAGER',
    status: 'Active',
  },
  {
    id: 3,
    username: 'staff01',
    email: 'staff01@example.com',
    role: 'STAFF',
    status: 'Active',
  },
  {
    id: 4,
    username: 'staff02',
    email: 'staff02@example.com',
    role: 'STAFF',
    status: 'Inactive',
  },
]

const roleOptions = [
  { label: 'Admin', value: 'ADMIN' },
  { label: 'Manager', value: 'MANAGER' },
  { label: 'Staff', value: 'STAFF' },
]

const statusOptions = [
  { label: 'Active', value: 'Active' },
  { label: 'Inactive', value: 'Inactive' },
]

export default function UserPage() {
  const [users, setUsers] = useState<User[]>(initialUsers)

  const [searchText, setSearchText] = useState('')

  const [roleFilter, setRoleFilter] =
    useState<User['role'] | undefined>(undefined)

  const [statusFilter, setStatusFilter] =
    useState<User['status'] | undefined>(undefined)

  const [modalOpen, setModalOpen] = useState(false)

  const [editingUser, setEditingUser] =
    useState<User | null>(null)

  const [form] =
    Form.useForm<UserFormValues>()

  const filteredUsers = users.filter((user) => {
    const search = searchText.toLowerCase().trim()

    const matchesSearch =
      !search ||
      user.username.toLowerCase().includes(search) ||
      user.email.toLowerCase().includes(search)

    const matchesRole =
      !roleFilter || user.role === roleFilter

    const matchesStatus =
      !statusFilter || user.status === statusFilter

    return matchesSearch && matchesRole && matchesStatus
  })

  const openCreateModal = () => {
    setEditingUser(null)

    form.resetFields()

    form.setFieldsValue({
      role: 'STAFF',
      status: 'Active',
    })

    setModalOpen(true)
  }

  const openEditModal = (user: User) => {
    setEditingUser(user)

    form.setFieldsValue({
      username: user.username,
      email: user.email,
      role: user.role,
      status: user.status,
      password: undefined,
    })

    setModalOpen(true)
  }

  const closeModal = () => {
    setModalOpen(false)
    setEditingUser(null)
    form.resetFields()
  }

  const handleSubmit = async () => {
    const values = await form.validateFields()

    if (editingUser) {
      setUsers((currentUsers) =>
        currentUsers.map((user) =>
          user.id === editingUser.id
            ? {
                ...user,
                username: values.username,
                email: values.email,
                role: values.role,
                status: values.status,
              }
            : user,
        ),
      )
    } else {
      const newUser: User = {
        id: Date.now(),
        username: values.username,
        email: values.email,
        role: values.role,
        status: values.status,
      }

      setUsers((currentUsers) => [
        ...currentUsers,
        newUser,
      ])
    }

    closeModal()
  }

  const handleDelete = (user: User) => {
    Modal.confirm({
      title: 'Delete user?',
      content: (
        <>
          Are you sure you want to delete{' '}
          <strong>{user.username}</strong>?
        </>
      ),
      okText: 'Delete',
      okType: 'danger',
      cancelText: 'Cancel',
      onOk: () => {
        setUsers((currentUsers) =>
          currentUsers.filter(
            (item) => item.id !== user.id,
          ),
        )
      },
    })
  }

  const columns: ColumnsType<User> = [
    {
      title: 'Username',
      dataIndex: 'username',
      key: 'username',
      sorter: (a, b) =>
        a.username.localeCompare(b.username),
      render: (username: string) => (
        <Text strong>{username}</Text>
      ),
    },
    {
      title: 'Email',
      dataIndex: 'email',
      key: 'email',
    },
    {
      title: 'Role',
      dataIndex: 'role',
      key: 'role',
      render: (role: User['role']) => {
        const label =
          role.charAt(0) +
          role.slice(1).toLowerCase()

        return <Tag>{label}</Tag>
      },
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      align: 'center',
      render: (status: User['status']) =>
        status === 'Active' ? (
          <Tag color="green">Active</Tag>
        ) : (
          <Tag>Inactive</Tag>
        ),
    },
    {
      title: 'Actions',
      key: 'actions',
      align: 'right',
      fixed: 'right',
      render: (_, user) => (
        <Space size="small">
          <Button
            type="text"
            icon={<EditOutlined />}
            onClick={() =>
              openEditModal(user)
            }
          />

          <Button
            type="text"
            danger
            icon={<DeleteOutlined />}
            onClick={() =>
              handleDelete(user)
            }
          />
        </Space>
      ),
    },
  ]

  return (
    <div>
      <div>
        <Title
          level={2}
          style={{
            margin: 0,
            color: '#263238',
          }}
        >
          Users
        </Title>

        <Text type="secondary">
          Manage users and their access roles.
        </Text>
      </div>

      <Card
        style={{
          marginTop: 24,
          background: '#f7f8fa',
        }}
      >
        <Row
          gutter={[12, 12]}
          align="middle"
          justify="space-between"
          style={{ marginBottom: 20 }}
        >
          <Col xs={24} lg={18}>
            <Row gutter={[8, 8]}>
              <Col xs={24} sm={12} md={10}>
                <Input
                  placeholder="Search users..."
                  prefix={<SearchOutlined />}
                  value={searchText}
                  onChange={(event) =>
                    setSearchText(
                      event.target.value,
                    )
                  }
                  allowClear
                />
              </Col>

              <Col xs={24} sm={6} md={6}>
                <Select
                  placeholder="Role"
                  value={roleFilter}
                  onChange={setRoleFilter}
                  allowClear
                  style={{ width: '100%' }}
                  options={roleOptions}
                />
              </Col>

              <Col xs={24} sm={6} md={5}>
                <Select
                  placeholder="Status"
                  value={statusFilter}
                  onChange={setStatusFilter}
                  allowClear
                  style={{ width: '100%' }}
                  options={statusOptions}
                />
              </Col>
            </Row>
          </Col>

          <Col
            xs={24}
            lg={6}
            style={{ textAlign: 'right' }}
          >
            <Button
              type="primary"
              icon={<PlusOutlined />}
              onClick={openCreateModal}
            >
              Add User
            </Button>
          </Col>
        </Row>

        <Table
          rowKey="id"
          columns={columns}
          dataSource={filteredUsers}
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) =>
              `Total ${total} users`,
          }}
          scroll={{ x: 800 }}
        />
      </Card>

      <Modal
        title={
          editingUser
            ? 'Edit User'
            : 'Add User'
        }
        open={modalOpen}
        onCancel={closeModal}
        onOk={handleSubmit}
        okText={
          editingUser
            ? 'Update'
            : 'Create'
        }
        destroyOnClose
      >
        <Form
          form={form}
          layout="vertical"
          style={{ marginTop: 24 }}
        >
          <Form.Item
            label="Username"
            name="username"
            rules={[
              {
                required: true,
                message:
                  'Please enter the username',
              },
            ]}
          >
            <Input placeholder="e.g. john.doe" />
          </Form.Item>

          <Form.Item
            label="Email"
            name="email"
            rules={[
              {
                required: true,
                message:
                  'Please enter the email',
              },
              {
                type: 'email',
                message:
                  'Please enter a valid email',
              },
            ]}
          >
            <Input placeholder="e.g. john@example.com" />
          </Form.Item>

          <Form.Item
            label={
              editingUser
                ? 'Password'
                : 'Password'
            }
            name="password"
            rules={[
              {
                required: !editingUser,
                message:
                  'Please enter the password',
              },
              {
                min: 6,
                message:
                  'Password must be at least 6 characters',
              },
            ]}
          >
            <Input.Password
              placeholder={
                editingUser
                  ? 'Leave blank to keep current password'
                  : 'Enter password'
              }
            />
          </Form.Item>

          <Form.Item
            label="Role"
            name="role"
            rules={[
              {
                required: true,
                message:
                  'Please select a role',
              },
            ]}
          >
            <Select
              placeholder="Select role"
              options={roleOptions}
            />
          </Form.Item>

          <Form.Item
            label="Status"
            name="status"
            rules={[
              {
                required: true,
                message:
                  'Please select the status',
              },
            ]}
          >
            <Select
              options={statusOptions}
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}