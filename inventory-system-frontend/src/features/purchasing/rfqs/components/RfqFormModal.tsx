import {
  Button,
  DatePicker,
  Flex,
  Form,
  Input,
  InputNumber,
  Modal,
  Select,
  Typography,
} from 'antd'
import {
  DeleteOutlined,
  PlusOutlined,
} from '@ant-design/icons'
import type { Dayjs } from 'dayjs'

const { Text } = Typography

export type RfqFormValues = {
  title: string
  responseDeadline: Dayjs | null
  description?: string
  vendorIds: number[]
  items: {
    productId: number
    quantity: number
    notes?: string
  }[]
  emailSubject?: string
  emailMessage?: string
}

type SelectOption = {
  label: string
  value: number
}

type RfqFormModalProps = {
  open: boolean
  onClose: () => void
  onSubmit?: (
    values: RfqFormValues,
    action: 'draft' | 'send',
  ) => void
  submitting?: boolean
  vendors: SelectOption[]
  products: SelectOption[]
}

function RfqFormModal({
  open,
  onClose,
  onSubmit,
  submitting = false,
  vendors,
  products,
}: RfqFormModalProps) {
  const [form] =
    Form.useForm<RfqFormValues>()

  const handleSubmit = async (
    action: 'draft' | 'send',
  ) => {
    try {
      const values =
        await form.validateFields()

      onSubmit?.(values, action)
    } catch {
      // Ant Design displays validation errors.
    }
  }

  return (
    <Modal
      title="Create Request for Quotation"
      open={open}
      onCancel={onClose}
      width={720}
      destroyOnHidden
      footer={
        <Flex
          justify="flex-end"
          gap={8}
        >
          <Button
            onClick={onClose}
            disabled={submitting}
          >
            Cancel
          </Button>

          <Button
            loading={submitting}
            onClick={() =>
              handleSubmit('draft')
            }
          >
            Save Draft
          </Button>

          <Button
            type="primary"
            loading={submitting}
            onClick={() =>
              handleSubmit('send')
            }
          >
            Send RFQ
          </Button>
        </Flex>
      }
    >
      <Form
        form={form}
        layout="vertical"
        initialValues={{
          vendorIds: [],
          items: [
            {
              productId: undefined,
              quantity: 1,
              notes: '',
            },
          ],
        }}
      >
        <Form.Item
          label="Title"
          name="title"
          rules={[
            {
              required: true,
              message:
                'Please enter an RFQ title',
            },
          ]}
        >
          <Input
            placeholder="e.g. Office Furniture"
          />
        </Form.Item>

        <Form.Item
          label="Response Deadline"
          name="responseDeadline"
          rules={[
            {
              required: true,
              message:
                'Please select a response deadline',
            },
          ]}
        >
          <DatePicker
            style={{ width: '100%' }}
            format="DD MMM YYYY"
          />
        </Form.Item>

        <Form.Item
          label="Vendors"
          name="vendorIds"
          rules={[
            {
              required: true,
              message:
                'Please select at least one vendor',
            },
          ]}
        >
          <Select
            mode="multiple"
            placeholder="Select vendors"
            showSearch
            optionFilterProp="label"
            options={vendors}
          />
        </Form.Item>

        <Form.Item
          label="Description"
          name="description"
        >
          <Input.TextArea
            rows={4}
            placeholder="Describe the requirements..."
          />
        </Form.Item>

        <div
          style={{
            marginBottom: 16,
          }}
        >
          <Text strong>
            Requested Items
          </Text>
        </div>

        <Form.List name="items">
          {(fields, { add, remove }) => (
            <>
              {fields.map(
                ({
                  key,
                  name,
                  ...restField
                }) => (
                  <Flex
                    key={key}
                    gap={8}
                    align="flex-start"
                    style={{
                      marginBottom: 12,
                    }}
                  >
                    <Form.Item
                      {...restField}
                      name={[
                        name,
                        'productId',
                      ]}
                      rules={[
                        {
                          required: true,
                          message:
                            'Select a product',
                        },
                      ]}
                      style={{
                        flex: 1,
                        marginBottom: 0,
                      }}
                    >
                      <Select
                        placeholder="Select product"
                        showSearch
                        optionFilterProp="label"
                        options={products}
                      />
                    </Form.Item>

                    <Form.Item
                      {...restField}
                      name={[
                        name,
                        'quantity',
                      ]}
                      rules={[
                        {
                          required: true,
                          message:
                            'Enter quantity',
                        },
                      ]}
                      style={{
                        width: 110,
                        marginBottom: 0,
                      }}
                    >
                      <InputNumber
                        min={1}
                        style={{
                          width: '100%',
                        }}
                        placeholder="Qty"
                      />
                    </Form.Item>

                    <Form.Item
                      {...restField}
                      name={[
                        name,
                        'notes',
                      ]}
                      style={{
                        flex: 1,
                        marginBottom: 0,
                      }}
                    >
                      <Input
                        placeholder="Notes"
                      />
                    </Form.Item>

                    <Button
                      danger
                      type="text"
                      icon={
                        <DeleteOutlined />
                      }
                      disabled={
                        fields.length === 1
                      }
                      onClick={() =>
                        remove(name)
                      }
                    />
                  </Flex>
                ),
              )}

              <Button
                type="dashed"
                block
                icon={<PlusOutlined />}
                onClick={() =>
                  add({
                    quantity: 1,
                    notes: '',
                  })
                }
              >
                Add Item
              </Button>
            </>
          )}
        </Form.List>

        <Form.Item
          label="Email Subject"
          name="emailSubject"
          style={{
            marginTop: 24,
          }}
        >
          <Input
            placeholder="Request for Quotation"
          />
        </Form.Item>

        <Form.Item
          label="Email Message"
          name="emailMessage"
        >
          <Input.TextArea
            rows={7}
            placeholder="Write the message sent to vendors..."
          />
        </Form.Item>
      </Form>
    </Modal>
  )
}

export default RfqFormModal