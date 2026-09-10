import { useState } from 'react'
import {
  Avatar,
  Badge,
  Button,
  ConfigProvider,
  Drawer,
  Dropdown,
  Grid,
  Layout,
  Menu,
} from 'antd'
import {
  AppstoreOutlined,
  BellOutlined,
  DashboardOutlined,
  EnvironmentOutlined,
  FileTextOutlined,
  LogoutOutlined,
  MenuOutlined,
  SettingOutlined,
  ShopOutlined,
  ShoppingCartOutlined,
  SwapOutlined,
  UserOutlined,
} from '@ant-design/icons'
import {
  Outlet,
  useLocation,
  useNavigate,
} from 'react-router-dom'

const { Sider, Header, Content } = Layout
const { useBreakpoint } = Grid

const menuItems = [
  {
    key: '/',
    label: 'Dashboard',
    icon: <DashboardOutlined />,
  },
  {
    key: 'inventory',
    label: 'Inventory',
    icon: <AppstoreOutlined />,
    children: [
      {
        key: '/products',
        label: 'Products',
      },
      {
        key: '/inventory',
        label: 'Inventory',
      },
      {
        key: '/warehouses',
        label: 'Warehouses',
        icon: <ShopOutlined />,
      },
      {
        key: '/locations',
        label: 'Locations',
        icon: <EnvironmentOutlined />,
      },
    ],
  },
  {
    key: 'sales',
    label: 'Sales',
    icon: <ShoppingCartOutlined />,
    children: [
      {
        key: '/customers',
        label: 'Customers',
        icon: <UserOutlined />,
      },
      {
        key: '/sales-orders',
        label: 'Sales Orders',
        icon: <FileTextOutlined />,
      },
    ],
  },
  {
    key: 'purchasing',
    label: 'Purchasing',
    icon: <ShopOutlined />,
    children: [
      {
        key: '/vendors',
        label: 'Vendors',
        icon: <UserOutlined />,
      },
      {
        key: '/purchase-orders',
        label: 'Purchase Orders',
        icon: <FileTextOutlined />,
      },
    ],
  },
  {
    key: 'operations',
    label: 'Operations',
    icon: <SwapOutlined />,
    children: [
      {
        key: '/stock-movements',
        label: 'Stock Movements',
      },
    ],
  },
  {
    key: 'administration',
    label: 'Administration',
    icon: <UserOutlined />,
    children: [
      {
        key: '/users',
        label: 'Users',
      },
    ],
  },
]

const userMenuItems = [
  {
    key: 'profile',
    label: 'Profile',
    icon: <UserOutlined />,
  },
  {
    key: 'settings',
    label: 'Settings',
    icon: <SettingOutlined />,
  },
  {
    type: 'divider' as const,
  },
  {
    key: 'logout',
    label: 'Logout',
    icon: <LogoutOutlined />,
    danger: true,
  },
]

const theme = {
  token: {
    colorPrimary: '#3b6ea5',
    colorLink: '#3b6ea5',

    colorBgLayout: '#dfe4ea',
    colorBgContainer: '#f7f8fa',

    colorText: '#263238',
    colorTextSecondary: '#64748b',

    colorBorder: '#d1d7de',

    borderRadius: 6,
  },

  components: {
    Layout: {
      bodyBg: '#dfe4ea',
      headerBg: '#e5eaf0',
      siderBg: '#1f2937',
      headerHeight: 64,
    },

    Card: {
      colorBgContainer: '#f7f8fa',
    },

    Table: {
      colorBgContainer: '#f7f8fa',
      headerBg: '#eef1f4',
      borderColor: '#d1d7de',
    },

    Menu: {
      darkItemBg: '#1f2937',
      darkSubMenuItemBg: '#182230',
      darkItemColor: '#cbd5e1',
      darkItemHoverColor: '#ffffff',
      darkItemSelectedColor: '#ffffff',
      darkItemSelectedBg: '#334155',
      darkGroupTitleColor: '#94a3b8',
    },

    Button: {
      primaryShadow: 'none',
    },
  },
}

export default function MainLayout() {
  const navigate = useNavigate()
  const location = useLocation()
  const screens = useBreakpoint()

  const [collapsed, setCollapsed] = useState(false)
  const [drawerOpen, setDrawerOpen] = useState(false)

  const isMobile = !screens.md

  const handleNavigation = (key: string) => {
    navigate(key)

    if (isMobile) {
      setDrawerOpen(false)
    }
  }

  const getPageTitle = () => {
    const titles: Record<string, string> = {
      '/': 'Dashboard',
      '/products': 'Products',
      '/inventory': 'Inventory',
      '/warehouses': 'Warehouses',
      '/locations': 'Locations',
      '/customers': 'Customers',
      '/sales-orders': 'Sales Orders',
      '/vendors': 'Vendors',
      '/purchase-orders': 'Purchase Orders',
      '/stock-movements': 'Stock Movements',
      '/users': 'Users',
    }

    return titles[location.pathname] ?? 'Inventory System'
  }

  const menu = (
    <Menu
      theme="dark"
      mode="inline"
      items={menuItems}
      selectedKeys={[location.pathname]}
      onClick={({ key }) => handleNavigation(key)}
    />
  )

  return (
    <ConfigProvider theme={theme}>
      <Layout
        style={{
          minHeight: '100vh',
          width: '100%',
          background: '#dfe4ea',
        }}
      >
        {/* Mobile navigation */}
        {isMobile ? (
          <Drawer
            placement="left"
            closable={false}
            open={drawerOpen}
            onClose={() => setDrawerOpen(false)}
            width={260}
            styles={{
              body: {
                padding: 0,
                background: '#1f2937',
              },
            }}
          >
            <div
              style={{
                height: 64,
                display: 'flex',
                alignItems: 'center',
                padding: '0 16px',
                color: '#f8fafc',
                fontSize: 18,
                fontWeight: 600,
                background: '#1f2937',
                borderBottom: '1px solid #334155',
              }}
            >
              Inventory System
            </div>

            {menu}
          </Drawer>
        ) : (
          /* Desktop sidebar */
          <Sider
            collapsed={collapsed}
            trigger={null}
            width={240}
            collapsedWidth={80}
            style={{
              background: '#1f2937',
            }}
          >
            {/* Sidebar header */}
            <div
              style={{
                display: 'flex',
                alignItems: 'center',
                height: 64,
                padding: '0 16px',
                gap: 8,
                borderBottom: '1px solid #334155',
              }}
            >
              <Button
                type="text"
                icon={<MenuOutlined />}
                onClick={() =>
                  setCollapsed((value) => !value)
                }
                style={{
                  flexShrink: 0,
                  width: 40,
                  height: 40,
                  color: '#cbd5e1',
                  fontSize: 18,
                }}
              />

              {!collapsed && (
                <span
                  style={{
                    color: '#f8fafc',
                    fontSize: 18,
                    fontWeight: 600,
                    whiteSpace: 'nowrap',
                  }}
                >
                  Inventory System
                </span>
              )}
            </div>

            {menu}
          </Sider>
        )}

        <Layout
          style={{
            minHeight: '100vh',
            width: '100%',
            background: '#dfe4ea',
          }}
        >
          {/* Top header */}
          <Header
            style={{
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'space-between',
              height: 64,
              padding: '0 24px',
              background: '#e5eaf0',
              borderBottom: '1px solid #cbd3dc',
            }}
          >
            {/* Left side */}
            <div
              style={{
                display: 'flex',
                alignItems: 'center',
                gap: 12,
                minWidth: 0,
              }}
            >
              {isMobile && (
                <Button
                  type="text"
                  icon={<MenuOutlined />}
                  onClick={() => setDrawerOpen(true)}
                  style={{
                    width: 40,
                    height: 40,
                    fontSize: 18,
                    color: '#334155',
                  }}
                />
              )}

              <span
                style={{
                  fontSize: 20,
                  fontWeight: 600,
                  color: '#263238',
                  whiteSpace: 'nowrap',
                  overflow: 'hidden',
                  textOverflow: 'ellipsis',
                }}
              >
                {getPageTitle()}
              </span>
            </div>

            {/* Right side */}
            <div
              style={{
                display: 'flex',
                alignItems: 'center',
                gap: 12,
              }}
            >
              {/* Notifications */}
              <Badge count={3} size="small">
                <Button
                  type="text"
                  shape="circle"
                  icon={<BellOutlined />}
                  style={{
                    fontSize: 18,
                    color: '#475569',
                  }}
                />
              </Badge>

              {/* User menu */}
              <Dropdown
                menu={{
                  items: userMenuItems,
                  onClick: ({ key }) => {
                    if (key === 'logout') {
                      navigate('/login')
                    }
                  },
                }}
                trigger={['click']}
              >
                <Button
                  type="text"
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    gap: 8,
                    height: 48,
                    padding: '0 8px',
                    color: '#263238',
                  }}
                >
                  <Avatar
                    size={32}
                    icon={<UserOutlined />}
                    style={{
                      background: '#64748b',
                    }}
                  />

                  {!isMobile && (
                    <span
                      style={{
                        fontWeight: 500,
                        color: '#263238',
                      }}
                    >
                      Admin
                    </span>
                  )}
                </Button>
              </Dropdown>
            </div>
          </Header>

          {/* Main workspace */}
          <Content
            style={{
              padding: isMobile ? 16 : 24,
              overflow: 'auto',
              background: '#dfe4ea',
            }}
          >
            <Outlet />
          </Content>
        </Layout>
      </Layout>
    </ConfigProvider>
  )
}