import axios from 'axios'
import { API_PATH } from './constants'

const api = axios.create({
  baseURL: API_PATH.PROD,
  withCredentials: false,
  headers: {
    'Content-Type': 'application/json',
  },
})

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      //Todo
    }
    return Promise.reject(error)
  }
)

export default api
