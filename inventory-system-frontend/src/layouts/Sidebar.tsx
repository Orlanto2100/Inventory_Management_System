import { useState } from 'react';
import { Button, ConfigProvider, Layout, Menu } from 'antd';
import { MenuOutlined } from '@ant-design/icons';
import { useNavigate, useLocation } from 'react-router-dom';

const { Sider } = Layout;

interface SidebarProps {
  menuItems: any[];
}

function Sidebar({ menuItems }: SidebarProps) {
  const [collapsed, setCollapsed] = useState(false);

  const navigate = useNavigate();
  const location = useLocation();

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
      {/* Header */}
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
          onClick={() => setCollapsed((value) => !value)}
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
          items={menuItems}
          selectedKeys={[location.pathname]}
          onClick={({ key }) => {
            navigate(key);
          }}
          style={{
            borderInlineEnd: 0,
          }}
        />
      </ConfigProvider>
    </Sider>
  );
}

export default Sidebar;