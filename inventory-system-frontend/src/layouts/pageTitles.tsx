const pageTitles: Record<string, string> = {
  '/': 'navigation.dashboard',

  '/products': 'navigation.products',
  '/inventory': 'navigation.inventory',
  '/warehouses': 'navigation.warehouses',
  '/locations': 'navigation.locations',

  '/customers': 'navigation.customers',
  '/customer-quotations': 'navigation.customerQuotations',
  '/sales-orders': 'navigation.salesOrders',
  '/deliveries': 'navigation.deliveries',
  '/customer-invoices': 'navigation.customerInvoices',

  '/vendors': 'navigation.vendors',
  '/purchase-requests': 'navigation.purchaseRequests',
  '/rfqs': 'navigation.rfqs',
  '/vendor-quotations': 'navigation.vendorQuotations',
  '/purchase-orders': 'navigation.purchaseOrders',
  '/receipts': 'navigation.receipts',
  '/vendor-invoices': 'navigation.vendorInvoices',

  '/stock-movements': 'navigation.stockMovements',

  '/users': 'navigation.users',
}

export function getPageTitle(path: string): string {
  return pageTitles[path] ?? 'common.inventorySystem'
}