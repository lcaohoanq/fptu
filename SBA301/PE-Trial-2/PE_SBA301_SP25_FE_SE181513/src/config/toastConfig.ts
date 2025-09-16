// Base toast style configuration
const baseToastStyle = {
  zIndex: 9999,
  maxWidth: "400px",
  padding: "16px",
  borderRadius: "8px",
  boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
  color: "#fff",
} as const;

// Toast configuration objects
export const toastConfig = {
  error: {
    duration: 4000,
    style: {
      ...baseToastStyle,
      background: "#ef4444",
    },
    position: "top-right" as const,
  },

  errorLong: {
    duration: 6000,
    style: {
      ...baseToastStyle,
      background: "#ef4444",
    },
    position: "top-right" as const,
  },

  success: {
    duration: 3000,
    style: {
      ...baseToastStyle,
      background: "#10b981",
    },
    position: "top-right" as const,
  },
} as const;

// Type for toast configuration
export type ToastConfigType = typeof toastConfig;
