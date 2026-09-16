import {
  Avatar,
  Badge,
  Button,
  Dropdown,
  Layout,
  Select,
} from 'antd'

import {
  BellOutlined,
  MenuOutlined,
  UserOutlined,
} from '@ant-design/icons'

import { useTranslation } from 'react-i18next'

const { Header: AntHeader } = Layout

interface HeaderProps {
  isMobile: boolean
  setDrawerOpen: (open: boolean) => void
  pageTitle: string
  userMenuItems: any[]
  navigate: (path: string) => void
}

function Header({
  isMobile,
  setDrawerOpen,
  pageTitle,
  userMenuItems,
  navigate,
}: HeaderProps) {
  const { i18n } = useTranslation()

  const handleLanguageChange = (language: string) => {
    i18n.changeLanguage(language)
  }

  return (
    <AntHeader
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
          {pageTitle}
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

        <Select
          value={i18n.language}
          onChange={handleLanguageChange}
          style={{ width: 110 }}
          options={[
            { label: 'English', value: 'en' },
            { label: 'မြန်မာ', value: 'my' },
          ]}
        />

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
    </AntHeader>
  )
}

export default Header