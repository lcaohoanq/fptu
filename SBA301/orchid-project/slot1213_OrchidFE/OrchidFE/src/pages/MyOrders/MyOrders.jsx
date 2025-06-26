import { useCallback, useEffect, useState } from "react";
import { Alert, Badge, Button, Card, Col, Container, Row, Spinner, Table } from "react-bootstrap";
import { Link } from "react-router-dom";
import { orchidApi } from "../../apis/api.config";

export default function MyOrders() {
  const [loading, setLoading] = useState(false);
  const [orders, setOrders] = useState([]);
  const [error, setError] = useState(null);

  const fetchMyOrders = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await orchidApi.get("/orders/me/orders");
      setOrders(response.data.data || []);
    } catch (error) {
      console.error("Error fetching my orders:", error);
      setError("Failed to load your orders. Please try again.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchMyOrders();
  }, [fetchMyOrders]);

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
    
    const statusIcons = {
      PENDING: "bi-clock",
      PROCESSING: "bi-gear",
      COMPLETED: "bi-check-circle",
      CANCELLED: "bi-x-circle"
    };

    return (
      <Badge bg={statusColors[status] || "secondary"}>
        <i className={`bi ${statusIcons[status]} me-1`}></i>
        {status}
      </Badge>
    );
  };

  if (loading) {
    return (
      <Container className="py-5">
        <div className="d-flex justify-content-center align-items-center my-5">
          <Spinner animation="border" variant="primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </Spinner>
        </div>
      </Container>
    );
  }

  if (error) {
    return (
      <Container className="py-5">
        <Alert variant="danger">
          <Alert.Heading>Error</Alert.Heading>
          <p>{error}</p>
          <Button variant="outline-danger" onClick={fetchMyOrders}>
            <i className="bi bi-arrow-clockwise me-1"></i>
            Try Again
          </Button>
        </Alert>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2 className="mb-0">My Orders</h2>
        <Button variant="outline-primary" onClick={fetchMyOrders}>
          <i className="bi bi-arrow-clockwise me-1"></i>
          Refresh
        </Button>
      </div>

      {orders.length === 0 ? (
        <div className="text-center py-5">
          <i className="bi bi-receipt fs-1 text-muted mb-3 d-block"></i>
          <h4>No orders yet</h4>
          <p className="text-muted mb-4">
            You haven&apos;t placed any orders yet. Start shopping to see your orders here!
          </p>
          <Link to="/home" className="btn btn-primary">
            <i className="bi bi-shop me-1"></i>
            Start Shopping
          </Link>
        </div>
      ) : (
        <>
          {/* Desktop view */}
          <div className="d-none d-lg-block">
            <Card>
              <Card.Body className="p-0">
                <Table responsive hover className="mb-0">
                  <thead className="table-light">
                    <tr>
                      <th>Order ID</th>
                      <th>Date</th>
                      <th>Status</th>
                      <th>Total Amount</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
                  <tbody>
                    {orders.map((order) => (
                      <tr key={order.id}>
                        <td>
                          <span className="font-monospace">
                            #{order.id.slice(-8)}
                          </span>
                        </td>
                        <td>{formatDate(order.orderDate)}</td>
                        <td>{getStatusBadge(order.orderStatus)}</td>
                        <td>
                          <strong>${order.totalAmount.toFixed(2)}</strong>
                        </td>
                        <td>
                          <Link
                            to={`/orders/${order.id}`}
                            className="btn btn-outline-primary btn-sm me-2"
                          >
                            <i className="bi bi-eye me-1"></i>
                            View Details
                          </Link>
                          {order.orderStatus === "PENDING" && (
                            <Button
                              variant="outline-danger"
                              size="sm"
                              onClick={() => {
                                // Handle cancel order
                                console.log("Cancel order", order.id);
                              }}
                            >
                              <i className="bi bi-x-circle me-1"></i>
                              Cancel
                            </Button>
                          )}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </Table>
              </Card.Body>
            </Card>
          </div>

          {/* Mobile view */}
          <div className="d-lg-none">
            <Row>
              {orders.map((order) => (
                <Col key={order.id} xs={12} className="mb-3">
                  <Card>
                    <Card.Body>
                      <div className="d-flex justify-content-between align-items-start mb-2">
                        <div>
                          <h6 className="mb-1">
                            Order #{order.id.slice(-8)}
                          </h6>
                          <small className="text-muted">
                            {formatDate(order.orderDate)}
                          </small>
                        </div>
                        {getStatusBadge(order.orderStatus)}
                      </div>
                      
                      <div className="mb-3">
                        <strong className="fs-5">${order.totalAmount.toFixed(2)}</strong>
                      </div>
                      
                      <div className="d-flex gap-2">
                        <Link
                          to={`/orders/${order.id}`}
                          className="btn btn-outline-primary btn-sm flex-grow-1"
                        >
                          <i className="bi bi-eye me-1"></i>
                          View Details
                        </Link>
                        {order.orderStatus === "PENDING" && (
                          <Button
                            variant="outline-danger"
                            size="sm"
                            onClick={() => {
                              // Handle cancel order
                              console.log("Cancel order", order.id);
                            }}
                          >
                            <i className="bi bi-x-circle"></i>
                          </Button>
                        )}
                      </div>
                    </Card.Body>
                  </Card>
                </Col>
              ))}
            </Row>
          </div>

          {/* Summary */}
          <Card className="mt-4">
            <Card.Body className="bg-light">
              <Row className="text-center">
                <Col md={3}>
                  <div className="mb-2">
                    <i className="bi bi-receipt text-primary fs-4"></i>
                  </div>
                  <h5 className="mb-1">{orders.length}</h5>
                  <small className="text-muted">Total Orders</small>
                </Col>
                <Col md={3}>
                  <div className="mb-2">
                    <i className="bi bi-clock text-warning fs-4"></i>
                  </div>
                  <h5 className="mb-1">
                    {orders.filter(o => o.orderStatus === "PENDING").length}
                  </h5>
                  <small className="text-muted">Pending</small>
                </Col>
                <Col md={3}>
                  <div className="mb-2">
                    <i className="bi bi-check-circle text-success fs-4"></i>
                  </div>
                  <h5 className="mb-1">
                    {orders.filter(o => o.orderStatus === "COMPLETED").length}
                  </h5>
                  <small className="text-muted">Completed</small>
                </Col>
                <Col md={3}>
                  <div className="mb-2">
                    <i className="bi bi-currency-dollar text-info fs-4"></i>
                  </div>
                  <h5 className="mb-1">
                    ${orders.reduce((sum, order) => sum + order.totalAmount, 0).toFixed(2)}
                  </h5>
                  <small className="text-muted">Total Spent</small>
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </>
      )}
    </Container>
  );
}
