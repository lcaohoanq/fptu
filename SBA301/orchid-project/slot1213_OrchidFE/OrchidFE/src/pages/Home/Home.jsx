import { useEffect, useState } from "react";
import { Button, Card, Col, Row } from "react-bootstrap";
import Container from "react-bootstrap/esm/Container";
import { Link } from "react-router";
import { orchidApi } from "../../apis/api.config";
import { useCart } from "../../contexts/cart.context";

export default function HomeScreen() {
  const [data, setData] = useState([]);
  const { addToCart, isInCart } = useCart();
  
  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await orchidApi.get("/orchids");
      const sortedData = response.data.sort(
        (a, b) => parseInt(b.empId) - parseInt(a.empId),
      );
      setData(sortedData);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  return (
    <Container>
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
                    onClick={() => addToCart(item)}
                    disabled={isInCart(item.id)}
                  >
                    <i className={`bi ${isInCart(item.id) ? 'bi-check-circle' : 'bi-cart-plus'} me-1`}></i>
                    {isInCart(item.id) ? 'Added' : 'Add to Cart'}
                  </Button>
                </div>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
  );
}
