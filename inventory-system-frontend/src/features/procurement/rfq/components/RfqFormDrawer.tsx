import {
  Button,
  DatePicker,
  Drawer,
  Flex,
  Form,
  Input,
  Select,
} from 'antd'

type RfqFormDrawerProps = {
  open: boolean
  onClose: () => void
}

function RfqFormDrawer({
  open,
  onClose,
}: RfqFormDrawerProps) {
  return (
    <Drawer
      title="Create Request for Quotation"
      open={open}
      onClose={onClose}
      width={720}
      footer={
        <Flex justify="flex-end" gap={8}>
          <Button onClick={onClose}>
            Cancel
          </Button>

          <Button>
            Save Draft
          </Button>

          <Button type="primary">
            Send RFQ
          </Button>
        </Flex>
      }
    >
      <Form layout="vertical">
        <Form.Item
          label="Title"
          name="title"
          rules={[
            {
              required: true,
              message: 'Please enter an RFQ title',
            },
          ]}
        >
          <Input placeholder="e.g. Office Furniture" />
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
          label="Description"
          name="description"
        >
          <Input.TextArea
            rows={4}
            placeholder="Describe the requirements..."
          />
        </Form.Item>

        <Form.Item
          label="Vendors"
          name="vendors"
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
            options={[
              {
                label: 'ABC Office Supply',
                value: 'abc',
              },
              {
                label: 'Global Furniture',
                value: 'global',
              },
              {
                label: 'Modern Office Ltd',
                value: 'modern',
              },
            ]}
          />
        </Form.Item>

        <Form.Item
          label="Email Subject"
          name="emailSubject"
        >
          <Input
            placeholder="Request for Quotation - RFQ-001"
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
    </Drawer>
  )
}

export default RfqFormDrawer