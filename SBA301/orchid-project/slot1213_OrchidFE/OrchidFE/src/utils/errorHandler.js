import toast from "react-hot-toast";

// Global error handler for API errors
export const handleApiError = (error, customMessage) => {
  console.error("API Error:", error);

  let errorMessage = customMessage || "Something went wrong";

  if (error.response) {
    // Server responded with error status
    errorMessage =
      error.response.data?.message || `Server Error: ${error.response.status}`;
  } else if (error.request) {
    // Network error - no response received
    errorMessage =
      "Cannot connect to server. Please check if the backend is running on http://localhost:8080";
  } else {
    // Other error
    errorMessage = error.message || "An unexpected error occurred";
  }

  toast.error(errorMessage, {
    duration: 5000,
    position: 'top-center',
  });
  return errorMessage;
};

// Check if API server is available
export const checkApiConnection = async (apiInstance) => {
  try {
    await apiInstance.get("/health-check");
    return true;
  } catch (error) {
    console.error("API Server is not available:", error);
    toast.error(
      "Cannot connect to server. Please check if the backend is running on http://localhost:8080",
    );
    return false;
  }
};

export default { handleApiError, checkApiConnection };
