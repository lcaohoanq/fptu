import { useCallback, useEffect, useState } from "react";
import { Alert, Badge, Button, Card, Col, Row, Spinner } from "react-bootstrap";
import Container from "react-bootstrap/esm/Container";
import { Link, useParams } from "react-router-dom";
import { orchidApi } from "../../../apis/api.config";
  
export default function OrderDetail() {
  const [loading, setLoading] = useState(false);
  const [order, setOrder] = useState(null);
  const [error, setError] = useState(null);
  const { orderId } = useParams();

  const fetchOrderDetail = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await orchidApi.get(`/orders/${orderId}`);
      setOrder(response.data.data);
    } catch (error) {
      console.error("Error fetching order details:", error);
      setError("Failed to load order details. Please try again.");
    } finally {
      setLoading(false);
    }
  }, [orderId]);

  useEffect(() => {
    if (orderId) {
      fetchOrderDetail();
    }
  }, [orderId, fetchOrderDetail]);

  const formatDate = (timestamp) => {
    return new Date(timestamp).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
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
      <Badge bg={statusColors[status] || "secondary"} className="fs-6">
        <i className={`bi ${statusIcons[status]} me-1`}></i>
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

  if (error) {
    return (
      <Container className="py-5">
        <Alert variant="danger">
          <Alert.Heading>Error</Alert.Heading>
          <p>{error}</p>
          <Button variant="outline-danger" onClick={fetchOrderDetail}>
            <i className="bi bi-arrow-clockwise me-1"></i>
            Try Again
          </Button>
        </Alert>
      </Container>
    );
  }

  if (!order) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <i className="bi bi-receipt fs-1 text-muted mb-3 d-block"></i>
          <h3>Order not found</h3>
          <p className="text-muted">The order you&apos;re looking for doesn&apos;t exist.</p>
          <Link to="/orders" className="btn btn-primary mt-3">
            Back to Orders
          </Link>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      {/* Header */}
      <div className="d-flex justify-content-between align-items-center mb-4">
        <div>
          <Link to="/orders" className="btn btn-outline-secondary mb-2">
            <i className="bi bi-arrow-left me-1"></i>
            Back to Orders
          </Link>
          <h2 className="mb-0">Order Details</h2>
        </div>
        <Button variant="outline-primary" onClick={fetchOrderDetail}>
          <i className="bi bi-arrow-clockwise me-1"></i>
          Refresh
        </Button>
      </div>

      <Row>
        {/* Order Information */}
        <Col lg={8}>
          <Card className="mb-4">
            <Card.Header className="bg-primary text-white">
              <h5 className="mb-0">
                <i className="bi bi-receipt me-2"></i>
                Order Information
              </h5>
            </Card.Header>
            <Card.Body>
              <Row>
                <Col md={6}>
                  <div className="mb-3">
                    <strong>Order ID:</strong>
                    <div className="mt-1">
                      <code>#{order.id}</code>
                    </div>
                  </div>
                  
                  <div className="mb-3">
                    <strong>Order Date:</strong>
                    <div className="mt-1">
                      {formatDate(order.orderDate)}
                    </div>
                  </div>
                </Col>
                
                <Col md={6}>
                  <div className="mb-3">
                    <strong>Status:</strong>
                    <div className="mt-1">
                      {getStatusBadge(order.orderStatus)}
                    </div>
                  </div>
                  
                  <div className="mb-3">
                    <strong>Account ID:</strong>
                    <div className="mt-1">
                      <code>{order.accountId}</code>
                    </div>
                  </div>
                </Col>
              </Row>
            </Card.Body>
          </Card>

          {/* Order Items Placeholder */}
          <Card className="mb-4">
            <Card.Header>
              <h5 className="mb-0">
                <i className="bi bi-list-ul me-2"></i>
                Order Items
              </h5>
            </Card.Header>
            <Card.Body>
              <Alert variant="info">
                <i className="bi bi-info-circle me-2"></i>
                Order items details would be displayed here when the API endpoint for order items is available.
              </Alert>
            </Card.Body>
          </Card>
        </Col>

        {/* Order Summary */}
        <Col lg={4}>
          <Card>
            <Card.Header>
              <h5 className="mb-0">
                <i className="bi bi-calculator me-2"></i>
                Order Summary
              </h5>
            </Card.Header>
            <Card.Body>
              <div className="d-flex justify-content-between mb-2">
                <span>Subtotal:</span>
                <span>${order.totalAmount.toFixed(2)}</span>
              </div>
              
              <div className="d-flex justify-content-between mb-2">
                <span>Shipping:</span>
                <span className="text-success">Free</span>
              </div>
              
              <div className="d-flex justify-content-between mb-2">
                <span>Tax:</span>
                <span>$0.00</span>
              </div>
              
              <hr />
              
              <div className="d-flex justify-content-between mb-3">
                <strong>Total:</strong>
                <strong className="text-success fs-5">
                  ${order.totalAmount.toFixed(2)}
                </strong>
              </div>

              {/* Action Buttons */}
              <div className="d-grid gap-2">
                {order.orderStatus === 'PENDING' && (
                  <Button variant="outline-danger" size="sm">
                    <i className="bi bi-x-circle me-1"></i>
                    Cancel Order
                  </Button>
                )}
                
                {order.orderStatus === 'COMPLETED' && (
                  <Button variant="outline-primary" size="sm">
                    <i className="bi bi-arrow-repeat me-1"></i>
                    Reorder
                  </Button>
                )}
                
                <Button variant="outline-secondary" size="sm">
                  <i className="bi bi-download me-1"></i>
                  Download Invoice
                </Button>
              </div>
            </Card.Body>
          </Card>

          {/* Order Timeline */}
          <Card className="mt-3">
            <Card.Header>
              <h5 className="mb-0">
                <i className="bi bi-clock-history me-2"></i>
                Order Timeline
              </h5>
            </Card.Header>
            <Card.Body>
              <div className="timeline">
                <div className="timeline-item">
                  <div className="timeline-marker bg-success"></div>
                  <div className="timeline-content">
                    <h6 className="mb-1">Order Placed</h6>
                    <small className="text-muted">
                      {formatDate(order.orderDate)}
                    </small>
                  </div>
                </div>
                
                {order.orderStatus !== 'PENDING' && (
                  <div className="timeline-item">
                    <div className="timeline-marker bg-info"></div>
                    <div className="timeline-content">
                      <h6 className="mb-1">Processing Started</h6>
                      <small className="text-muted">
                        Status updated to {order.orderStatus}
                      </small>
                    </div>
                  </div>
                )}
                
                {order.orderStatus === 'COMPLETED' && (
                  <div className="timeline-item">
                    <div className="timeline-marker bg-success"></div>
                    <div className="timeline-content">
                      <h6 className="mb-1">Order Completed</h6>
                      <small className="text-muted">
                        Your order has been completed
                      </small>
                    </div>
                  </div>
                )}
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}
