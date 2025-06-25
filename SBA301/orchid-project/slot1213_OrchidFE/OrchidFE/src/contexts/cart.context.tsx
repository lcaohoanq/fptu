import React, {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from "react";
import toast from "react-hot-toast";

interface CartItem {
  id: string;
  orchidName: string;
  image: string;
  price: number;
  quantity: number;
  isNatural: boolean;
}

interface CartContextProps {
  cartItems: CartItem[];
  cartCount: number;
  cartTotal: number;
  addToCart: (orchid: any, quantity?: number) => void;
  removeFromCart: (id: string) => void;
  updateQuantity: (id: string, quantity: number) => void;
  clearCart: () => void;
  isInCart: (id: string) => boolean;
}

const CartContext = createContext<CartContextProps | undefined>(undefined);

export const CartProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [cartItems, setCartItems] = useState<CartItem[]>([]);

  // Load cart from localStorage on mount
  useEffect(() => {
    const savedCart = localStorage.getItem("cart");
    if (savedCart) {
      try {
        const parsedCart = JSON.parse(savedCart);
        setCartItems(parsedCart);
      } catch (error) {
        console.error("Error loading cart from localStorage:", error);
        localStorage.removeItem("cart");
      }
    }
  }, []);

  // Save cart to localStorage whenever cartItems change
  useEffect(() => {
    localStorage.setItem("cart", JSON.stringify(cartItems));
  }, [cartItems]);

  // Calculate cart count
  const cartCount = cartItems.reduce((total, item) => total + item.quantity, 0);

  // Calculate cart total
  const cartTotal = cartItems.reduce(
    (total, item) => total + item.price * item.quantity,
    0,
  );

  // Add item to cart
  const addToCart = (orchid: any, quantity = 1) => {
    setCartItems((prevItems) => {
      const existingItem = prevItems.find((item) => item.id === orchid.id);

      if (existingItem) {
        // Update quantity if item already exists
        toast.success(`Updated ${orchid.orchidName} quantity in cart`);
        return prevItems.map((item) =>
          item.id === orchid.id
            ? { ...item, quantity: item.quantity + quantity }
            : item,
        );
      } else {
        // Add new item to cart
        const newItem: CartItem = {
          id: orchid.id,
          orchidName: orchid.orchidName,
          image: orchid.image,
          price: generatePrice(orchid), // Generate a price for the orchid
          quantity,
          isNatural: orchid.isNatural,
        };

        toast.success(`Added ${orchid.orchidName} to cart`);
        return [...prevItems, newItem];
      }
    });
  };

  // Remove item from cart
  const removeFromCart = (id: string) => {
    setCartItems((prevItems) => {
      const item = prevItems.find((item) => item.id === id);
      if (item) {
        toast.success(`Removed ${item.orchidName} from cart`);
      }
      return prevItems.filter((item) => item.id !== id);
    });
  };

  // Update item quantity
  const updateQuantity = (id: string, quantity: number) => {
    if (quantity < 1) {
      removeFromCart(id);
      return;
    }

    setCartItems((prevItems) =>
      prevItems.map((item) => (item.id === id ? { ...item, quantity } : item)),
    );
  };

  // Clear entire cart
  const clearCart = () => {
    setCartItems([]);
    toast.success("Cart cleared");
  };

  // Check if item is in cart
  const isInCart = (id: string) => {
    return cartItems.some((item) => item.id === id);
  };

  // Generate a price for orchid (since your API doesn't seem to have prices)
  const generatePrice = (orchid: any) => {
    // Generate price based on some factors
    let basePrice = 25; // Base price

    // Natural orchids are more expensive
    if (orchid.isNatural) {
      basePrice *= 1.5;
    }

    // Add some randomness but keep it consistent per orchid
    const hash = orchid.id.split("").reduce((a, b) => {
      a = (a << 5) - a + b.charCodeAt(0);
      return a & a;
    }, 0);

    const variation = (Math.abs(hash) % 30) + 10; // 10-40 variation
    return parseFloat((basePrice + variation).toFixed(2));
  };

  return (
    <CartContext.Provider
      value={{
        cartItems,
        cartCount,
        cartTotal,
        addToCart,
        removeFromCart,
        updateQuantity,
        clearCart,
        isInCart,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => {
  const context = useContext(CartContext);
  if (context === undefined) {
    throw new Error("useCart must be used within a CartProvider");
  }
  return context;
};
