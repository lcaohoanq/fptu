import { useEffect, useState } from "react";
import { Button, Card, Col, Row } from "react-bootstrap";
import Container from "react-bootstrap/esm/Container";
import { Link } from "react-router";
import { orchidApi } from "../apis/api.config";

export default function HomeScreen() {
  const [data, setData] = useState([]);
  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await orchidApi.get("/");
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
              />
              <Card.Body>
                <Card.Title>{item.orchidName}</Card.Title>

                <div className="d-flex gap-2 mt-3">
                  <Link
                    to={`/detail/${item.id}`}
                    className="btn btn-outline-primary"
                  >
                    View Details
                  </Link>
                  <Button variant="success">
                    <i className="bi bi-cart-plus me-1"></i>Buy Now
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
