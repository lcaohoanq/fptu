import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/auth.context";
import { Spinner } from "react-bootstrap";
import PropTypes from "prop-types";

const ProtectedRoute = ({ children }) => {
  const { isAuthenticated, isLoading } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      navigate("/login");
    }
  }, [isAuthenticated, isLoading, navigate]);

  // Show loading spinner while checking authentication
  if (isLoading) {
    return (
      <div className="d-flex justify-content-center align-items-center vh-100">
        <Spinner animation="border" variant="primary" role="status">
          <span className="visually-hidden">Checking authentication...</span>
        </Spinner>
      </div>
    );
  }

  // If not loading and not authenticated, don't render anything 
  // (useEffect will handle redirect)
  if (!isAuthenticated) {
    return null;
  }

  return children;
};

ProtectedRoute.propTypes = {
  children: PropTypes.node.isRequired,
};

export default ProtectedRoute;
