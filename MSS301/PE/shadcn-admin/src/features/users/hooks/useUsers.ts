import { useQuery } from '@tanstack/react-query'
import api from '@/config/api'
import type { User } from '../types/user.types'

export const USER_QUERY_KEYS = {
  all: ['users'] as const,
  lists: () => [...USER_QUERY_KEYS.all, 'list'] as const,
  list: (filters: string) => [...USER_QUERY_KEYS.lists(), { filters }] as const,
  details: () => [...USER_QUERY_KEYS.all, 'detail'] as const,
  detail: (userId: string) => [...USER_QUERY_KEYS.details(), userId] as const,
}

export const useUsers = () => {
  return useQuery<User[]>({
    queryKey: USER_QUERY_KEYS.lists(),
    queryFn: fetchUsers,
  })
}

export const fetchUsers = async (): Promise<User[]> => {
  const response = await api.get('/auth/users/test-only')
  return response.data.data || []
}
