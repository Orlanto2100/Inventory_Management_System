import { useState } from 'react'
import { Button, Flex, Typography } from 'antd'
import { PlusOutlined } from '@ant-design/icons'

import VendorFilters from '../components/VendorFilters'
import VendorTable from '../components/VendorTable'
import VendorFormDrawer from '../components/VendorFormDrawer'

const { Title, Text } = Typography

function VendorPage() {
  const [drawerOpen, setDrawerOpen] = useState(false)

  return (
    <Flex vertical gap={24}>
      <Flex
        justify="space-between"
        align="center"
        wrap
        gap={16}
      >
        <div>
          <Title level={2} style={{ margin: 0 }}>
            Vendors
          </Title>

          <Text type="secondary">
            Manage your suppliers and their contact information.
          </Text>
        </div>

        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => setDrawerOpen(true)}
        >
          Add Vendor
        </Button>
      </Flex>

      <VendorFilters />

      <VendorTable />

      <VendorFormDrawer
        open={drawerOpen}
        onClose={() => setDrawerOpen(false)}
      />
    </Flex>
  )
}

export default VendorPage