import { useMemo, useState } from 'react'
import {
  Button,
  ConfigProvider,
  Layout,
  Menu,
} from 'antd'
import { MenuOutlined } from '@ant-design/icons'
import {
  useLocation,
  useNavigate,
} from 'react-router-dom'
import { useTranslation } from 'react-i18next'

const { Sider } = Layout

interface MenuItem {
  key: string
  label: string
  icon?: React.ReactNode
}

interface MenuSection extends MenuItem {
  children: MenuItem[]
}

type SidebarMenuItem = MenuItem | MenuSection

interface SidebarProps {
  menuItems: SidebarMenuItem[]
}

function Sidebar({ menuItems }: SidebarProps) {
  const [collapsed, setCollapsed] = useState(false)

  const navigate = useNavigate()
  const location = useLocation()
  const { t } = useTranslation()

  const translatedMenuItems = useMemo(() => {
    return menuItems.map((item) => {
      if ('children' in item) {
        return {
          ...item,
          label: t(item.label),
          children: item.children.map((child) => ({
            ...child,
            label: t(child.label),
          })),
        }
      }

      return {
        ...item,
        label: t(item.label),
      }
    })
  }, [menuItems, t])

  const openKeys = useMemo(() => {
    return menuItems
      .filter(
        (item): item is MenuSection =>
          'children' in item &&
          item.children.some(
            (child) =>
              child.key === location.pathname,
          ),
      )
      .map((item) => item.key)
  }, [menuItems, location.pathname])

  return (
    <Sider
      collapsed={collapsed}
      trigger={null}
      width={240}
      collapsedWidth={80}
      style={{
        background: '#1f2937',
      }}
    >
      {/* Sidebar Header */}
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
            {t('common.inventorySystem')}
          </span>
        )}
      </div>

      {/* Navigation */}
      <ConfigProvider
        theme={{
          components: {
            Menu: {
              darkItemBg: '#1f2937',
              darkItemColor: '#cbd5e1',
              darkItemHoverColor: '#ffffff',
              darkItemHoverBg: '#273449',
              darkItemSelectedColor: '#ffffff',
              darkItemSelectedBg: '#334155',
              darkSubMenuItemBg: '#1f2937',
            },
          },
        }}
      >
        <Menu
          mode="inline"
          theme="dark"
          items={translatedMenuItems}
          selectedKeys={[location.pathname]}
          defaultOpenKeys={openKeys}
          onClick={({ key }) => navigate(key)}
          style={{
            borderInlineEnd: 0,
          }}
        />
      </ConfigProvider>
    </Sider>
  )
}

export default Sidebar