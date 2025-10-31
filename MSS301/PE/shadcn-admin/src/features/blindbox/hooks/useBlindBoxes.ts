import { useQuery, useQueryClient } from '@tanstack/react-query'
import api from '@/config/api'
import type { ApiResponse } from '@/config/api.type'
// ...imports consolidated above
import { toast } from 'sonner'
import type { BlindBox } from '../types/blindbox.types'

export const BLINDBOX_QUERY_KEYS = {
  all: ['blindboxes'] as const,
  lists: () => [...BLINDBOX_QUERY_KEYS.all, 'list'] as const,
  list: (filters: string) =>
    [...BLINDBOX_QUERY_KEYS.lists(), { filters }] as const,
  details: () => [...BLINDBOX_QUERY_KEYS.all, 'detail'] as const,
  detail: (userId: string) =>
    [...BLINDBOX_QUERY_KEYS.details(), userId] as const,
}

export const useBlindBoxes = () => {
  return useQuery<BlindBox[]>({
    queryKey: BLINDBOX_QUERY_KEYS.lists(),
    queryFn: fetchBlindBoxes,
  })
}

// Fetches list of blind boxes from API and returns the array of BlindBox
export const fetchBlindBoxes = async (): Promise<BlindBox[]> => {
  try {
    const response = await api.get<ApiResponse<BlindBox[]>>('/blindboxes')
    // response.data is ApiResponse<BlindBox[]>
    return response.data.data
  } catch (err) {
    // If API is unreachable or returns error, return empty array and warn.
    // You can replace this with mocked data during development if needed.
    // eslint-disable-next-line no-console
    console.warn('Failed to fetch blindboxes, returning empty list', err)
    return []
  }
}

// Update a blindbox by id with partial payload
export const updateBlindBox = async (
  id: number | string,
  payload: Partial<BlindBox>
): Promise<BlindBox> => {
  const response = await api.put<ApiResponse<BlindBox>>(
    `/blindboxes/${id}`,
    payload
  )
  return response.data.data
}

// Delete a blindbox by id
export const deleteBlindBox = async (id: number | string): Promise<void> => {
  await api.delete(`/blindboxes/${id}`)
}

export const useUpdateBlindBox = () => {
  const qc = useQueryClient()

  const mutateAsync = async (
    id: number | string,
    payload: Partial<BlindBox>
  ) => {
    try {
      const res = await updateBlindBox(id, payload)
      await qc.invalidateQueries({ queryKey: BLINDBOX_QUERY_KEYS.lists() })
      toast.success('Blind box updated')
      return res
    } catch (err) {
      toast.error('Failed to update blind box')
      throw err
    }
  }

  const mutate = (id: number | string, payload: Partial<BlindBox>) => {
    mutateAsync(id, payload).catch(() => {})
  }

  return { mutate, mutateAsync }
}

export const useDeleteBlindBox = () => {
  const qc = useQueryClient()

  const mutateAsync = async (id: number | string) => {
    try {
      await deleteBlindBox(id)
      await qc.invalidateQueries({ queryKey: BLINDBOX_QUERY_KEYS.lists() })
      toast.success('Blind box deleted')
    } catch (err) {
      toast.error('Failed to delete blind box')
      throw err
    }
  }

  const mutate = (id: number | string) => {
    mutateAsync(id).catch(() => {})
  }

  return { mutate, mutateAsync }
}
