import { useState } from 'react'
import {
  Button,
  Card,
  Col,
  Form,
  Input,
  Modal,
  Row,
  Space,
  Table,
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

type Vendor = {
  id: number
  name: string
  contactPerson: string
  phone: string
  email: string
  address: string
}

type VendorFormValues = {
  name: string
  contactPerson: string
  phone: string
  email: string
  address: string
}

const initialVendors: Vendor[] = [
  {
    id: 1,
    name: 'Yangon Food Supply',
    contactPerson: 'Aung Min',
    phone: '09123456789',
    email: 'aung@yangonfood.com',
    address: 'Yangon',
  },
  {
    id: 2,
    name: 'Myanmar Beverage Co.',
    contactPerson: 'Mya Thandar',
    phone: '09876543210',
    email: 'mya@myanmarev.com',
    address: 'Mandalay',
  },
  {
    id: 3,
    name: 'Golden Meat Supplier',
    contactPerson: 'Ko Ko',
    phone: '09234567890',
    email: 'koko@goldenmeat.com',
    address: 'Bago',
  },
  {
    id: 4,
    name: 'Fresh Ingredients Ltd.',
    contactPerson: 'Su Su',
    phone: '09555555555',
    email: '',
    address: 'Naypyidaw',
  },
]

export default function VendorPage() {
  const [vendors, setVendors] =
    useState<Vendor[]>(initialVendors)

  const [searchText, setSearchText] = useState('')

  const [modalOpen, setModalOpen] = useState(false)

  const [editingVendor, setEditingVendor] =
    useState<Vendor | null>(null)

  const [form] =
    Form.useForm<VendorFormValues>()

  const filteredVendors =
    vendors.filter((vendor) => {
      const search =
        searchText.toLowerCase().trim()

      if (!search) {
        return true
      }

      return (
        vendor.name
          .toLowerCase()
          .includes(search) ||
        vendor.contactPerson
          .toLowerCase()
          .includes(search) ||
        vendor.phone
          .toLowerCase()
          .includes(search) ||
        vendor.email
          .toLowerCase()
          .includes(search) ||
        vendor.address
          .toLowerCase()
          .includes(search)
      )
    })

  const openCreateModal = () => {
    setEditingVendor(null)
    form.resetFields()
    setModalOpen(true)
  }

  const openEditModal = (
    vendor: Vendor,
  ) => {
    setEditingVendor(vendor)

    form.setFieldsValue({
      name: vendor.name,
      contactPerson:
        vendor.contactPerson,
      phone: vendor.phone,
      email: vendor.email,
      address: vendor.address,
    })

    setModalOpen(true)
  }

  const closeModal = () => {
    setModalOpen(false)
    setEditingVendor(null)
    form.resetFields()
  }

  const handleSubmit = async () => {
    const values =
      await form.validateFields()

    if (editingVendor) {
      setVendors(
        (currentVendors) =>
          currentVendors.map(
            (vendor) =>
              vendor.id ===
              editingVendor.id
                ? {
                    ...vendor,
                    ...values,
                  }
                : vendor,
          ),
      )
    } else {
      const newVendor: Vendor = {
        id: Date.now(),
        ...values,
      }

      setVendors(
        (currentVendors) => [
          ...currentVendors,
          newVendor,
        ],
      )
    }

    closeModal()
  }

  const handleDelete = (
    vendor: Vendor,
  ) => {
    Modal.confirm({
      title: 'Delete vendor?',
      content: (
        <>
          Are you sure you want to delete{' '}
          <strong>
            {vendor.name}
          </strong>
          ?
        </>
      ),
      okText: 'Delete',
      okType: 'danger',
      cancelText: 'Cancel',
      onOk: () => {
        setVendors(
          (currentVendors) =>
            currentVendors.filter(
              (item) =>
                item.id !== vendor.id,
            ),
        )
      },
    })
  }

  const columns: ColumnsType<Vendor> = [
    {
      title: 'Vendor',
      dataIndex: 'name',
      key: 'name',
      sorter: (a, b) =>
        a.name.localeCompare(b.name),
      render: (name: string) => (
        <Text strong>{name}</Text>
      ),
    },
    {
      title: 'Contact Person',
      dataIndex: 'contactPerson',
      key: 'contactPerson',
    },
    {
      title: 'Phone',
      dataIndex: 'phone',
      key: 'phone',
    },
    {
      title: 'Email',
      dataIndex: 'email',
      key: 'email',
      render: (email: string) =>
        email || '—',
    },
    {
      title: 'Address',
      dataIndex: 'address',
      key: 'address',
    },
    {
      title: 'Actions',
      key: 'actions',
      align: 'right',
      fixed: 'right',
      render: (_, vendor) => (
        <Space size="small">
          <Button
            type="text"
            icon={<EditOutlined />}
            onClick={() =>
              openEditModal(vendor)
            }
          />

          <Button
            type="text"
            danger
            icon={<DeleteOutlined />}
            onClick={() =>
              handleDelete(vendor)
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
          Vendors
        </Title>

        <Text type="secondary">
          Manage suppliers and vendors
          for your inventory system.
        </Text>
      </div>

      {/* Vendor table */}
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
            <Input
              placeholder="Search vendors..."
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
              style={{
                maxWidth: 400,
              }}
            />
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
              Add Vendor
            </Button>
          </Col>
        </Row>

        {/* Table */}
        <Table
          rowKey="id"
          columns={columns}
          dataSource={filteredVendors}
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) =>
              `Total ${total} vendors`,
          }}
          scroll={{
            x: 900,
          }}
        />
      </Card>

      {/* Create / Edit modal */}
      <Modal
        title={
          editingVendor
            ? 'Edit Vendor'
            : 'Add Vendor'
        }
        open={modalOpen}
        onCancel={closeModal}
        onOk={handleSubmit}
        okText={
          editingVendor
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
            label="Vendor Name"
            name="name"
            rules={[
              {
                required: true,
                message:
                  'Please enter the vendor name',
              },
            ]}
          >
            <Input
              placeholder="e.g. Yangon Food Supply"
            />
          </Form.Item>

          <Form.Item
            label="Contact Person"
            name="contactPerson"
            rules={[
              {
                required: true,
                message:
                  'Please enter the contact person',
              },
            ]}
          >
            <Input
              placeholder="e.g. Aung Min"
            />
          </Form.Item>

          <Form.Item
            label="Phone"
            name="phone"
            rules={[
              {
                required: true,
                message:
                  'Please enter the phone number',
              },
            ]}
          >
            <Input
              placeholder="e.g. 09123456789"
            />
          </Form.Item>

          <Form.Item
            label="Email"
            name="email"
            rules={[
              {
                type: 'email',
                message:
                  'Please enter a valid email address',
              },
            ]}
          >
            <Input
              placeholder="e.g. supplier@example.com"
            />
          </Form.Item>

          <Form.Item
            label="Address"
            name="address"
            rules={[
              {
                required: true,
                message:
                  'Please enter the address',
              },
            ]}
          >
            <Input.TextArea
              rows={3}
              placeholder="Enter vendor address"
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}