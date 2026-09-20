export type AccountType =
  | 'COMPANY'
  | 'VENDOR'
  | 'CUSTOMER'

export type Role =
  | 'ADMIN'
  | 'WAREHOUSE_STAFF'
  | 'PURCHASING_STAFF'
  | 'SALES_STAFF'

export interface User {
  id: number
  username: string
  fullName: string
  email: string | null
  accountType: AccountType
  role: Role | null
}

export interface CreateUserRequest {
  username: string
  password: string
  fullName: string
  email?: string | null
  accountType: AccountType
  role?: Role | null
}