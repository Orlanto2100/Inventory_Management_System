import {
  Form,
  Input,
  Modal,
  Select,
} from 'antd'
import { useEffect } from 'react'
import type {
  AccountType,
  CreateUserRequest,
  Role,
} from '../types/user'

interface UserFormModalProps {
  open: boolean
  loading?: boolean
  onCancel: () => void
  onSubmit: (
    values: CreateUserRequest,
  ) => Promise<void>
}

export default function UserFormModal({
  open,
  loading = false,
  onCancel,
  onSubmit,
}: UserFormModalProps) {
  const [form] =
    Form.useForm<CreateUserRequest>()

  const accountType =
    Form.useWatch('accountType', form)

  useEffect(() => {
    if (!open) {
      form.resetFields()
    }
  }, [open, form])

  const handleFinish = async (
    values: CreateUserRequest,
  ) => {
    await onSubmit({
      ...values,
      role:
        values.accountType === 'COMPANY'
          ? values.role
          : null,
    })

    form.resetFields()
  }

  return (
    <Modal
      title="Create User"
      open={open}
      onCancel={onCancel}
      onOk={() => form.submit()}
      confirmLoading={loading}
      destroyOnHidden
    >
      <Form
        form={form}
        layout="vertical"
        onFinish={handleFinish}
      >
        <Form.Item
          label="Username"
          name="username"
          rules={[
            {
              required: true,
              message: 'Username is required',
            },
            {
              max: 50,
              message:
                'Username must not exceed 50 characters',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Password"
          name="password"
          rules={[
            {
              required: true,
              message: 'Password is required',
            },
            {
              min: 8,
              message:
                'Password must be at least 8 characters',
            },
          ]}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item
          label="Full Name"
          name="fullName"
          rules={[
            {
              required: true,
              message: 'Full name is required',
            },
            {
              max: 100,
              message:
                'Full name must not exceed 100 characters',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Email"
          name="email"
          rules={[
            {
              type: 'email',
              message: 'Invalid email format',
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Account Type"
          name="accountType"
          rules={[
            {
              required: true,
              message:
                'Account type is required',
            },
          ]}
        >
          <Select<AccountType>
            options={[
              {
                label: 'Company',
                value: 'COMPANY',
              },
              {
                label: 'Vendor',
                value: 'VENDOR',
              },
              {
                label: 'Customer',
                value: 'CUSTOMER',
              },
            ]}
          />
        </Form.Item>

        {accountType === 'COMPANY' && (
          <Form.Item
            label="Role"
            name="role"
            rules={[
              {
                required: true,
                message:
                  'Role is required for company users',
              },
            ]}
          >
            <Select<Role>
              options={[
                {
                  label: 'Admin',
                  value: 'ADMIN',
                },
                {
                  label: 'Warehouse Staff',
                  value: 'WAREHOUSE_STAFF',
                },
                {
                  label: 'Purchasing Staff',
                  value: 'PURCHASING_STAFF',
                },
                {
                  label: 'Sales Staff',
                  value: 'SALES_STAFF',
                },
              ]}
            />
          </Form.Item>
        )}
      </Form>
    </Modal>
  )
}