import {
  Button,
  Col,
  DatePicker,
  Divider,
  Form,
  Input,
  InputNumber,
  Modal,
  Row,
  Select,
  Space,
} from 'antd'
import dayjs, { type Dayjs } from 'dayjs'
import type {
  CreatePurchaseRequestRequest,
  PurchaseRequestResponse,
  UpdatePurchaseRequestRequest,
} from '../../../../api/purchaseRequestApi'

interface Option {
  value: number
  label: string
}

interface PurchaseRequestFormProps {
  open: boolean
  loading?: boolean
  purchaseRequest?: PurchaseRequestResponse | null
  warehouseOptions: Option[]
  locationOptions: Option[]
  productOptions: Option[]
  onCancel: () => void
  onSubmit: (
    values:
      | CreatePurchaseRequestRequest
      | UpdatePurchaseRequestRequest
  ) => Promise<void>
}

interface FormValues {
  department: string
  requiredDate: Dayjs
  reason: string
  notes?: string
  warehouseId: number
  locationId?: number
  lines: {
    productId?: number
    description?: string
    quantity: number
    unit: string
    requiredDate?: Dayjs
    notes?: string
  }[]
}

export default function PurchaseRequestForm({
  open,
  loading = false,
  purchaseRequest,
  warehouseOptions,
  locationOptions,
  productOptions,
  onCancel,
  onSubmit,
}: PurchaseRequestFormProps) {
  const [form] = Form.useForm<FormValues>()

  const isEdit = Boolean(purchaseRequest)

  const initialValues: FormValues =
    purchaseRequest
      ? {
          department:
            purchaseRequest.department,

          requiredDate: dayjs(
            purchaseRequest.requiredDate
          ),

          reason: purchaseRequest.reason,

          notes:
            purchaseRequest.notes ??
            undefined,

          warehouseId:
            purchaseRequest.warehouseId!,

          locationId:
            purchaseRequest.locationId ??
            undefined,

          lines:
            purchaseRequest.lines.map(
              (line) => ({
                productId:
                  line.productId ??
                  undefined,

                description:
                  line.description ??
                  undefined,

                quantity: line.quantity,

                unit: line.unit,

                requiredDate:
                  line.requiredDate
                    ? dayjs(
                        line.requiredDate
                      )
                    : undefined,

                notes:
                  line.notes ??
                  undefined,
              })
            ),
        }
      : {
          department: '',
          requiredDate: dayjs(),
          reason: '',
          notes: undefined,
          warehouseId: undefined as unknown as number,
          locationId: undefined,
          lines: [
            {
              quantity: 1,
              unit: 'PCS',
            },
          ],
        }

  const handleFinish = async (
    values: FormValues
  ) => {
    const request = {
      department:
        values.department,

      requiredDate:
        values.requiredDate.format(
          'YYYY-MM-DD'
        ),

      reason: values.reason,

      notes: values.notes,

      warehouseId:
        values.warehouseId,

      locationId:
        values.locationId,

      lines: values.lines.map(
        (line) => ({
          productId:
            line.productId,

          description:
            line.description,

          quantity:
            line.quantity,

          unit:
            line.unit,

          requiredDate:
            line.requiredDate
              ? line.requiredDate.format(
                  'YYYY-MM-DD'
                )
              : undefined,

          notes:
            line.notes,
        })
      ),
    }

    await onSubmit(request)
  }

  const handleCancel = () => {
    form.resetFields()
    onCancel()
  }

  return (
    <Modal
      title={
        isEdit
          ? 'Edit Purchase Request'
          : 'Create Purchase Request'
      }
      open={open}
      onCancel={handleCancel}
      footer={null}
      width={1000}
      destroyOnHidden
    >
      <Form<FormValues>
        form={form}
        layout="vertical"
        initialValues={initialValues}
        onFinish={handleFinish}
      >
        <Divider>
          Request Information
        </Divider>

        <Row gutter={16}>
          <Col
            xs={24}
            md={12}
          >
            <Form.Item
              label="Department"
              name="department"
              rules={[
                {
                  required: true,
                  message:
                    'Please enter the department',
                },
              ]}
            >
              <Input
                placeholder="e.g. Warehouse"
              />
            </Form.Item>
          </Col>

          <Col
            xs={24}
            md={12}
          >
            <Form.Item
              label="Required Date"
              name="requiredDate"
              rules={[
                {
                  required: true,
                  message:
                    'Please select the required date',
                },
              ]}
            >
              <DatePicker
                style={{
                  width: '100%',
                }}
                format="YYYY-MM-DD"
                disabledDate={(current) =>
                  current &&
                  current <
                    dayjs().startOf('day')
                }
              />
            </Form.Item>
          </Col>
        </Row>

        <Form.Item
          label="Reason"
          name="reason"
          rules={[
            {
              required: true,
              message:
                'Please enter the reason',
            },
            {
              max: 500,
              message:
                'Reason cannot exceed 500 characters',
            },
          ]}
        >
          <Input.TextArea
            rows={3}
            placeholder="Why is this purchase required?"
          />
        </Form.Item>

        <Form.Item
          label="Notes"
          name="notes"
        >
          <Input.TextArea
            rows={2}
            placeholder="Optional notes"
          />
        </Form.Item>

        <Row gutter={16}>
          <Col
            xs={24}
            md={12}
          >
            <Form.Item
              label="Warehouse"
              name="warehouseId"
              rules={[
                {
                  required: true,
                  message:
                    'Please select a warehouse',
                },
              ]}
            >
              <Select
                showSearch
                optionFilterProp="label"
                placeholder="Select warehouse"
                options={
                  warehouseOptions
                }
              />
            </Form.Item>
          </Col>

          <Col
            xs={24}
            md={12}
          >
            <Form.Item
              label="Location"
              name="locationId"
            >
              <Select
                allowClear
                showSearch
                optionFilterProp="label"
                placeholder="Select location"
                options={
                  locationOptions
                }
              />
            </Form.Item>
          </Col>
        </Row>

        <Divider>
          Request Lines
        </Divider>

        <Form.List
          name="lines"
          rules={[
            {
              validator: async (
                _,
                value
              ) => {
                if (
                  !value ||
                  value.length === 0
                ) {
                  return Promise.reject(
                    new Error(
                      'At least one request line is required'
                    )
                  )
                }
              },
            },
          ]}
        >
          {(
            fields,
            { add, remove },
            { errors }
          ) => (
            <>
              {fields.map(
                (
                  field,
                  index
                ) => (
                  <div
                    key={
                      field.key
                    }
                    style={{
                      padding: 16,
                      marginBottom: 16,
                      border:
                        '1px solid #e5e7eb',
                      borderRadius: 8,
                    }}
                  >
                    <Space
                      style={{
                        width:
                          '100%',
                        justifyContent:
                          'space-between',
                        marginBottom: 12,
                      }}
                    >
                      <strong>
                        Line{' '}
                        {index +
                          1}
                      </strong>

                      {fields.length >
                        1 && (
                        <Button
                          danger
                          type="link"
                          onClick={() =>
                            remove(
                              field.name
                            )
                          }
                        >
                          Remove
                        </Button>
                      )}
                    </Space>

                    <Row
                      gutter={16}
                    >
                      <Col
                        xs={24}
                        md={12}
                      >
                        <Form.Item
                          {...field}
                          label="Product"
                          name={[
                            field.name,
                            'productId',
                          ]}
                          dependencies={[
                            [
                              field.name,
                              'description',
                            ],
                          ]}
                          rules={[
                            ({
                              getFieldValue,
                            }) => ({
                              validator:
                                async (
                                  _,
                                  value
                                ) => {
                                  const description =
                                    getFieldValue(
                                      [
                                        'lines',
                                        field.name,
                                        'description',
                                      ]
                                    )

                                  if (
                                    !value &&
                                    !description?.trim()
                                  ) {
                                    return Promise.reject(
                                      new Error(
                                        'Select a product or enter a description'
                                      )
                                    )
                                  }

                                  return Promise.resolve()
                                },
                            }),
                          ]}
                        >
                          <Select
                            allowClear
                            showSearch
                            optionFilterProp="label"
                            placeholder="Select product"
                            options={
                              productOptions
                            }
                          />
                        </Form.Item>
                      </Col>

                      <Col
                        xs={24}
                        md={12}
                      >
                        <Form.Item
                          {...field}
                          label="Unit"
                          name={[
                            field.name,
                            'unit',
                          ]}
                          rules={[
                            {
                              required:
                                true,
                              message:
                                'Please enter the unit',
                            },
                          ]}
                        >
                          <Input placeholder="PCS" />
                        </Form.Item>
                      </Col>
                    </Row>

                    <Row
                      gutter={16}
                    >
                      <Col
                        xs={24}
                        md={8}
                      >
                        <Form.Item
                          {...field}
                          label="Quantity"
                          name={[
                            field.name,
                            'quantity',
                          ]}
                          rules={[
                            {
                              required:
                                true,
                              message:
                                'Please enter quantity',
                            },
                            {
                              type: 'number',
                              min: 0.0001,
                              message:
                                'Quantity must be greater than 0',
                            },
                          ]}
                        >
                          <InputNumber
                            style={{
                              width:
                                '100%',
                            }}
                            min={
                              0.0001
                            }
                            step={1}
                          />
                        </Form.Item>
                      </Col>

                      <Col
                        xs={24}
                        md={8}
                      >
                        <Form.Item
                          {...field}
                          label="Required Date"
                          name={[
                            field.name,
                            'requiredDate',
                          ]}
                        >
                          <DatePicker
                            style={{
                              width:
                                '100%',
                            }}
                            format="YYYY-MM-DD"
                          />
                        </Form.Item>
                      </Col>

                      <Col
                        xs={24}
                        md={8}
                      >
                        <Form.Item
                          {...field}
                          label="Description"
                          name={[
                            field.name,
                            'description',
                          ]}
                          dependencies={[
                            [
                              field.name,
                              'productId',
                            ],
                          ]}
                          rules={[
                            ({
                              getFieldValue,
                            }) => ({
                              validator:
                                async (
                                  _,
                                  value
                                ) => {
                                  const productId =
                                    getFieldValue(
                                      [
                                        'lines',
                                        field.name,
                                        'productId',
                                      ]
                                    )

                                  if (
                                    !productId &&
                                    !value?.trim()
                                  ) {
                                    return Promise.reject(
                                      new Error(
                                        'Select a product or enter a description'
                                      )
                                    )
                                  }

                                  return Promise.resolve()
                                },
                            }),
                          ]}
                        >
                          <Input
                            placeholder="Optional description"
                          />
                        </Form.Item>
                      </Col>
                    </Row>

                    <Form.Item
                      {...field}
                      label="Line Notes"
                      name={[
                        field.name,
                        'notes',
                      ]}
                    >
                      <Input.TextArea
                        rows={2}
                        placeholder="Optional line notes"
                      />
                    </Form.Item>
                  </div>
                )
              )}

              <Form.ErrorList
                errors={errors}
              />

              <Button
                type="dashed"
                block
                onClick={() =>
                  add({
                    quantity: 1,
                    unit: 'PCS',
                  })
                }
              >
                + Add Line
              </Button>
            </>
          )}
        </Form.List>

        <Divider />

        <Space
          style={{
            width: '100%',
            justifyContent:
              'flex-end',
          }}
        >
          <Button
            onClick={
              handleCancel
            }
          >
            Cancel
          </Button>

          <Button
            type="primary"
            htmlType="submit"
            loading={loading}
          >
            {isEdit
              ? 'Save Changes'
              : 'Create'}
          </Button>
        </Space>
      </Form>
    </Modal>
  )
}
