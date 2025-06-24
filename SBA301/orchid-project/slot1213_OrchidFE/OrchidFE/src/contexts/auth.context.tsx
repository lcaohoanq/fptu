import React, {
  createContext,
  ReactNode,
  useContext,
  useEffect,
  useState,
} from "react";
import { loginApi } from "../apis/auth.api";
import { LoginRes } from "../types";

interface AuthContextProps {
  isAuthenticated: boolean;
  user: any | null;
  login: (email: string, password: string) => Promise<LoginRes>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState<any | null>(null);

  // Check if user is authenticated on initial load
  useEffect(() => {
    const checkAuth = () => {
      const token = localStorage.getItem("access_token");
      if (token) {
        // You might want to verify the token validity here
        setIsAuthenticated(true);

        // You could decode JWT to get basic user info
        // For demonstration, we'll just set a placeholder user object
        setUser({
          email: localStorage.getItem("user_email") || "",
          role: localStorage.getItem("user_role") || "User",
        });
      }
    };

    checkAuth();
  }, []);

  const login = async (email: string, password: string): Promise<LoginRes> => {
    const response = await loginApi(email, password);

    // Store token data
    localStorage.setItem("access_token", response.data.token.access_token);
    localStorage.setItem("refresh_token", response.data.token.refresh_token);
    localStorage.setItem("token_expires", response.data.token.expires);
    localStorage.setItem("token_type", response.data.token.token_type);
    localStorage.setItem("user_email", email);
    localStorage.setItem(
      "user_role",
      response.data.account.role.name || "User",
    );

    setIsAuthenticated(true);
    setUser({ email, role: response.data.account.role.name || "User" });

    return response;
  };

  const logout = () => {
    // Remove all auth related data from localStorage
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    localStorage.removeItem("token_expires");
    localStorage.removeItem("token_type");

    // Keep remembered_email if needed

    setIsAuthenticated(false);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
