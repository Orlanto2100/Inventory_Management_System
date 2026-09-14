import {
  Dropdown,
  Table,
  Tag,
  Typography,
} from 'antd'
import type {
  MenuProps,
  TableColumnsType,
} from 'antd'
import { MoreOutlined } from '@ant-design/icons'

type RfqStatus =
  | 'DRAFT'
  | 'SENT'
  | 'OPEN'
  | 'RESPONSES_RECEIVED'
  | 'UNDER_REVIEW'
  | 'AWARDED'
  | 'CLOSED'
  | 'CANCELLED'

type Rfq = {
  key: string
  rfqNumber: string
  title: string
  vendors: number
  items: number
  responseDeadline: string
  status: RfqStatus
}

const rfqs: Rfq[] = [
  {
    key: '1',
    rfqNumber: 'RFQ-001',
    title: 'Office Furniture',
    vendors: 3,
    items: 5,
    responseDeadline: '15 Sep 2026',
    status: 'OPEN',
  },
  {
    key: '2',
    rfqNumber: 'RFQ-002',
    title: 'Printer Supplies',
    vendors: 4,
    items: 8,
    responseDeadline: '12 Sep 2026',
    status: 'UNDER_REVIEW',
  },
  {
    key: '3',
    rfqNumber: 'RFQ-003',
    title: 'Warehouse Equipment',
    vendors: 2,
    items: 3,
    responseDeadline: '05 Sep 2026',
    status: 'AWARDED',
  },
  {
    key: '4',
    rfqNumber: 'RFQ-004',
    title: 'Packaging Materials',
    vendors: 5,
    items: 6,
    responseDeadline: '20 Sep 2026',
    status: 'DRAFT',
  },
]

const statusConfig: Record<
  RfqStatus,
  {
    label: string
    color: string
  }
> = {
  DRAFT: {
    label: 'Draft',
    color: 'default',
  },
  SENT: {
    label: 'Sent',
    color: 'blue',
  },
  OPEN: {
    label: 'Open',
    color: 'green',
  },
  RESPONSES_RECEIVED: {
    label: 'Responses Received',
    color: 'gold',
  },
  UNDER_REVIEW: {
    label: 'Under Review',
    color: 'orange',
  },
  AWARDED: {
    label: 'Awarded',
    color: 'purple',
  },
  CLOSED: {
    label: 'Closed',
    color: 'default',
  },
  CANCELLED: {
    label: 'Cancelled',
    color: 'red',
  },
}

function getActionItems(
  record: Rfq,
): MenuProps['items'] {
  const items: MenuProps['items'] = [
    {
      key: 'view',
      label: 'View',
    },
  ]

  switch (record.status) {
    case 'DRAFT':
      items.push(
        {
          key: 'edit',
          label: 'Edit',
        },
        {
          key: 'send',
          label: 'Send RFQ',
        },
        {
          key: 'delete',
          label: 'Delete',
          danger: true,
        },
      )
      break

    case 'OPEN':
      items.push(
        {
          key: 'responses',
          label: 'View Responses',
        },
        {
          key: 'reminder',
          label: 'Send Reminder',
        },
        {
          key: 'close',
          label: 'Close RFQ',
        },
      )
      break

    case 'RESPONSES_RECEIVED':
    case 'UNDER_REVIEW':
      items.push({
        key: 'compare',
        label: 'Compare Quotations',
      })
      break

    case 'AWARDED':
      items.push({
        key: 'purchase-order',
        label: 'Create Purchase Order',
      })
      break
  }

  return items
}

const columns: TableColumnsType<Rfq> = [
  {
    title: 'RFQ No.',
    dataIndex: 'rfqNumber',
    key: 'rfqNumber',
  },
  {
    title: 'Title',
    dataIndex: 'title',
    key: 'title',
    render: (title: string) => (
      <Typography.Text strong>
        {title}
      </Typography.Text>
    ),
  },
  {
    title: 'Vendors',
    dataIndex: 'vendors',
    key: 'vendors',
  },
  {
    title: 'Items',
    dataIndex: 'items',
    key: 'items',
  },
  {
    title: 'Response Deadline',
    dataIndex: 'responseDeadline',
    key: 'responseDeadline',
  },
  {
    title: 'Status',
    dataIndex: 'status',
    key: 'status',
    render: (status: RfqStatus) => {
      const config = statusConfig[status]

      return (
        <Tag color={config.color}>
          {config.label}
        </Tag>
      )
    },
  },
  {
    title: 'Actions',
    key: 'actions',
    align: 'right',
    render: (_, record) => (
      <Dropdown
        trigger={['click']}
        menu={{
          items: getActionItems(record),
        }}
      >
        <MoreOutlined
          style={{
            fontSize: 20,
            cursor: 'pointer',
          }}
        />
      </Dropdown>
    ),
  },
]

function RfqTable() {
  return (
    <Table<Rfq>
      columns={columns}
      dataSource={rfqs}
      pagination={{
        pageSize: 10,
        showSizeChanger: false,
      }}
      scroll={{ x: 900 }}
    />
  )
}

export default RfqTable