import { ConfigProvider, Drawer, Menu } from 'antd'
import { useLocation, useNavigate } from 'react-router-dom'
import { useTranslation } from 'react-i18next'

interface MenuItem {
  key: string
  label: string
  icon?: React.ReactNode
}

interface MenuSection extends MenuItem {
  children: MenuItem[]
}

type SidebarMenuItem = MenuItem | MenuSection

interface MobileSidebarProps {
  open: boolean
  setOpen: (open: boolean) => void
  menuItems: SidebarMenuItem[]
}

function MobileSidebar({
  open,
  setOpen,
  menuItems,
}: MobileSidebarProps) {
  const navigate = useNavigate()
  const location = useLocation()
  const { t } = useTranslation()

  const translatedMenuItems = menuItems.map((item) => {
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

  const handleMenuClick = ({
    key,
  }: {
    key: string
  }) => {
    navigate(key)
    setOpen(false)
  }

  return (
    <Drawer
      placement="left"
      open={open}
      onClose={() => setOpen(false)}
      closable={false}
      width={240}
      styles={{
        body: {
          padding: 0,
          background: '#1f2937',
        },
      }}
    >
      <div
        style={{
          display: 'flex',
          alignItems: 'center',
          height: 64,
          padding: '0 16px',
          color: '#f8fafc',
          fontSize: 18,
          fontWeight: 600,
          borderBottom: '1px solid #334155',
        }}
      >
        {t('common.inventorySystem')}
      </div>

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
          onClick={handleMenuClick}
          style={{
            borderInlineEnd: 0,
          }}
        />
      </ConfigProvider>
    </Drawer>
  )
}

export default MobileSidebar
