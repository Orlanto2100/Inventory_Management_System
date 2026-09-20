import { useMemo, useState } from 'react'
import { Button, Card, Flex, Typography } from 'antd'
import { PlusOutlined } from '@ant-design/icons'

import UserFilters from '../components/UserFilters'
import UserTable from '../components/UserTable'
import UserFormModal from '../components/UserFormModal'
import { useUsers } from '../hooks/useUsers'

import type {
  AccountType,
  CreateUserRequest,
  Role,
} from '../types/user'

const { Title } = Typography

export default function UserPage() {
  const {
    users,
    loading,
    addUser,
    removeUser,
  } = useUsers()

  const [search, setSearch] = useState('')
  const [accountType, setAccountType] =
    useState<AccountType | undefined>()

  const [role, setRole] =
    useState<Role | undefined>()

  const [modalOpen, setModalOpen] =
    useState(false)

  const filteredUsers = useMemo(() => {
    const searchValue =
      search.trim().toLowerCase()

    return users.filter((user) => {
      const matchesSearch =
        !searchValue ||
        user.username
          .toLowerCase()
          .includes(searchValue) ||
        user.fullName
          .toLowerCase()
          .includes(searchValue) ||
        user.email
          ?.toLowerCase()
          .includes(searchValue)

      const matchesAccountType =
        !accountType ||
        user.accountType === accountType

      const matchesRole =
        !role ||
        user.role === role

      return (
        matchesSearch &&
        matchesAccountType &&
        matchesRole
      )
    })
  }, [
    users,
    search,
    accountType,
    role,
  ])

  const handleCreateUser = async (
    values: CreateUserRequest,
  ) => {
    await addUser(values)
    setModalOpen(false)
  }

  return (
    <Card>
      <Flex
        justify="space-between"
        align="center"
        style={{ marginBottom: 24 }}
      >
        <Title
          level={3}
          style={{ margin: 0 }}
        >
          Users
        </Title>

        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => setModalOpen(true)}
        >
          Create User
        </Button>
      </Flex>

      <UserFilters
        search={search}
        accountType={accountType}
        role={role}
        onSearchChange={setSearch}
        onAccountTypeChange={setAccountType}
        onRoleChange={setRole}
      />

      <UserTable
        users={filteredUsers}
        loading={loading}
        onDelete={removeUser}
      />

      <UserFormModal
        open={modalOpen}
        loading={loading}
        onCancel={() => setModalOpen(false)}
        onSubmit={handleCreateUser}
      />
    </Card>
  )
}