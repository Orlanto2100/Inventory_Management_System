import { useState } from 'react'
import { useTranslation } from 'react-i18next'
import {
  ConfigProvider,
  Grid,
  Layout,
} from 'antd'
import {
  Outlet,
  useLocation,
  useNavigate,
} from 'react-router-dom'

import Sidebar from './Sidebar'
import MobileSidebar from './MobileSidebar'
import AppHeader from './Header'

import {
  menuItems,
  userMenuItems,
} from './menuItems'

import { getPageTitle } from './pageTitles'

const { Content } = Layout
const { useBreakpoint } = Grid

function MainLayout() {
  const screens = useBreakpoint()
  const isMobile = !screens.lg

  const [drawerOpen, setDrawerOpen] = useState(false)

  const navigate = useNavigate()
  const location = useLocation()
  const { t } = useTranslation()

  const pageTitle = t(
    getPageTitle(location.pathname),
  )

  return (
    <ConfigProvider
      theme={{
        token: {
          colorPrimary: '#334155',
        },
      }}
    >
      <Layout
        style={{
          minHeight: '100vh',
        }}
      >
        {isMobile ? (
          <MobileSidebar
            open={drawerOpen}
            setOpen={setDrawerOpen}
            menuItems={menuItems}
          />
        ) : (
          <Sidebar menuItems={menuItems} />
        )}

        <Layout
          style={{
            minWidth: 0,
            minHeight: '100vh',
            background: '#dfe4ea',
          }}
        >
          <AppHeader
            isMobile={isMobile}
            setDrawerOpen={setDrawerOpen}
            pageTitle={pageTitle}
            userMenuItems={userMenuItems}
            navigate={navigate}
          />

          <Content
            style={{
              padding: 24,
              minWidth: 0,
              flex: 1,
            }}
          >
            <Outlet />
          </Content>
        </Layout>
      </Layout>
    </ConfigProvider>
  )
}

export default MainLayout