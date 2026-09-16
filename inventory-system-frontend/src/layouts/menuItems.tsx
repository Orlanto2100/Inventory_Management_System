import {
  AppstoreOutlined,
  DashboardOutlined,
  EnvironmentOutlined,
  FileTextOutlined,
  LogoutOutlined,
  SettingOutlined,
  ShopOutlined,
  ShoppingCartOutlined,
  SwapOutlined,
  UserOutlined,
} from '@ant-design/icons'

export const menuItems = [
  // Dashboard
  {
    key: '/',
    label: 'navigation.dashboard',
    icon: <DashboardOutlined />,
  },

  // Inventory
  {
    key: 'inventory',
    label: 'navigation.inventory',
    icon: <AppstoreOutlined />,
    children: [
      {
        key: '/products',
        label: 'navigation.products',
      },
      {
        key: '/inventory',
        label: 'navigation.inventory',
      },
      {
        key: '/warehouses',
        label: 'navigation.warehouses',
        icon: <ShopOutlined />,
      },
      {
        key: '/locations',
        label: 'navigation.locations',
        icon: <EnvironmentOutlined />,
      },
    ],
  },

  // Sales
  {
    key: 'sales',
    label: 'navigation.sales',
    icon: <ShoppingCartOutlined />,
    children: [
      {
        key: '/customers',
        label: 'navigation.customers',
        icon: <UserOutlined />,
      },
      {
        key: '/customer-quotations',
        label: 'navigation.customerQuotations',
        icon: <FileTextOutlined />,
      },
      {
        key: '/sales-orders',
        label: 'navigation.salesOrders',
        icon: <FileTextOutlined />,
      },
      {
        key: '/deliveries',
        label: 'navigation.deliveries',
        icon: <SwapOutlined />,
      },
      {
        key: '/customer-invoices',
        label: 'navigation.customerInvoices',
        icon: <FileTextOutlined />,
      },
    ],
  },

  // Purchasing
  {
    key: 'purchasing',
    label: 'navigation.purchasing',
    icon: <ShopOutlined />,
    children: [
      {
        key: '/vendors',
        label: 'navigation.vendors',
        icon: <UserOutlined />,
      },
      {
        key: '/purchase-requests',
        label: 'navigation.purchaseRequests',
        icon: <FileTextOutlined />,
      },
      {
        key: '/rfqs',
        label: 'navigation.rfqs',
        icon: <FileTextOutlined />,
      },
      {
        key: '/vendor-quotations',
        label: 'navigation.vendorQuotations',
        icon: <FileTextOutlined />,
      },
      {
        key: '/purchase-orders',
        label: 'navigation.purchaseOrders',
        icon: <FileTextOutlined />,
      },
      {
        key: '/receipts',
        label: 'navigation.receipts',
        icon: <SwapOutlined />,
      },
      {
        key: '/vendor-invoices',
        label: 'navigation.vendorInvoices',
        icon: <FileTextOutlined />,
      },
    ],
  },

  // Operations
  {
    key: 'operations',
    label: 'navigation.operations',
    icon: <SwapOutlined />,
    children: [
      {
        key: '/stock-movements',
        label: 'navigation.stockMovements',
      },
    ],
  },

  // Administration
  {
    key: 'administration',
    label: 'navigation.administration',
    icon: <UserOutlined />,
    children: [
      {
        key: '/users',
        label: 'navigation.users',
      },
    ],
  },
]

export const userMenuItems = [
  {
    key: 'profile',
    label: 'common.profile',
    icon: <UserOutlined />,
  },

  {
    key: 'settings',
    label: 'common.settings',
    icon: <SettingOutlined />,
  },

  {
    type: 'divider' as const,
  },

  {
    key: 'logout',
    label: 'common.logout',
    icon: <LogoutOutlined />,
    danger: true,
  },
]