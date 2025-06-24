export type Orchid = {
  id: string;
  orchidName: string;
  isNatural: boolean;
  description: string;
  categoryId: string;
  isAttractive: boolean;
  image: string;
};

export type ApiResponse<T> = {
  message: string;
  data: T;
};

export type Employee = {
  id: string;
  url: string;
  name: string;
  gender: string;
  designation: string;
};

export type LoginRes = ApiResponse<{
  token: {
    id: string;
    access_token: string;
    refresh_token: string;
    token_type: string;
    expires: string;
    expires_refresh_token: string;
    is_mobile: boolean;
  };
  account: {
    id: string;
    role: {
      id: string;
      name: RoleName;
    };
  };
}>;

export type RoleName = "Admin" | "User" | "Manager";
export type Account = {
  id: string;
  name: string;
  email: string;
  password: string;
};

export type Category = {
  id: string;
  name: string;
};

export type Order = {
  id: string;
  totalAmount: number;
  orderDate: string;
  orderStatus: "PENDING" | "PROCESSING" | "COMPLETED" | "CANCELLED";
  acountId: string;
};

export type OrderDetail = {
  id: string;
  orderId: string;
  orchidId: string;
  quantity: number;
  price: number;
};
