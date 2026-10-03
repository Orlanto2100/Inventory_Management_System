import {
  Descriptions,
  Divider,
  Modal,
  Table,
  Tag,
} from 'antd'
import type { ColumnsType } from 'antd/es/table'
import type { PurchaseRequestResponse } from '../../../../api/purchaseRequestApi'

interface PurchaseRequestDetailsProps {
  open: boolean
  purchaseRequest: PurchaseRequestResponse | null
  onClose: () => void
}

const statusColors: Record<
  PurchaseRequestResponse['status'],
  string
> = {
  DRAFT: 'default',
  PENDING_APPROVAL: 'gold',
  APPROVED: 'blue',
  REJECTED: 'red',
  PROCESSING: 'purple',
  COMPLETED: 'green',
}

const formatStatus = (
  status: PurchaseRequestResponse['status']
) => {
  return status.replaceAll('_', ' ')
}

export default function PurchaseRequestDetails({
  open,
  purchaseRequest,
  onClose,
}: PurchaseRequestDetailsProps) {
  if (!purchaseRequest) {
    return null
  }

  const columns: ColumnsType<
    PurchaseRequestResponse['lines'][number]
  > = [
    {
      title: 'Product',
      dataIndex: 'productName',
      key: 'productName',
      render: (value) => value ?? '-',
    },
    {
      title: 'Description',
      dataIndex: 'description',
      key: 'description',
      render: (value) => value ?? '-',
    },
    {
      title: 'Quantity',
      dataIndex: 'quantity',
      key: 'quantity',
    },
    {
      title: 'Unit',
      dataIndex: 'unit',
      key: 'unit',
    },
    {
      title: 'Required Date',
      dataIndex: 'requiredDate',
      key: 'requiredDate',
      render: (value) => value ?? '-',
    },
    {
      title: 'Notes',
      dataIndex: 'notes',
      key: 'notes',
      render: (value) => value ?? '-',
    },
  ]

  return (
    <Modal
      title={`Purchase Request ${purchaseRequest.requestNo}`}
      open={open}
      onCancel={onClose}
      footer={null}
      width={1100}
    >
      <Descriptions
        bordered
        column={{
          xs: 1,
          sm: 2,
        }}
      >
        <Descriptions.Item label="Request No.">
          {purchaseRequest.requestNo}
        </Descriptions.Item>

        <Descriptions.Item label="Requester">
          {purchaseRequest.requesterName}
        </Descriptions.Item>

        <Descriptions.Item label="Department">
          {purchaseRequest.department}
        </Descriptions.Item>

        <Descriptions.Item label="Request Date">
          {purchaseRequest.requestDate}
        </Descriptions.Item>

        <Descriptions.Item label="Required Date">
          {purchaseRequest.requiredDate}
        </Descriptions.Item>

        <Descriptions.Item label="Status">
          <Tag
            color={
              statusColors[purchaseRequest.status]
            }
          >
            {formatStatus(purchaseRequest.status)}
          </Tag>
        </Descriptions.Item>

        <Descriptions.Item label="Warehouse">
          {purchaseRequest.warehouseName ?? '-'}
        </Descriptions.Item>

        <Descriptions.Item label="Location">
          {purchaseRequest.locationName ?? '-'}
        </Descriptions.Item>

        <Descriptions.Item
          label="Reason"
          span={2}
        >
          {purchaseRequest.reason}
        </Descriptions.Item>

        <Descriptions.Item
          label="Notes"
          span={2}
        >
          {purchaseRequest.notes ?? '-'}
        </Descriptions.Item>
      </Descriptions>

      <Divider>
        Request Lines
      </Divider>

      <Table
        rowKey="id"
        columns={columns}
        dataSource={purchaseRequest.lines}
        pagination={false}
        scroll={{ x: 900 }}
      />
    </Modal>
  )
}