import { useState } from 'react'
import { Button, Flex, Typography } from 'antd'
import { PlusOutlined } from '@ant-design/icons'

import RfqFilters from '../components/RfqFilters'
import RfqTable from '../components/RfqTable'
import RfqFormDrawer from '../components/RfqFormDrawer'

const { Title, Text } = Typography

function RfqPage() {
  const [drawerOpen, setDrawerOpen] = useState(false)

  return (
    <Flex vertical gap={24}>
      {/* Page header */}
      <Flex
        justify="space-between"
        align="center"
        wrap
        gap={16}
      >
        <div>
          <Title level={2} style={{ margin: 0 }}>
            Requests for Quotation
          </Title>

          <Text type="secondary">
            Create and manage requests sent to vendors.
          </Text>
        </div>

        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={() => setDrawerOpen(true)}
        >
          Create RFQ
        </Button>
      </Flex>

      {/* Filters */}
      <RfqFilters />

      {/* RFQ table */}
      <RfqTable />

      {/* Create RFQ drawer */}
      <RfqFormDrawer
        open={drawerOpen}
        onClose={() => setDrawerOpen(false)}
      />
    </Flex>
  )
}

export default RfqPage