import {
  Button,
  Card,
  Input,
  Popconfirm,
  Select,
  Space,
  Table,
  Tag,
  message,
} from 'antd'
import type { ColumnsType } from 'antd/es/table'
import {
  useEffect,
  useMemo,
  useState,
} from 'react'

import type {
  PurchaseRequestResponse,
  PurchaseRequestStatus,
} from '../../../../api/purchaseRequestApi'

import {
  getWarehouses,
  type WarehouseResponse,
} from '../../../../api/warehouseApi'

import PurchaseRequestForm from '../components/PurchaseRequestForm'

import { usePurchaseRequests } from '../hooks/usePurchaseRequests'

const { Search } = Input

type Role =
  | 'ADMIN'
  | 'WAREHOUSE_STAFF'
  | 'PURCHASING_STAFF'
  | 'SALES_STAFF'

type SelectOption = {
  value: number
  label: string
}

const statusColors: Record<
  PurchaseRequestStatus,
  string
> = {
  DRAFT: 'default',
  PENDING_APPROVAL: 'gold',
  APPROVED: 'blue',
  REJECTED: 'red',
  PROCESSING: 'purple',
  COMPLETED: 'green',
}

export default function PurchaseRequestPage() {
  const {
    purchaseRequests,
    loading,
    error,

    createPurchaseRequest,

    submitPurchaseRequest,
    approvePurchaseRequest,
    rejectPurchaseRequest,
    processPurchaseRequest,
    completePurchaseRequest,
  } = usePurchaseRequests()

  const [search, setSearch] = useState('')

  const [status, setStatus] = useState<
    PurchaseRequestStatus | undefined
  >()

  const [formOpen, setFormOpen] =
    useState(false)

  const [formLoading, setFormLoading] =
    useState(false)

  const [warehouses, setWarehouses] =
    useState<WarehouseResponse[]>([])

  const [warehousesLoading, setWarehousesLoading] =
    useState(false)

  const role =
    localStorage.getItem('role') as Role | null

  const userId = Number(
    localStorage.getItem('userId')
  )

  /*
   * Load warehouses for the purchase request form.
   */
  useEffect(() => {
    const loadWarehouses = async () => {
      setWarehousesLoading(true)

      try {
        const response =
          await getWarehouses({
            page: 0,
            size: 100,
            status: 'ACTIVE',
          })

        setWarehouses(
          response.content
        )
      } catch (err) {
        message.error(
          err instanceof Error
            ? err.message
            : 'Failed to load warehouses'
        )
      } finally {
        setWarehousesLoading(false)
      }
    }

    loadWarehouses()
  }, [])

  const warehouseOptions =
    useMemo(
      (): SelectOption[] =>
        warehouses.map(
          (warehouse) => ({
            value:
              warehouse.warehouseId,
            label:
              `${warehouse.code} - ${warehouse.name}`,
          })
        ),
      [warehouses]
    )

  /*
   * Product options will be connected
   * when the product API/hook is wired.
   *
   * The form currently allows a line to
   * contain a description instead.
   */
  const productOptions: SelectOption[] = []

  /*
   * Location options will be connected
   * when the location API/hook is wired.
   */
  const locationOptions: SelectOption[] = []

  const filteredPurchaseRequests =
    useMemo(() => {
      const searchValue =
        search
          .toLowerCase()
          .trim()

      return purchaseRequests.filter(
        (purchaseRequest) => {
          const matchesSearch =
            !searchValue ||
            purchaseRequest.requestNo
              .toLowerCase()
              .includes(
                searchValue
              ) ||
            purchaseRequest
              .requesterName
              .toLowerCase()
              .includes(
                searchValue
              ) ||
            purchaseRequest
              .department
              .toLowerCase()
              .includes(
                searchValue
              )

          const matchesStatus =
            !status ||
            purchaseRequest.status ===
              status

          return (
            matchesSearch &&
            matchesStatus
          )
        }
      )
    }, [
      purchaseRequests,
      search,
      status,
    ])

  const handleCreate = async (
    values: Parameters<
      typeof createPurchaseRequest
    >[0]
  ) => {
    setFormLoading(true)

    try {
      await createPurchaseRequest(
        values
      )

      message.success(
        'Purchase request created successfully'
      )

      setFormOpen(false)
    } catch (err) {
      message.error(
        err instanceof Error
          ? err.message
          : 'Failed to create purchase request'
      )
    } finally {
      setFormLoading(false)
    }
  }

  const handleSubmit = async (
    id: number
  ) => {
    try {
      await submitPurchaseRequest(
        id
      )

      message.success(
        'Purchase request submitted'
      )
    } catch (err) {
      message.error(
        err instanceof Error
          ? err.message
          : 'Failed to submit purchase request'
      )
    }
  }

  const handleApprove = async (
    id: number
  ) => {
    try {
      await approvePurchaseRequest(
        id
      )

      message.success(
        'Purchase request approved'
      )
    } catch (err) {
      message.error(
        err instanceof Error
          ? err.message
          : 'Failed to approve purchase request'
      )
    }
  }

  const handleReject = async (
    id: number
  ) => {
    try {
      await rejectPurchaseRequest(
        id
      )

      message.success(
        'Purchase request rejected'
      )
    } catch (err) {
      message.error(
        err instanceof Error
          ? err.message
          : 'Failed to reject purchase request'
      )
    }
  }

  const handleProcess = async (
    id: number
  ) => {
    try {
      await processPurchaseRequest(
        id
      )

      message.success(
        'Purchase request is now processing'
      )
    } catch (err) {
      message.error(
        err instanceof Error
          ? err.message
          : 'Failed to process purchase request'
      )
    }
  }

  const handleComplete = async (
    id: number
  ) => {
    try {
      await completePurchaseRequest(
        id
      )

      message.success(
        'Purchase request completed'
      )
    } catch (err) {
      message.error(
        err instanceof Error
          ? err.message
          : 'Failed to complete purchase request'
      )
    }
  }

  const canEdit = (
    record: PurchaseRequestResponse
  ) => {
    if (
      record.status !== 'DRAFT'
    ) {
      return false
    }

    if (role === 'ADMIN') {
      return true
    }

    if (
      role !==
        'WAREHOUSE_STAFF' &&
      role !==
        'PURCHASING_STAFF'
    ) {
      return false
    }

    return (
      record.requesterId ===
      userId
    )
  }

  const canSubmit = (
    record: PurchaseRequestResponse
  ) => {
    if (
      record.status !== 'DRAFT'
    ) {
      return false
    }

    if (role === 'ADMIN') {
      return true
    }

    if (
      role !==
        'WAREHOUSE_STAFF' &&
      role !==
        'PURCHASING_STAFF'
    ) {
      return false
    }

    return (
      record.requesterId ===
      userId
    )
  }

  const canApproveOrReject =
    role === 'ADMIN' ||
    role ===
      'PURCHASING_STAFF'

  const canProcessOrComplete =
    role === 'ADMIN' ||
    role ===
      'PURCHASING_STAFF'

  const columns: ColumnsType<
    PurchaseRequestResponse
  > = [
    {
      title: 'Request No.',
      dataIndex: 'requestNo',
      key: 'requestNo',
    },
    {
      title: 'Requester',
      dataIndex: 'requesterName',
      key: 'requesterName',
    },
    {
      title: 'Department',
      dataIndex: 'department',
      key: 'department',
    },
    {
      title: 'Required Date',
      dataIndex: 'requiredDate',
      key: 'requiredDate',
    },
    {
      title: 'Warehouse',
      dataIndex: 'warehouseName',
      key: 'warehouseName',
      render: (
        value: string | null
      ) => value ?? '-',
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      render: (
        value: PurchaseRequestStatus
      ) => (
        <Tag
          color={
            statusColors[value]
          }
        >
          {value.replaceAll(
            '_',
            ' '
          )}
        </Tag>
      ),
    },
    {
      title: 'Actions',
      key: 'actions',
      render: (
        _,
        record
      ) => (
        <Space wrap>
          <Button size="small">
            View
          </Button>

          {canEdit(record) && (
            <Button size="small">
              Edit
            </Button>
          )}

          {canSubmit(record) && (
            <Popconfirm
              title="Submit this purchase request?"
              onConfirm={() =>
                handleSubmit(
                  record.id
                )
              }
            >
              <Button
                size="small"
                type="primary"
              >
                Submit
              </Button>
            </Popconfirm>
          )}

          {canApproveOrReject &&
            record.status ===
              'PENDING_APPROVAL' && (
              <>
                <Button
                  size="small"
                  type="primary"
                  onClick={() =>
                    handleApprove(
                      record.id
                    )
                  }
                >
                  Approve
                </Button>

                <Popconfirm
                  title="Reject this purchase request?"
                  onConfirm={() =>
                    handleReject(
                      record.id
                    )
                  }
                >
                  <Button
                    size="small"
                    danger
                  >
                    Reject
                  </Button>
                </Popconfirm>
              </>
            )}

          {canProcessOrComplete &&
            record.status ===
              'APPROVED' && (
              <Button
                size="small"
                type="primary"
                onClick={() =>
                  handleProcess(
                    record.id
                  )
                }
              >
                Process
              </Button>
            )}

          {canProcessOrComplete &&
            record.status ===
              'PROCESSING' && (
              <Popconfirm
                title="Mark this purchase request as completed?"
                onConfirm={() =>
                  handleComplete(
                    record.id
                  )
                }
              >
                <Button
                  size="small"
                  type="primary"
                >
                  Complete
                </Button>
              </Popconfirm>
            )}
        </Space>
      ),
    },
  ]

  return (
    <>
      <Card
        title="Purchase Requests"
        extra={
          <Button
            type="primary"
            onClick={() =>
              setFormOpen(true)
            }
            loading={
              warehousesLoading
            }
          >
            Create Purchase Request
          </Button>
        }
      >
        <Space
          style={{
            width: '100%',
            marginBottom: 16,
          }}
          wrap
        >
          <Search
            placeholder="Search request no., requester, department"
            allowClear
            onChange={(event) =>
              setSearch(
                event.target.value
              )
            }
            style={{
              width: 300,
            }}
          />

          <Select
            placeholder="Filter by status"
            allowClear
            value={status}
            onChange={setStatus}
            style={{
              width: 200,
            }}
            options={[
              {
                value: 'DRAFT',
                label: 'Draft',
              },
              {
                value:
                  'PENDING_APPROVAL',
                label:
                  'Pending Approval',
              },
              {
                value: 'APPROVED',
                label: 'Approved',
              },
              {
                value: 'REJECTED',
                label: 'Rejected',
              },
              {
                value: 'PROCESSING',
                label: 'Processing',
              },
              {
                value: 'COMPLETED',
                label: 'Completed',
              },
            ]}
          />
        </Space>

        {error && (
          <div
            style={{
              marginBottom: 16,
              color: 'red',
            }}
          >
            {error}
          </div>
        )}

        <Table
          rowKey="id"
          columns={columns}
          dataSource={
            filteredPurchaseRequests
          }
          loading={loading}
          scroll={{
            x: 1000,
          }}
        />
      </Card>

      <PurchaseRequestForm
        open={formOpen}
        loading={formLoading}
        purchaseRequest={null}
        warehouseOptions={
          warehouseOptions
        }
        locationOptions={
          locationOptions
        }
        productOptions={
          productOptions
        }
        onCancel={() =>
          setFormOpen(false)
        }
        onSubmit={handleCreate}
      />
    </>
  )
}