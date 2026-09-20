import { useState } from 'react'
import {
  Button,
  Card,
  Col,
  Form,
  Input,
  Modal,
  Row,
  Select,
  Space,
  Table,
  Tag,
  Typography,
} from 'antd'
import {
  DeleteOutlined,
  EditOutlined,
  PlusOutlined,
  SearchOutlined,
} from '@ant-design/icons'
import type { ColumnsType } from 'antd/es/table'

const { Title, Text } = Typography

type Warehouse = {
  id: number
  name: string
  code: string
  address: string
  description: string
  status: 'Active' | 'Inactive'
}

type WarehouseFormValues = {
  name: string
  code: string
  address: string
  description: string
  status: 'Active' | 'Inactive'
}

const initialWarehouses: Warehouse[] = [
  {
    id: 1,
    name: 'Main Warehouse',
    code: 'WH-001',
    address: 'Yangon',
    description: 'Primary storage warehouse',
    status: 'Active',
  },
  {
    id: 2,
    name: 'Mandalay Warehouse',
    code: 'WH-002',
    address: 'Mandalay',
    description: 'Northern distribution warehouse',
    status: 'Active',
  },
  {
    id: 3,
    name: 'Bago Warehouse',
    code: 'WH-003',
    address: 'Bago',
    description: 'Regional storage facility',
    status: 'Active',
  },
  {
    id: 4,
    name: 'Old Warehouse',
    code: 'WH-004',
    address: 'Yangon',
    description: 'Former storage facility',
    status: 'Inactive',
  },
]

export default function WarehousePage() {
  const [warehouses, setWarehouses] =
    useState<Warehouse[]>(initialWarehouses)

  const [searchText, setSearchText] = useState('')

  const [statusFilter, setStatusFilter] =
    useState<Warehouse['status'] | undefined>(
      undefined,
    )

  const [modalOpen, setModalOpen] = useState(false)

  const [editingWarehouse, setEditingWarehouse] =
    useState<Warehouse | null>(null)

  const [form] =
    Form.useForm<WarehouseFormValues>()

  const filteredWarehouses =
    warehouses.filter((warehouse) => {
      const search =
        searchText.toLowerCase().trim()

      const matchesSearch =
        !search ||
        warehouse.name
          .toLowerCase()
          .includes(search) ||
        warehouse.code
          .toLowerCase()
          .includes(search) ||
        warehouse.address
          .toLowerCase()
          .includes(search) ||
        warehouse.description
          .toLowerCase()
          .includes(search)

      const matchesStatus =
        !statusFilter ||
        warehouse.status === statusFilter

      return (
        matchesSearch &&
        matchesStatus
      )
    })

  const openCreateModal = () => {
    setEditingWarehouse(null)

    form.resetFields()

    form.setFieldsValue({
      status: 'Active',
    })

    setModalOpen(true)
  }

  const openEditModal = (
    warehouse: Warehouse,
  ) => {
    setEditingWarehouse(warehouse)

    form.setFieldsValue({
      name: warehouse.name,
      code: warehouse.code,
      address: warehouse.address,
      description:
        warehouse.description,
      status: warehouse.status,
    })

    setModalOpen(true)
  }

  const closeModal = () => {
    setModalOpen(false)
    setEditingWarehouse(null)
    form.resetFields()
  }

  const handleSubmit = async () => {
    const values =
      await form.validateFields()

    if (editingWarehouse) {
      setWarehouses(
        (currentWarehouses) =>
          currentWarehouses.map(
            (warehouse) =>
              warehouse.id ===
              editingWarehouse.id
                ? {
                    ...warehouse,
                    ...values,
                  }
                : warehouse,
          ),
      )
    } else {
      const newWarehouse: Warehouse = {
        id: Date.now(),
        ...values,
      }

      setWarehouses(
        (currentWarehouses) => [
          ...currentWarehouses,
          newWarehouse,
        ],
      )
    }

    closeModal()
  }

  const handleDelete = (
    warehouse: Warehouse,
  ) => {
    Modal.confirm({
      title: 'Delete warehouse?',
      content: (
        <>
          Are you sure you want to delete{' '}
          <strong>
            {warehouse.name}
          </strong>
          ?
        </>
      ),
      okText: 'Delete',
      okType: 'danger',
      cancelText: 'Cancel',
      onOk: () => {
        setWarehouses(
          (currentWarehouses) =>
            currentWarehouses.filter(
              (item) =>
                item.id !==
                warehouse.id,
            ),
        )
      },
    })
  }

  const columns: ColumnsType<Warehouse> = [
    {
      title: 'Warehouse',
      dataIndex: 'name',
      key: 'name',
      sorter: (a, b) =>
        a.name.localeCompare(b.name),
      render: (name: string) => (
        <Text strong>{name}</Text>
      ),
    },
    {
      title: 'Code',
      dataIndex: 'code',
      key: 'code',
    },
    {
      title: 'Address',
      dataIndex: 'address',
      key: 'address',
    },
    {
      title: 'Description',
      dataIndex: 'description',
      key: 'description',
      render: (
        description: string,
      ) => description || '—',
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      align: 'center',
      render: (
        status: Warehouse['status'],
      ) =>
        status === 'Active' ? (
          <Tag color="green">
            Active
          </Tag>
        ) : (
          <Tag>Inactive</Tag>
        ),
    },
    {
      title: 'Actions',
      key: 'actions',
      align: 'right',
      fixed: 'right',
      render: (_, warehouse) => (
        <Space size="small">
          <Button
            type="text"
            icon={<EditOutlined />}
            onClick={() =>
              openEditModal(warehouse)
            }
          />

          <Button
            type="text"
            danger
            icon={<DeleteOutlined />}
            onClick={() =>
              handleDelete(warehouse)
            }
          />
        </Space>
      ),
    },
  ]

  return (
    <div>
      {/* Page heading */}
      <div>
        <Title
          level={2}
          style={{
            margin: 0,
            color: '#263238',
          }}
        >
          Warehouses
        </Title>

        <Text type="secondary">
          Manage warehouses and storage
          facilities.
        </Text>
      </div>

      {/* Warehouse table */}
      <Card
        style={{
          marginTop: 24,
          background: '#f7f8fa',
        }}
      >
        {/* Toolbar */}
        <Row
          gutter={[12, 12]}
          align="middle"
          justify="space-between"
          style={{
            marginBottom: 20,
          }}
        >
          <Col
            xs={24}
            lg={18}
          >
            <Row gutter={[8, 8]}>
              <Col
                xs={24}
                sm={16}
                md={12}
              >
                <Input
                  placeholder="Search warehouses..."
                  prefix={
                    <SearchOutlined />
                  }
                  value={searchText}
                  onChange={(event) =>
                    setSearchText(
                      event.target.value,
                    )
                  }
                  allowClear
                />
              </Col>

              <Col
                xs={24}
                sm={8}
                md={6}
              >
                <Select
                  placeholder="Status"
                  value={statusFilter}
                  onChange={
                    setStatusFilter
                  }
                  allowClear
                  style={{
                    width: '100%',
                  }}
                  options={[
                    {
                      label: 'Active',
                      value: 'Active',
                    },
                    {
                      label: 'Inactive',
                      value: 'Inactive',
                    },
                  ]}
                />
              </Col>
            </Row>
          </Col>

          <Col
            xs={24}
            lg={6}
            style={{
              textAlign: 'right',
            }}
          >
            <Button
              type="primary"
              icon={<PlusOutlined />}
              onClick={
                openCreateModal
              }
            >
              Add Warehouse
            </Button>
          </Col>
        </Row>

        {/* Table */}
        <Table
          rowKey="id"
          columns={columns}
          dataSource={
            filteredWarehouses
          }
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) =>
              `Total ${total} warehouses`,
          }}
          scroll={{
            x: 900,
          }}
        />
      </Card>

      {/* Create / Edit modal */}
      <Modal
        title={
          editingWarehouse
            ? 'Edit Warehouse'
            : 'Add Warehouse'
        }
        open={modalOpen}
        onCancel={closeModal}
        onOk={handleSubmit}
        okText={
          editingWarehouse
            ? 'Update'
            : 'Create'
        }
        destroyOnHidden
      >
        <Form
          form={form}
          layout="vertical"
          style={{
            marginTop: 24,
          }}
        >
          <Form.Item
            label="Warehouse Name"
            name="name"
            rules={[
              {
                required: true,
                message:
                  'Please enter the warehouse name',
              },
            ]}
          >
            <Input
              placeholder="e.g. Main Warehouse"
            />
          </Form.Item>

          <Form.Item
            label="Warehouse Code"
            name="code"
            rules={[
              {
                required: true,
                message:
                  'Please enter the warehouse code',
              },
            ]}
          >
            <Input
              placeholder="e.g. WH-001"
            />
          </Form.Item>

          <Form.Item
            label="Address"
            name="address"
            rules={[
              {
                required: true,
                message:
                  'Please enter the warehouse address',
              },
            ]}
          >
            <Input.TextArea
              rows={2}
              placeholder="Enter warehouse address"
            />
          </Form.Item>

          <Form.Item
            label="Description"
            name="description"
          >
            <Input.TextArea
              rows={3}
              placeholder="Enter warehouse description"
            />
          </Form.Item>

          <Form.Item
            label="Status"
            name="status"
            rules={[
              {
                required: true,
                message:
                  'Please select the status',
              },
            ]}
          >
            <Select
              options={[
                {
                  label: 'Active',
                  value: 'Active',
                },
                {
                  label: 'Inactive',
                  value: 'Inactive',
                },
              ]}
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}