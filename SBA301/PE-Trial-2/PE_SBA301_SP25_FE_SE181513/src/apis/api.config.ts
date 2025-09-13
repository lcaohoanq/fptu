import axios from "axios";
import toast from "react-hot-toast";
import type {
  Car,
  CreateCarRequest,
  LoginRequest,
  LoginResponse,
} from "../types";

const API_CONFIG = {
  API_BASE_URL: "http://localhost:8080/api",
  API_PATH: {
    LOGIN: "/accounts/login",
    INFOS_CARS: "/infocars",
    COUNTRIES: "/countries",
  },
};

const api = axios.create({
  baseURL: API_CONFIG.API_BASE_URL,
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

// Add response interceptor for error handling
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    // Handle validation errors and show field-specific messages
    if (error.response?.status === 400 && error.response?.data?.fieldErrors) {
      const fieldErrors = error.response.data.fieldErrors;

      // Show individual field errors with better formatting
      Object.entries(fieldErrors).forEach(([field, message]) => {
        const fieldName = field.charAt(0).toUpperCase() + field.slice(1);
        toast.error(`${fieldName}: ${message}`, {
          duration: 6000,
          style: {
            background: "#ef4444",
            color: "#fff",
            zIndex: 9999,
            maxWidth: "400px",
            padding: "16px",
            borderRadius: "8px",
            boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
          },
          position: "top-right",
        });
      });
    } else if (error.response?.data?.message) {
      // Show general error message
      toast.error(error.response.data.message, {
        duration: 4000,
        style: {
          background: "#ef4444",
          color: "#fff",
          zIndex: 9999,
          maxWidth: "400px",
          padding: "16px",
          borderRadius: "8px",
          boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
        },
        position: "top-right",
      });
    } else if (error.message) {
      // Show network or other errors
      toast.error(error.message, {
        duration: 4000,
        style: {
          background: "#ef4444",
          color: "#fff",
          zIndex: 9999,
          maxWidth: "400px",
          padding: "16px",
          borderRadius: "8px",
          boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
        },
        position: "top-right",
      });
    } else {
      // Fallback error message
      toast.error("An unexpected error occurred", {
        duration: 4000,
        style: {
          background: "#ef4444",
          color: "#fff",
          zIndex: 9999,
          maxWidth: "400px",
          padding: "16px",
          borderRadius: "8px",
          boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
        },
        position: "top-right",
      });
    }

    return Promise.reject(error);
  }
);

export const authAPI = {
  login: async (credentials: LoginRequest): Promise<LoginResponse> => {
    const response = await api.post(API_CONFIG.API_PATH.LOGIN, credentials);
    return response.data.data;
  },
};

export const carAPI = {
  getAll: async (): Promise<Car[]> => {
    const response = await api.get(API_CONFIG.API_PATH.INFOS_CARS);
    return response.data.data;
  },

  create: async (car: CreateCarRequest): Promise<Car> => {
    const response = await api.post(API_CONFIG.API_PATH.INFOS_CARS, car);
    toast.success("Blind box created successfully!", {
      duration: 3000,
      style: {
        background: "#10b981",
        color: "#fff",
        zIndex: 9999,
        maxWidth: "400px",
        padding: "16px",
        borderRadius: "8px",
        boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
      },
      position: "top-right",
    });
    return response.data.data;
  },

  update: async (id: number, car: Partial<Car>): Promise<Car> => {
    const response = await api.put(
      `${API_CONFIG.API_PATH.INFOS_CARS}/${id}`,
      car
    );
    toast.success("Blind box updated successfully!", {
      duration: 3000,
      style: {
        background: "#10b981",
        color: "#fff",
        zIndex: 9999,
        maxWidth: "400px",
        padding: "16px",
        borderRadius: "8px",
        boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
      },
      position: "top-right",
    });
    return response.data.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`${API_CONFIG.API_PATH.INFOS_CARS}/${id}`);
    toast.success("Blind box deleted successfully!", {
      duration: 3000,
      style: {
        background: "#10b981",
        color: "#fff",
        zIndex: 9999,
        maxWidth: "400px",
        padding: "16px",
        borderRadius: "8px",
        boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
      },
      position: "top-right",
    });
  },
};

export const countryAPI = {
  getAll: async (): Promise<Car[]> => {
    const response = await api.get(API_CONFIG.API_PATH.COUNTRIES);
    return response.data.data;
  },
};
