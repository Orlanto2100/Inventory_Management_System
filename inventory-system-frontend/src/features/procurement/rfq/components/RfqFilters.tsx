import { DatePicker, Flex, Input, Select } from 'antd'

function RfqFilters() {
  return (
    <Flex
      wrap
      gap={12}
    >
      <Input.Search
        placeholder="Search RFQ number or title"
        allowClear
        style={{ width: 280 }}
      />

      <Select
        placeholder="Status"
        allowClear
        style={{ width: 160 }}
        options={[
          { label: 'Draft', value: 'DRAFT' },
          { label: 'Sent', value: 'SENT' },
          { label: 'Open', value: 'OPEN' },
          {
            label: 'Responses Received',
            value: 'RESPONSES_RECEIVED',
          },
          {
            label: 'Under Review',
            value: 'UNDER_REVIEW',
          },
          { label: 'Awarded', value: 'AWARDED' },
          { label: 'Closed', value: 'CLOSED' },
          { label: 'Cancelled', value: 'CANCELLED' },
        ]}
      />

      <Select
        placeholder="Vendor"
        allowClear
        style={{ width: 200 }}
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

      <DatePicker placeholder="Response deadline" />
    </Flex>
  )
}

export default RfqFilters