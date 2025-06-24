// Define interfaces for API requests and responses
export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  message: string;
  data: {
    token: {
      id: string;
      access_token: string;
      refresh_token: string;
      token_type: string;
      expires: string;
      expires_refresh_token: string;
      is_mobile: boolean;
    };
  };
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
}

export interface RegisterResponse {
  code: number;
  message: string;
  data: null;
}

const API_BASE_URL = "http://localhost:8080/api/v1"; // Replace with your actual API URL

// Login API function
export const loginApi = async (
  email: string,
  password: string,
): Promise<LoginResponse> => {
  const response = await fetch(`${API_BASE_URL}/auth/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email, password }),
  });

  if (!response.ok) {
    throw new Error("Login failed");
  }

  return response.json();
};

// Register API function
export const registerApi = async (
  name: string,
  email: string,
  password: string,
): Promise<RegisterResponse> => {
  const response = await fetch(`${API_BASE_URL}/auth/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ name, email, password }),
  });

  if (!response.ok) {
    throw new Error("Registration failed");
  }

  return response.json();
};
