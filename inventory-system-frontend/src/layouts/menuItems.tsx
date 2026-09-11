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
} from '@ant-design/icons';

export const menuItems = [
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
];

export const userMenuItems = [
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
];