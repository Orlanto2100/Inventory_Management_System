import { Button, Popconfirm, Table, Tag } from 'antd'
import type { ColumnsType } from 'antd/es/table'
import type { User } from '../types/user'

interface UserTableProps {
  users: User[]
  loading: boolean
  onDelete: (id: number) => void
}

export default function UserTable({
  users,
  loading,
  onDelete,
}: UserTableProps) {
  const columns: ColumnsType<User> = [
    {
      title: 'Username',
      dataIndex: 'username',
      key: 'username',
    },
    {
      title: 'Full Name',
      dataIndex: 'fullName',
      key: 'fullName',
    },
    {
      title: 'Email',
      dataIndex: 'email',
      key: 'email',
      render: (email: string | null) =>
        email || '-',
    },
    {
      title: 'Account Type',
      dataIndex: 'accountType',
      key: 'accountType',
      render: (accountType) => (
        <Tag>{accountType}</Tag>
      ),
    },
    {
      title: 'Role',
      dataIndex: 'role',
      key: 'role',
      render: (role) =>
        role ? <Tag>{role}</Tag> : '-',
    },
    {
      title: 'Actions',
      key: 'actions',
      render: (_, user) => (
        <Popconfirm
          title="Delete this user?"
          description="This action cannot be undone."
          onConfirm={() => onDelete(user.id)}
          okText="Delete"
          cancelText="Cancel"
        >
          <Button danger size="small">
            Delete
          </Button>
        </Popconfirm>
      ),
    },
  ]

  return (
    <Table<User>
      rowKey="id"
      columns={columns}
      dataSource={users}
      loading={loading}
    />
  )
}