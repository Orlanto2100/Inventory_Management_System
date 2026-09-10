import { useEffect, useMemo, useState } from 'react'
import {
  Alert,
  Button,
  Card,
  Col,
  Empty,
  Form,
  Input,
  InputNumber,
  Modal,
  Row,
  Space,
  Table,
  Typography,
  message,
} from 'antd'
import {
  DeleteOutlined,
  EditOutlined,
  PlusOutlined,
  ReloadOutlined,
  SearchOutlined,
} from '@ant-design/icons'
import type { ColumnsType } from 'antd/es/table'

import {
  createProduct,
  deleteProduct,
  getProducts,
  updateProduct,
  type ProductResponse,
} from '../../../api/productApi'

const { Title, Text } = Typography

type ProductFormValues = {
  productName: string
  sku: string
  price: number
  description?: string
}

export default function ProductPage() {
  const [products, setProducts] = useState<ProductResponse[]>([])
  const [loading, setLoading] = useState(false)
  const [submitting, setSubmitting] = useState(false)
  const [deletingId, setDeletingId] = useState<number | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [searchText, setSearchText] = useState('')
  const [modalOpen, setModalOpen] = useState(false)
  const [editingProduct, setEditingProduct] =
    useState<ProductResponse | null>(null)

  const [form] = Form.useForm<ProductFormValues>()

  const loadProducts = async () => {
    try {
      setLoading(true)
      setError(null)

      const data = await getProducts()

      setProducts(data)
    } catch (error) {
      const errorMessage =
        error instanceof Error
          ? error.message
          : 'Failed to load products.'

      setError(errorMessage)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadProducts()
  }, [])

  const filteredProducts = useMemo(() => {
    const search = searchText.toLowerCase().trim()

    if (!search) {
      return products
    }

    return products.filter((product) => {
      return (
        product.productName.toLowerCase().includes(search) ||
        product.sku.toLowerCase().includes(search) ||
        (product.description ?? '').toLowerCase().includes(search)
      )
    })
  }, [products, searchText])

  const openCreateModal = () => {
    setEditingProduct(null)

    form.resetFields()

    form.setFieldsValue({
      price: 0,
    })

    setModalOpen(true)
  }

  const openEditModal = (product: ProductResponse) => {
    setEditingProduct(product)

    form.setFieldsValue({
      productName: product.productName,
      sku: product.sku,
      price: product.price,
      description: product.description ?? undefined,
    })

    setModalOpen(true)
  }

  const closeModal = () => {
    if (submitting) {
      return
    }

    setModalOpen(false)
    setEditingProduct(null)
    form.resetFields()
  }

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields()

      setSubmitting(true)

      if (editingProduct) {
        const updatedProduct = await updateProduct(
          editingProduct.productId,
          {
            productName: values.productName,
            price: values.price,
            description: values.description,
          },
        )

        setProducts((currentProducts) =>
          currentProducts.map((product) =>
            product.productId === editingProduct.productId
              ? updatedProduct
              : product,
          ),
        )

        message.success('Product updated successfully.')
      } else {
        const newProduct = await createProduct({
          productName: values.productName,
          sku: values.sku,
          price: values.price,
          description: values.description,
        })

        setProducts((currentProducts) => [
          ...currentProducts,
          newProduct,
        ])

        message.success('Product created successfully.')
      }

      setModalOpen(false)
      setEditingProduct(null)
      form.resetFields()
    } catch (error) {
      if (
        error &&
        typeof error === 'object' &&
        'errorFields' in error
      ) {
        return
      }

      const errorMessage =
        error instanceof Error
          ? error.message
          : editingProduct
            ? 'Failed to update product.'
            : 'Failed to create product.'

      message.error(errorMessage)
    } finally {
      setSubmitting(false)
    }
  }

  const handleDelete = (product: ProductResponse) => {
    Modal.confirm({
      title: 'Delete product?',
      content: (
        <>
          Are you sure you want to delete{' '}
          <strong>{product.productName}</strong>?
        </>
      ),
      okText: 'Delete',
      okType: 'danger',
      cancelText: 'Cancel',

      onOk: async () => {
        try {
          setDeletingId(product.productId)

          await deleteProduct(product.productId)

          setProducts((currentProducts) =>
            currentProducts.filter(
              (item) => item.productId !== product.productId,
            ),
          )

          message.success('Product deleted successfully.')
        } catch (error) {
          const errorMessage =
            error instanceof Error
              ? error.message
              : 'Failed to delete product.'

          message.error(errorMessage)

          throw error
        } finally {
          setDeletingId(null)
        }
      },
    })
  }

  const columns: ColumnsType<ProductResponse> = [
    {
      title: 'Product',
      dataIndex: 'productName',
      key: 'productName',
      sorter: (a, b) =>
        a.productName.localeCompare(b.productName),
      render: (productName: string) => (
        <Text strong>{productName}</Text>
      ),
    },

    {
      title: 'SKU',
      dataIndex: 'sku',
      key: 'sku',
    },

    {
      title: 'Price',
      dataIndex: 'price',
      key: 'price',
      align: 'right',
      sorter: (a, b) => a.price - b.price,
      render: (price: number) =>
        `${price.toLocaleString()} MMK`,
    },

    {
      title: 'Description',
      dataIndex: 'description',
      key: 'description',
      ellipsis: true,
      render: (description: string | null) =>
        description || (
          <Text type="secondary">
            No description
          </Text>
        ),
    },

    {
      title: 'Actions',
      key: 'actions',
      align: 'right',
      fixed: 'right',

      render: (_, product) => (
        <Space size="small">
          <Button
            type="text"
            icon={<EditOutlined />}
            disabled={deletingId === product.productId}
            onClick={() => openEditModal(product)}
          />

          <Button
            type="text"
            danger
            icon={<DeleteOutlined />}
            loading={deletingId === product.productId}
            onClick={() => handleDelete(product)}
          />
        </Space>
      ),
    },
  ]

  return (
    <div>
      {/* Page header */}
      <div>
        <Title
          level={2}
          style={{
            margin: 0,
            color: '#263238',
          }}
        >
          Products
        </Title>

        <Text type="secondary">
          Manage products in your inventory system.
        </Text>
      </div>

      {/* Error message */}
      {error && (
        <Alert
          type="error"
          showIcon
          message="Failed to load products"
          description={error}
          action={
            <Button
              size="small"
              onClick={loadProducts}
            >
              Retry
            </Button>
          }
          style={{
            marginTop: 24,
          }}
        />
      )}

      {/* Product table card */}
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
          <Col xs={24} lg={18}>
            <Row gutter={[8, 8]}>
              <Col xs={24} sm={16} md={14} lg={12}>
                <Input
                  placeholder="Search products..."
                  prefix={<SearchOutlined />}
                  value={searchText}
                  onChange={(event) =>
                    setSearchText(event.target.value)
                  }
                  allowClear
                />
              </Col>

              <Col xs={24} sm={8} md={6} lg={4}>
                <Button
                  icon={<ReloadOutlined />}
                  onClick={loadProducts}
                  loading={loading}
                  style={{
                    width: '100%',
                  }}
                >
                  Refresh
                </Button>
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
              onClick={openCreateModal}
            >
              Add Product
            </Button>
          </Col>
        </Row>

        {/* Product table */}
        <Table
          rowKey="productId"
          columns={columns}
          dataSource={filteredProducts}
          loading={loading}
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            showTotal: (total) =>
              `Total ${total} products`,
          }}
          locale={{
            emptyText: (
              <Empty
                description={
                  searchText
                    ? 'No products match your search.'
                    : 'No products found.'
                }
              />
            ),
          }}
          scroll={{ x: 800 }}
        />
      </Card>

      {/* Create/Edit modal */}
      <Modal
        title={
          editingProduct
            ? 'Edit Product'
            : 'Add Product'
        }
        open={modalOpen}
        onCancel={closeModal}
        onOk={handleSubmit}
        okText={
          editingProduct
            ? 'Update'
            : 'Create'
        }
        confirmLoading={submitting}
        destroyOnHidden
      >
        <Form
          form={form}
          layout="vertical"
          style={{
            marginTop: 24,
          }}
        >
          {/* Product name */}
          <Form.Item
            label="Product Name"
            name="productName"
            rules={[
              {
                required: true,
                message:
                  'Please enter the product name',
              },
              {
                max: 100,
                message:
                  'Product name cannot exceed 100 characters',
              },
            ]}
          >
            <Input
              placeholder="e.g. Cheeseburger"
              maxLength={100}
            />
          </Form.Item>

          {/* SKU */}
          <Form.Item
            label="SKU"
            name="sku"
            rules={[
              {
                required: true,
                message: 'Please enter the SKU',
              },
              {
                max: 50,
                message:
                  'SKU cannot exceed 50 characters',
              },
            ]}
          >
            <Input
              placeholder="e.g. BURGER-001"
              maxLength={50}
              disabled={!!editingProduct}
            />
          </Form.Item>

          {/* Price */}
          <Form.Item
            label="Price"
            name="price"
            rules={[
              {
                required: true,
                message: 'Please enter the price',
              },
            ]}
          >
            <InputNumber
              min={0}
              style={{
                width: '100%',
              }}
              placeholder="Price"
            />
          </Form.Item>

          {/* Description */}
          <Form.Item
            label="Description"
            name="description"
            rules={[
              {
                max: 1000,
                message:
                  'Description cannot exceed 1000 characters',
              },
            ]}
          >
            <Input.TextArea
              rows={4}
              placeholder="Enter a product description..."
              maxLength={1000}
              showCount
            />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  )
}