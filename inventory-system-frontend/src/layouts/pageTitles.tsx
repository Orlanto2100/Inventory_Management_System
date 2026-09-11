const pageTitles: Record<string, string> = {
  '/': 'Dashboard',
  '/products': 'Products',
  '/inventory': 'Inventory',
  '/warehouses': 'Warehouses',
  '/locations': 'Locations',
  '/customers': 'Customers',
  '/sales-orders': 'Sales Orders',
  '/vendors': 'Vendors',
  '/purchase-orders': 'Purchase Orders',
  '/stock-movements': 'Stock Movements',
  '/users': 'Users',
};

export function getPageTitle(path: string): string {
  return pageTitles[path] ?? 'Inventory System';
}