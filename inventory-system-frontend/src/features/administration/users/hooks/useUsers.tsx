import { useCallback, useEffect, useState } from 'react'
import { message } from 'antd'
import {
  createUser,
  deleteUser,
  getUsers,
} from '../../../../api/userApi'
import type {
  CreateUserRequest,
  User,
} from '../types/user'

export function useUsers() {
  const [users, setUsers] = useState<User[]>([])
  const [loading, setLoading] = useState(false)

  const loadUsers = useCallback(async () => {
    try {
      setLoading(true)

      const data = await getUsers()

      setUsers(data)
    } catch (error) {
      message.error(
        error instanceof Error
          ? error.message
          : 'Failed to load users',
      )
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    loadUsers()
  }, [loadUsers])

  const addUser = async (
    request: CreateUserRequest,
  ) => {
    try {
      await createUser(request)

      message.success(
        'User created successfully.',
      )

      await loadUsers()
    } catch (error) {
      message.error(
        error instanceof Error
          ? error.message
          : 'Failed to create user',
      )

      throw error
    }
  }

  const removeUser = async (id: number) => {
    try {
      await deleteUser(id)

      message.success(
        'User deleted successfully.',
      )

      await loadUsers()
    } catch (error) {
      message.error(
        error instanceof Error
          ? error.message
          : 'Failed to delete user',
      )
    }
  }

  return {
    users,
    loading,
    addUser,
    removeUser,
    reload: loadUsers,
  }
}