import { Routes, Route } from 'react-router-dom'
import MainLayout from '../layouts/MainLayout'
import ScrollToTop from './ScrollToTop'

// Dashboard
import DashboardPage from '../features/dashboard/pages/DashboardPage'

// Authentication
import LoginPage from '../features/auth/pages/LoginPage'

// Inventory
import ProductPage from '../features/products/pages/ProductPage'
import InventoryPage from '../features/inventory/pages/InventoryPage'
import WarehousePage from '../features/warehouses/pages/WarehousePage'
import LocationPage from '../features/locations/pages/LocationPage'
import StockMovementPage from '../features/stock-movements/pages/StockMovementPage'

// Purchasing
import VendorPage from '../features/vendors/pages/VendorPage'
import PurchaseRequestPage from '../features/purchase-requests/pages/PurchaseRequestPage'
import RfqPage from '../features/procurement/rfq/pages/RfqPage'
import VendorQuotationPage from '../features/vendor-quotations/pages/VendorQuotationPage.tsx'
import PurchaseOrderPage from '../features/purchase-orders/pages/PurchaseOrderPage'
import ReceiptPage from '../features/receipts/pages/ReceiptPage'
import VendorInvoicePage from '../features/vendor-invoices/pages/VendorInvoicePage'

// Sales
import CustomerPage from '../features/customers/pages/CustomerPage'
import CustomerQuotationPage from '../features/customer-quotations/pages/CustomerQuotationPage'
import SalesOrderPage from '../features/sales-orders/pages/SalesOrderPage'
import DeliveryPage from '../features/deliveries/pages/DeliveryPage.tsx'
import CustomerInvoicePage from '../features/customer-invoices/pages/CustomerInvoicePage'

// Administration
import UserPage from '../features/users/pages/UserPage'

export default function AppRoutes() {
  return (
    <>
      <ScrollToTop />

      <Routes>
        {/* Authentication */}
        <Route
          path="/login"
          element={<LoginPage />}
        />

        {/* Main Application */}
        <Route element={<MainLayout />}>

          {/* Dashboard */}
          <Route
            path="/"
            element={<DashboardPage />}
          />

          {/* Inventory */}
          <Route
            path="/products"
            element={<ProductPage />}
          />
          <Route
            path="/inventory"
            element={<InventoryPage />}
          />
          <Route
            path="/warehouses"
            element={<WarehousePage />}
          />
          <Route
            path="/locations"
            element={<LocationPage />}
          />
          <Route
            path="/stock-movements"
            element={<StockMovementPage />}
          />

          {/* Purchasing */}
          <Route
            path="/vendors"
            element={<VendorPage />}
          />
          <Route
            path="/purchase-requests"
            element={<PurchaseRequestPage />}
          />
          <Route
            path="/rfqs"
            element={<RfqPage />}
          />
          <Route
            path="/vendor-quotations"
            element={<VendorQuotationPage />}
          />
          <Route
            path="/purchase-orders"
            element={<PurchaseOrderPage />}
          />
          <Route
            path="/receipts"
            element={<ReceiptPage />}
          />
          <Route
            path="/vendor-invoices"
            element={<VendorInvoicePage />}
          />

          {/* Sales */}
          <Route
            path="/customers"
            element={<CustomerPage />}
          />
          <Route
            path="/customer-quotations"
            element={<CustomerQuotationPage />}
          />
          <Route
            path="/sales-orders"
            element={<SalesOrderPage />}
          />
          <Route
            path="/deliveries"
            element={<DeliveryPage />}
          />
          <Route
            path="/customer-invoices"
            element={<CustomerInvoicePage />}
          />

          {/* Administration */}
          <Route
            path="/users"
            element={<UserPage />}
          />

        </Route>
      </Routes>
    </>
  )
}