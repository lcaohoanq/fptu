import axios from "axios";
import type {
  LoginRequest,
  LoginResponse,
  CreateCarRequest,
  Car,
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
  getAll: async (): Promise<Car[]> => {
    const response = await api.get("/info-cars");
    return response.data.data;
  },

  create: async (car: CreateCarRequest): Promise<Car> => {
    const response = await api.post("/info-cars", car);
    return response.data.data;
  },

  update: async (id: number, car: Partial<Car>): Promise<Car> => {
    const response = await api.put(`/info-cars/${id}`, car);
    return response.data.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/info-cars/${id}`);
  },
};

export const countryAPI = {
  getAll: async (): Promise<Car[]> => {
    const response = await api.get("/countries");
    return response.data.data;
  },
};
