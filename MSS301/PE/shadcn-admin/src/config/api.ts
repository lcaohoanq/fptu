import axios from 'axios'
import { API_PATH } from './constants'

const api = axios.create({
  baseURL: API_PATH.PROD,
  withCredentials: true, // Enable credentials for cross-origin requests
  timeout: 30000, // 30 second timeout
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor to add auth token to requests
api.interceptors.request.use(
  (config) => {
    // Get token from cookie (matches auth store implementation)
    const cookieValue = document.cookie
      .split('; ')
      .find((row) => row.startsWith('thisisjustarandomstring='))
      ?.split('=')[1]

    if (cookieValue) {
      try {
        const token = JSON.parse(decodeURIComponent(cookieValue))
        if (token) {
          config.headers.Authorization = `Bearer ${token}`
        }
      } catch {
        // Failed to parse token, skip
      }
    }

    return config
  },
  (error) => Promise.reject(error)
)

// Response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Handle unauthorized - could redirect to login
      // window.location.href = '/sign-in'
    }

    // Log error details for debugging
    if (error.code === 'ECONNABORTED') {
      // eslint-disable-next-line no-console
      console.error('Request timeout:', error.config?.url)
    } else if (error.code === 'ERR_NETWORK') {
      // eslint-disable-next-line no-console
      console.error('Network error - check if backend is running')
    }

    return Promise.reject(error)
  }
)

export default api
