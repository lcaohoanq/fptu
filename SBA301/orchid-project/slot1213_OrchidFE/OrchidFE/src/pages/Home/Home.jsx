import { useEffect, useState } from "react";
import { Button, Card, Col, Row, Alert, Spinner } from "react-bootstrap";
import Container from "react-bootstrap/esm/Container";
import { Link, useNavigate } from "react-router";
import { orchidApi } from "../../apis/api.config";
import { useCart } from "../../contexts/cart.context";
import { handleApiError } from "../../utils/errorHandler";

export default function HomeScreen() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { addToCart, isInCart } = useCart();
  const navigate = useNavigate();
  
  useEffect(() => {
    fetchData();
  }, []);
  const fetchData = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await orchidApi.get("/orchids");
      const sortedData = response.data.sort(
        (a, b) => parseInt(b.empId) - parseInt(a.empId),
      );
      setData(sortedData);
    } catch (error) {
      const errorMessage = handleApiError(error, "Failed to load orchids");
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };
  return (
    <Container>
      {/* Loading State */}
      {loading && (
        <div className="text-center py-5">
          <Spinner animation="border" variant="primary" />
          <p className="mt-2">Loading orchids...</p>
        </div>
      )}

      {/* Error State */}
      {error && (
        <Alert variant="danger" className="mt-3">
          <Alert.Heading>
            <i className="bi bi-exclamation-triangle me-2"></i>
            Unable to Load Orchids
          </Alert.Heading>
          <p>{error}</p>
          <p className="mb-3">
            <strong>Please check:</strong>
          </p>
          <ul>
            <li>Backend server is running on <code>http://localhost:8080</code></li>
            <li>Your internet connection is working</li>
            <li>CORS is properly configured on the backend</li>
          </ul>
          <hr />
          <div className="d-flex gap-2">
            <Button variant="outline-danger" onClick={fetchData}>
              <i className="bi bi-arrow-clockwise me-2"></i>
              Try Again
            </Button>
            <Button 
              variant="outline-secondary" 
              onClick={() => window.location.reload()}
            >
              <i className="bi bi-arrow-clockwise me-2"></i>
              Reload Page
            </Button>
          </div>
        </Alert>
      )}      {/* Success State - Show orchids */}
      {!loading && !error && data.length > 0 && (
        <Row className="mt-3 g-4">
        {data.map((item) => (
          <Col md={4} key={item.id}>
            <Card>
              <Card.Img
                variant="top"
                src={item.image}
                alt="example"
                height={350}
              />              <Card.Body>
                <Card.Title>{item.orchidName}</Card.Title>
                
                {/* Display price and natural badge */}
                <div className="mb-2">
                  {item.isNatural ? (
                    <span className="badge bg-success me-2">Natural</span>
                  ) : (
                    <span className="badge bg-warning me-2">Industry</span>
                  )}
                </div>

                <div className="d-flex gap-2 mt-3">
                  <Link
                    to={`/detail/${item.id}`}
                    className="btn btn-outline-primary flex-grow-1"
                  >
                    View Details
                  </Link>
                  <Button 
                    variant={isInCart(item.id) ? "outline-success" : "success"}
                    onClick={() => addToCart(item, 1, () => navigate('/login'))}
                    disabled={isInCart(item.id)}
                    title={isInCart(item.id) ? "Already in cart" : "Add to cart"}
                  >
                    <i className={`bi ${isInCart(item.id) ? 'bi-check-circle' : 'bi-cart-plus'} me-1`}></i>
                    {isInCart(item.id) ? 'Added' : 'Add'}
                  </Button>
                </div>
              </Card.Body>
            </Card>
          </Col>        ))}        </Row>
      )}

      {/* No Data State */}
      {!loading && !error && data.length === 0 && (
        <div className="text-center py-5">
          <i className="bi bi-flower1 fs-1 text-muted mb-3 d-block"></i>
          <h3>No Orchids Found</h3>
          <p className="text-muted">There are no orchids available at the moment.</p>
          <Button variant="primary" onClick={fetchData}>
            <i className="bi bi-arrow-clockwise me-2"></i>
            Refresh
          </Button>
        </div>
      )}
    </Container>
  );
}
