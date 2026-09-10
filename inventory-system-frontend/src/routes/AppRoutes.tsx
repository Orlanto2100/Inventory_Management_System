import { Routes, Route } from 'react-router-dom'
import MainLayout from '../layouts/MainLayout'

import DashboardPage from '../features/dashboard/pages/DashboardPage'
import LoginPage from '../features/auth/pages/LoginPage'
import ProductPage from '../features/products/pages/ProductPage'
import CustomerPage from '../features/customers/pages/CustomerPage'
import VendorPage from '../features/vendors/pages/VendorPage'
import WarehousePage from '../features/warehouses/pages/WarehousePage'
import LocationPage from '../features/locations/pages/LocationPage'
import UserPage from '../features/users/pages/UserPage'
import InventoryPage from '../features/inventory/pages/InventoryPage'
import PurchaseOrderPage from '../features/purchase-orders/pages/PurchaseOrderPage'
import SalesOrderPage from '../features/sales-orders/pages/SalesOrderPage'
import StockMovementPage from '../features/stock-movements/pages/StockMovementPage'

export default function AppRoutes() {
  return (
    <Routes>
      {/* Authentication */}
      <Route path="/login" element={<LoginPage />} />

      {/* Main Application */}
      <Route element={<MainLayout />}>
        {/* Dashboard */}
        <Route path="/" element={<DashboardPage />} />

        {/* Inventory */}
        <Route path="/products" element={<ProductPage />} />
        <Route path="/inventory" element={<InventoryPage />} />
        <Route path="/warehouses" element={<WarehousePage />} />
        <Route path="/locations" element={<LocationPage />} />

        {/* Sales */}
        <Route path="/customers" element={<CustomerPage />} />
        <Route path="/sales-orders" element={<SalesOrderPage />} />

        {/* Purchasing */}
        <Route path="/vendors" element={<VendorPage />} />
        <Route
          path="/purchase-orders"
          element={<PurchaseOrderPage />}
        />

        {/* Operations */}
        <Route
          path="/stock-movements"
          element={<StockMovementPage />}
        />

        {/* Administration */}
        <Route path="/users" element={<UserPage />} />
      </Route>
    </Routes>
  )
}