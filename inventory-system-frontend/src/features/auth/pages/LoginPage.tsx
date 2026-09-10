import { useState } from 'react'
import {
  Button,
  Card,
  Form,
  Input,
  Typography,
  message,
} from 'antd'
import {
  LockOutlined,
  UserOutlined,
} from '@ant-design/icons'

const { Title, Text } = Typography

type LoginFormValues = {
  username: string
  password: string
}

export default function LoginPage() {
  const [loading, setLoading] = useState(false)

  const handleLogin = async (values: LoginFormValues) => {
    try {
      setLoading(true)

      // Backend login API will be connected here later.
      console.log('Login values:', values)

      // Temporary simulation of login request.
      await new Promise((resolve) =>
        setTimeout(resolve, 800),
      )

      message.success('Login successful.')
    } catch {
      message.error('Login failed.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div
      style={{
        minHeight: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        background: '#f5f7fa',
        padding: 24,
      }}
    >
      <Card
        style={{
          width: '100%',
          maxWidth: 420,
        }}
        styles={{
          body: {
            padding: 40,
          },
        }}
      >
        {/* Header */}
        <div
          style={{
            textAlign: 'center',
            marginBottom: 32,
          }}
        >
          <Title
            level={2}
            style={{
              marginBottom: 8,
              color: '#263238',
            }}
          >
            Inventory Management System
          </Title>

          <Text type="secondary">
            Sign in to continue
          </Text>
        </div>

        {/* Login Form */}
        <Form<LoginFormValues>
          layout="vertical"
          onFinish={handleLogin}
          autoComplete="off"
        >
          {/* Username */}
          <Form.Item
            label="Username"
            name="username"
            rules={[
              {
                required: true,
                message: 'Please enter your username',
              },
            ]}
          >
            <Input
              size="large"
              prefix={<UserOutlined />}
              placeholder="Enter your username"
            />
          </Form.Item>

          {/* Password */}
          <Form.Item
            label="Password"
            name="password"
            rules={[
              {
                required: true,
                message: 'Please enter your password',
              },
            ]}
          >
            <Input.Password
              size="large"
              prefix={<LockOutlined />}
              placeholder="Enter your password"
            />
          </Form.Item>

          {/* Submit */}
          <Form.Item style={{ marginBottom: 0 }}>
            <Button
              type="primary"
              htmlType="submit"
              size="large"
              loading={loading}
              block
            >
              Sign In
            </Button>
          </Form.Item>
        </Form>

        {/* Footer */}
        <div
          style={{
            textAlign: 'center',
            marginTop: 24,
          }}
        >
          <Text type="secondary">
            Inventory Management System
          </Text>
        </div>
      </Card>
    </div>
  )
}