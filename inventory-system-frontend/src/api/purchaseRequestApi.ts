import { apiRequest } from './client'

export type PurchaseRequestStatus =
  | 'DRAFT'
  | 'PENDING_APPROVAL'
  | 'APPROVED'
  | 'REJECTED'
  | 'PROCESSING'
  | 'COMPLETED'

export interface PurchaseRequestLineRequest {
  productId?: number
  description?: string
  quantity: number
  unit: string
  requiredDate?: string
  notes?: string
}

export interface CreatePurchaseRequestRequest {
  department: string
  requiredDate: string
  reason: string
  notes?: string
  warehouseId: number
  locationId?: number
  lines: PurchaseRequestLineRequest[]
}

export interface UpdatePurchaseRequestRequest {
  department: string
  requiredDate: string
  reason: string
  notes?: string
  warehouseId: number
  locationId?: number
  lines: PurchaseRequestLineRequest[]
}

export interface PurchaseRequestLineResponse {
  id: number
  productId: number | null
  productName: string | null
  description: string | null
  quantity: number
  unit: string
  requiredDate: string | null
  notes: string | null
}

export interface PurchaseRequestResponse {
  id: number
  requestNo: string
  requesterId: number
  requesterName: string
  department: string
  requestDate: string
  requiredDate: string
  reason: string
  notes: string | null
  warehouseId: number | null
  warehouseName: string | null
  locationId: number | null
  locationName: string | null
  status: PurchaseRequestStatus
  lines: PurchaseRequestLineResponse[]
}

const BASE_URL = '/purchase-requests'

export const purchaseRequestApi = {
  getAll(): Promise<PurchaseRequestResponse[]> {
    return apiRequest(BASE_URL)
  },

  getById(
    id: number
  ): Promise<PurchaseRequestResponse> {
    return apiRequest(
      `${BASE_URL}/${id}`
    )
  },

  create(
    request: CreatePurchaseRequestRequest
  ): Promise<PurchaseRequestResponse> {
    return apiRequest(BASE_URL, {
      method: 'POST',
      body: JSON.stringify(request),
    })
  },

  update(
    id: number,
    request: UpdatePurchaseRequestRequest
  ): Promise<PurchaseRequestResponse> {
    return apiRequest(
      `${BASE_URL}/${id}`,
      {
        method: 'PUT',
        body: JSON.stringify(request),
      }
    )
  },

  submit(
    id: number
  ): Promise<PurchaseRequestResponse> {
    return apiRequest(
      `${BASE_URL}/${id}/submit`,
      {
        method: 'POST',
      }
    )
  },

  approve(
    id: number
  ): Promise<PurchaseRequestResponse> {
    return apiRequest(
      `${BASE_URL}/${id}/approve`,
      {
        method: 'POST',
      }
    )
  },

  reject(
    id: number
  ): Promise<PurchaseRequestResponse> {
    return apiRequest(
      `${BASE_URL}/${id}/reject`,
      {
        method: 'POST',
      }
    )
  },

  process(
    id: number
  ): Promise<PurchaseRequestResponse> {
    return apiRequest(
      `${BASE_URL}/${id}/process`,
      {
        method: 'POST',
      }
    )
  },

  complete(
    id: number
  ): Promise<PurchaseRequestResponse> {
    return apiRequest(
      `${BASE_URL}/${id}/complete`,
      {
        method: 'POST',
      }
    )
  },
}