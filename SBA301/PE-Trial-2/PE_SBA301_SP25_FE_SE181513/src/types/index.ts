export type Car = {
  id: number;
  name: string;
  price: number;
  stock: number;
  country: Country;
  createdAt: string;
  updatedAt: string;
};

export type Country = {
  id: number;
  name: string;
};

export type User = {
  id: number;
  email: string;
  password: string;
  role: Role;
};

export type Role = "ADMIN" | "STAFF" | "MEMBER";

export type LoginRequest = {
  email: string;
  password: string;
};

export type LoginResponse = {
  accessToken: string;
  account: User;
};

export type CreateCarRequest = {
  name: string;
  price: number;
  stock: number;
  countryId: number;
};
