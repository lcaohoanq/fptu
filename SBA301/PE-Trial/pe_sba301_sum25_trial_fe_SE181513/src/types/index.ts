export type BlindBox = {
  id: number;
  name: string;
  rarity: string;
  price: number;
  releaseDate: string;
  stock: number;
  categoryName: string;
  categoryId: number;
};

export type Category = {
  id: number;
  name: string;
  description: string;
  rarityLevel: string;
  priceRange: string;
};

export type User = {
  id: number;
  username: string;
  email: string;
  role: string;
  isActive: boolean;
};

export type LoginRequest = {
  email: string;
  password: string;
};

export type LoginResponse = {
  accessToken: string;
  account: User;
};

export type CreateBlindBoxRequest = {
  name: string;
  rarity: string;
  price: number;
  stock: number;
  categoryId: number;
};
