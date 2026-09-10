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

type Location = {
  id: number
  name: string
  code: string
  warehouse: string
  description: string
  status: 'Active' | 'Inactive'
}

type LocationFormValues = {
  name: string
  code: string
  warehouse: string
  description: string
  status: 'Active' | 'Inactive'
}

const initialLocations: Location[] = [
  {
    id: 1,
    name: 'Shelf A-01',
    code: 'A-01',
    warehouse: 'Main Warehouse',
    description: 'Main storage area',
    status: 'Active',
  },
  {
    id: 2,
    name: 'Shelf A-02',
    code: 'A-02',
    warehouse: 'Main Warehouse',
    description: 'Food products',
    status: 'Active',
  },
  {
    id: 3,
    name: 'Shelf B-01',
    code: 'B-01',
    warehouse: 'Mandalay Warehouse',
    description: 'Ingredient storage',
    status: 'Active',
  },
  {
    id: 4,
    name: 'Shelf C-01',
    code: 'C-01',
    warehouse: 'Bago Warehouse',
    description: 'Regional stock',
    status: 'Active',
  },
  {
    id: 5,
    name: 'Old Shelf',
    code: 'OLD-01',
    warehouse: 'Old Warehouse',
    description: 'Unused storage location',
    status: 'Inactive',
  },
]

const warehouseOptions = [
  'Main Warehouse',
  'Mandalay Warehouse',
  'Bago Warehouse',
  'Old Warehouse',
]

export default function LocationPage() {
  const [locations, setLocations] =
    useState<Location[]>(initialLocations)

  const [searchText, setSearchText] = useState('')

  const [warehouseFilter, setWarehouseFilter] =
    useState<string | undefined>(undefined)

  const [statusFilter, setStatusFilter] =
    useState<Location['status'] | undefined>(undefined)

  const [modalOpen, setModalOpen] = useState(false)

  const [editingLocation, setEditingLocation] =
    useState<Location | null>(null)

  const [form] =
    Form.useForm<LocationFormValues>()

  const filteredLocations = locations.filter(
    (location) => {
      const search = searchText
        .toLowerCase()
        .trim()

      const matchesSearch =
        !search ||
        location.name
          .toLowerCase()
          .includes(search) ||
        location.code
          .toLowerCase()
          .includes(search) ||
        location.warehouse
          .toLowerCase()
          .includes(search) ||
        location.description
          .toLowerCase()
          .includes(search)

      const matchesWarehouse =
        !warehouseFilter ||
        location.warehouse === warehouseFilter

      const matchesStatus =
        !statusFilter ||
        location.status === statusFilter

      return (
        matchesSearch &&
        matchesWarehouse &&
        matchesStatus
      )
    },
  )

  const openCreateModal = () => {
    setEditingLocation(null)

    form.resetFields()

    form.setFieldsValue({
      status: 'Active',
    })

    setModalOpen(true)
  }

  const openEditModal = (
    location: Location,
  ) => {
    setEditingLocation(location)

    form.setFieldsValue({
      name: location.name,
      code: location.code,
      warehouse: location.warehouse,
      description: location.description,
      status: location.status,
    })

    setModalOpen(true)
  }

  const closeModal = () => {
    setModalOpen(false)
    setEditingLocation(null)
    form.resetFields()
  }

  const handleSubmit = async () => {
    const values =
      await form.validateFields()

    if (editingLocation) {
      setLocations(
        (currentLocations) =>
          currentLocations.map(
            (location) =>
              location.id ===
              editingLocation.id
                ? {
                    ...location,
                    ...values,
                  }
                : location,
          ),
      )
    } else {
      const newLocation: Location = {
        id: Date.now(),
        ...values,
      }

      setLocations(
        (currentLocations) => [
          ...currentLocations,
          newLocation,
        ],
      )
    }

    closeModal()
  }

  const handleDelete = (
    location: Location,
  ) => {
    Modal.confirm({
      title: 'Delete location?',
      content: (
        <>
          Are you sure you want to delete{' '}
          <strong>{location.name}</strong>?
        </>
      ),
      okText: 'Delete',
      okType: 'danger',
      cancelText: 'Cancel',
      onOk: () => {
        setLocations(
          (currentLocations) =>
            currentLocations.filter(
              (item) =>
                item.id !== location.id,
            ),
        )
      },
    })
  }

  const columns: ColumnsType<Location> = [
    {
      title: 'Location',
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
      title: 'Warehouse',
      dataIndex: 'warehouse',
      key: 'warehouse',
    },
    {
      title: 'Description',
      dataIndex: 'description',
      key: 'description',
      render: (description: string) =>
        description || '—',
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      align: 'center',
      render: (
        status: Location['status'],
      ) =>
        status === 'Active' ? (
          <Tag color="green">Active</Tag>
        ) : (
          <Tag>Inactive</Tag>
        ),
    },
    {
      title: 'Actions',
      key: 'actions',
      align: 'right',
      fixed: 'right',
      render: (_, location) => (
        <Space size="small">
          <Button
            type="text"
            icon={<EditOutlined />}
            onClick={() =>
              openEditModal(location)
            }
          />

          <Button
            type="text"
            danger
            icon={<DeleteOutlined />}
            onClick={() =>
              handleDelete(location)
            }
          />
        </Space>
      ),
    },
  ]

  return (
    <div>
      <div>
        <Title
          level={2}
          style={{
            margin: 0,
            color: '#263238',
          }}
        >
          Locations
        </Title>

        <Text type="secondary">
          Manage storage locations within
          your warehouses.
        </Text>
      </div>

      <Card
        style={{
          marginTop: 24,
          background: '#f7f8fa',
        }}
      >
        <Row
          gutter={[12, 12]}
          align="middle"
          justify="space-between"
          style={{
            marginBottom: 20,
          }}
        >
          <Col xs={24} lg={18}>
            <Row gutter={[8, 8]}>
              <Col
                xs={24}
                sm={12}
                md={10}
              >
                <Input
                  placeholder="Search locations..."
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
                sm={6}
                md={6}
              >
                <Select
                  placeholder="Warehouse"
                  value={warehouseFilter}
                  onChange={
                    setWarehouseFilter
                  }
                  allowClear
                  style={{
                    width: '100%',
                  }}
                  options={warehouseOptions.map(
                    (warehouse) => ({
                      label: warehouse,
                      value: warehouse,
                    }),
                  )}
                />
              </Col>

              <Col
                xs={24}
                sm={6}
                md={5}
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
              Add Location
            </Button>
          </Col>
        </Row>

        <Table
          rowKey="id"
          columns={columns}
          dataSource={filteredLocations}
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) =>
              `Total ${total} locations`,
          }}
          scroll={{
            x: 900,
          }}
        />
      </Card>

      <Modal
        title={
          editingLocation
            ? 'Edit Location'
            : 'Add Location'
        }
        open={modalOpen}
        onCancel={closeModal}
        onOk={handleSubmit}
        okText={
          editingLocation
            ? 'Update'
            : 'Create'
        }
        destroyOnClose
      >
        <Form
          form={form}
          layout="vertical"
          style={{
            marginTop: 24,
          }}
        >
          <Form.Item
            label="Location Name"
            name="name"
            rules={[
              {
                required: true,
                message:
                  'Please enter the location name',
              },
            ]}
          >
            <Input placeholder="e.g. Shelf A-01" />
          </Form.Item>

          <Form.Item
            label="Location Code"
            name="code"
            rules={[
              {
                required: true,
                message:
                  'Please enter the location code',
              },
            ]}
          >
            <Input placeholder="e.g. A-01" />
          </Form.Item>

          <Form.Item
            label="Warehouse"
            name="warehouse"
            rules={[
              {
                required: true,
                message:
                  'Please select a warehouse',
              },
            ]}
          >
            <Select
              placeholder="Select warehouse"
              options={warehouseOptions.map(
                (warehouse) => ({
                  label: warehouse,
                  value: warehouse,
                }),
              )}
            />
          </Form.Item>

          <Form.Item
            label="Description"
            name="description"
          >
            <Input.TextArea
              rows={3}
              placeholder="Enter location description"
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