import { useEffect, useState } from "react";
import { Badge, Button, Card, Col, Row, Spinner, Table } from "react-bootstrap";
import Container from "react-bootstrap/esm/Container";
import { Link } from "react-router-dom";
import { orchidApi } from "../../../apis/api.config";

export default function Orders() {
  const [loading, setLoading] = useState(false);
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    setLoading(true);
    try {
      const response = await orchidApi.get("/orders");
      setOrders(response.data.data || []);
    } catch (error) {
      console.error("Error fetching orders:", error);
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (timestamp) => {
    return new Date(timestamp).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const getStatusBadge = (status) => {
    const statusColors = {
      PENDING: "warning",
      PROCESSING: "info", 
      COMPLETED: "success",
      CANCELLED: "danger"
    };
    return (
      <Badge bg={statusColors[status] || "secondary"}>
        {status}
      </Badge>
    );
  };

  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center my-5">
        <Spinner animation="border" variant="primary" role="status">
          <span className="visually-hidden">Loading...</span>
        </Spinner>
      </div>
    );
  }

  if (orders.length === 0) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <i className="bi bi-receipt fs-1 text-muted mb-3 d-block"></i>
          <h3>No orders found</h3>
          <p className="text-muted">You haven&apos;t placed any orders yet</p>
          <Link to="/home" className="btn btn-primary mt-3">
            Start Shopping
          </Link>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>My Orders</h2>
        <Button variant="outline-primary" onClick={fetchOrders}>
          <i className="bi bi-arrow-clockwise me-2"></i>Refresh
        </Button>
      </div>

      {/* Desktop Table View */}
      <div className="d-none d-md-block">
        <Card>
          <Card.Body>
            <Table responsive hover>
              <thead>
                <tr>
                  <th>Order ID</th>
                  <th>Date</th>
                  <th>Total Amount</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {orders.map((order) => (
                  <tr key={order.id}>
                    <td>
                      <code>#{order.id.slice(-8)}</code>
                    </td>
                    <td>{formatDate(order.orderDate)}</td>
                    <td>
                      <strong>${order.totalAmount.toFixed(2)}</strong>
                    </td>
                    <td>{getStatusBadge(order.orderStatus)}</td>
                    <td>
                      <Link
                        to={`/manage/orders/${order.id}`}
                        className="btn btn-outline-primary btn-sm"
                      >
                        <i className="bi bi-eye me-1"></i>View Details
                      </Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </Card.Body>
        </Card>
      </div>

      {/* Mobile Card View */}
      <div className="d-md-none">
        <Row className="g-3">
          {orders.map((order) => (
            <Col xs={12} key={order.id}>
              <Card>
                <Card.Body>
                  <div className="d-flex justify-content-between align-items-start mb-2">
                    <div>
                      <h6 className="mb-1">Order #{order.id.slice(-8)}</h6>
                      <small className="text-muted">
                        {formatDate(order.orderDate)}
                      </small>
                    </div>
                    {getStatusBadge(order.orderStatus)}
                  </div>
                  
                  <div className="d-flex justify-content-between align-items-center">
                    <div>
                      <strong className="text-success">
                        ${order.totalAmount.toFixed(2)}
                      </strong>
                    </div>
                    <Link
                      to={`/orders/${order.id}`}
                      className="btn btn-outline-primary btn-sm"
                    >
                      View Details
                    </Link>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      </div>
    </Container>
  );
}
