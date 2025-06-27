import axios from "axios";
import type {
  BlindBox,
  Category,
  LoginRequest,
  LoginResponse,
  CreateBlindBoxRequest,
} from "../types";

const API_BASE_URL = "http://localhost:8080/api";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Add request interceptor to include auth token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const authAPI = {
  login: async (credentials: LoginRequest): Promise<LoginResponse> => {
    const response = await api.post("/auth/login", credentials);
    return response.data.data;
  },
};

export const blindBoxAPI = {
  getAll: async (): Promise<BlindBox[]> => {
    const response = await api.get("/blind-boxes");
    return response.data.data;
  },

  create: async (blindBox: CreateBlindBoxRequest): Promise<BlindBox> => {
    const response = await api.post("/blind-boxes", blindBox);
    return response.data.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/blind-boxes/${id}`);
  },
};

export const categoryAPI = {
  getAll: async (): Promise<Category[]> => {
    const response = await api.get("/categories");
    return response.data.data;
  },
};
