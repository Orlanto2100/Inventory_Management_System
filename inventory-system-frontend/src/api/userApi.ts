import { apiRequest } from '../api/client'
import type {
  CreateUserRequest,
  User,
} from '../features/administration/users/types/user'

export function getUsers(): Promise<User[]> {
  return apiRequest<User[]>('/users')
}

export function createUser(
  request: CreateUserRequest,
): Promise<User> {
  return apiRequest<User>('/users', {
    method: 'POST',
    body: JSON.stringify(request),
  })
}

export function deleteUser(
  id: number,
): Promise<void> {
  return apiRequest<void>(`/users/${id}`, {
    method: 'DELETE',
  })
}