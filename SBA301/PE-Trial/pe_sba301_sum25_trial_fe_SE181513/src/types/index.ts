export interface BlindBox {
  id: number;
  name: string;
  rarity: string;
  price: number;
  releaseDate: string;
  stock: number;
  categoryName: string;
  categoryId: number;
}

export interface Category {
  id: number;
  name: string;
  description: string;
  rarityLevel: string;
  priceRange: string;
}

export interface User {
  id: number;
  username: string;
  email: string;
  role: string;
  isActive: boolean;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
  account: User;
}

export interface CreateBlindBoxRequest {
  name: string;
  rarity: string;
  price: number;
  stock: number;
  categoryId: number;
}
