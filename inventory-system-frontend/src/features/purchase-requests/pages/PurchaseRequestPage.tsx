import { Button, Card, Space, Table, Tag } from 'antd'
import type { ColumnsType } from 'antd/es/table'

type PurchaseRequest = {
id: number
requestNo: string
requestedBy: string
department: string
requestDate: string
status: 'DRAFT' | 'PENDING' | 'APPROVED' | 'REJECTED'
}

const demoData: PurchaseRequest[] = [
{
id: 1,
requestNo: 'PR-0001',
requestedBy: 'John Doe',
department: 'Warehouse',
requestDate: '2026-09-16',
status: 'PENDING',
},
{
id: 2,
requestNo: 'PR-0002',
requestedBy: 'Jane Smith',
department: 'Operations',
requestDate: '2026-09-15',
status: 'APPROVED',
},
{
id: 3,
requestNo: 'PR-0003',
requestedBy: 'Mike Lee',
department: 'Sales',
requestDate: '2026-09-14',
status: 'DRAFT',
},
]

const statusColors: Record<PurchaseRequest['status'], string> = {
DRAFT: 'default',
PENDING: 'processing',
APPROVED: 'success',
REJECTED: 'error',
}

export default function PurchaseRequestPage() {
const columns: ColumnsType<PurchaseRequest> = [
{
title: 'Request No.',
dataIndex: 'requestNo',
key: 'requestNo',
},
{
title: 'Requested By',
dataIndex: 'requestedBy',
key: 'requestedBy',
},
{
title: 'Department',
dataIndex: 'department',
key: 'department',
},
{
title: 'Request Date',
dataIndex: 'requestDate',
key: 'requestDate',
},
{
title: 'Status',
dataIndex: 'status',
key: 'status',
render: (status: PurchaseRequest['status']) => ( <Tag color={statusColors[status]}>
{status} </Tag>
),
},
]

return (
<Card
title="Purchase Requests"
extra={ <Space> <Button type="primary">
New Purchase Request </Button> </Space>
}
>
<Table
rowKey="id"
columns={columns}
dataSource={demoData}
pagination={{
pageSize: 10,
}}
/> </Card>
)
}
