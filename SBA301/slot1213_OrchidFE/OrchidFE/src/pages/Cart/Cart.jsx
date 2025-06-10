import { useEffect, useState } from "react";
import { Button, Card, Col, Row, Spinner, Table, Image, Form } from "react-bootstrap";
import Container from "react-bootstrap/esm/Container";
import { Link } from "react-router";
import { orchidApi } from "../../apis/api.config";

export default function Cart() {
  const [loading, setLoading] = useState(false);
  const [cartItems, setCartItems] = useState([]);
  const [cartTotal, setCartTotal] = useState(0);

  useEffect(() => {
    fetchCartData();
  }, []);

  // Calculate total whenever cart items change
  useEffect(() => {
    const total = cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    setCartTotal(total);
  }, [cartItems]);

  const fetchCartData = async () => {
    setLoading(true);
    try {
      // In a real app, you would fetch cart data from an API
      // For now, we'll use the orchid API and add some mock cart properties
      const response = await orchidApi.get("/");
      
      // Mock data for cart demonstration
      const mockCartItems = response.data.slice(0, 3).map(orchid => ({
        ...orchid,
        quantity: Math.floor(Math.random() * 3) + 1, // Random quantity between 1-3
        price: parseFloat((Math.random() * 50 + 20).toFixed(2)), // Random price between $20-$70
      }));
      
      setCartItems(mockCartItems);
    } catch (error) {
      console.error("Error fetching cart data:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleQuantityChange = (id, newQuantity) => {
    if (newQuantity < 1) return;
    
    setCartItems(prevItems => 
      prevItems.map(item => 
        item.id === id ? { ...item, quantity: newQuantity } : item
      )
    );
  };

  const handleRemoveItem = (id) => {
    setCartItems(prevItems => prevItems.filter(item => item.id !== id));
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

  if (cartItems.length === 0) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <i className="bi bi-cart3 fs-1 text-muted mb-3 d-block"></i>
          <h3>Your cart is empty</h3>
          <p className="text-muted">Add some beautiful orchids to your cart</p>
          <Link to="/home" className="btn btn-primary mt-3">
            Continue Shopping
          </Link>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <h2 className="mb-4">Your Shopping Cart</h2>
      
      <Row>
        <Col lg={8}>
          <div className="card mb-4">
            <div className="card-body">
              <Table responsive className="align-middle">
                <thead>
                  <tr>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  {cartItems.map((item) => (
                    <tr key={item.id}>
                      <td>
                        <div className="d-flex align-items-center">
                          <Image 
                            src={item.image} 
                            alt={item.orchidName} 
                            width={80} 
                            height={80} 
                            className="object-fit-cover me-3"
                          />
                          <div>
                            <h6 className="mb-0">{item.orchidName}</h6>
                            {item.isNatural ? 
                              <span className="badge bg-success me-1">Natural</span> : 
                              <span className="badge bg-warning me-1">Industry</span>
                            }
                          </div>
                        </div>
                      </td>
                      <td>${item.price.toFixed(2)}</td>
                      <td style={{ width: "120px" }}>
                        <div className="input-group">
                          <Button 
                            variant="outline-secondary" 
                            size="sm"
                            onClick={() => handleQuantityChange(item.id, item.quantity - 1)}
                          >
                            -
                          </Button>
                          <Form.Control
                            type="number"
                            min="1"
                            value={item.quantity}
                            onChange={(e) => handleQuantityChange(item.id, parseInt(e.target.value) || 1)}
                            className="text-center"
                          />
                          <Button 
                            variant="outline-secondary" 
                            size="sm"
                            onClick={() => handleQuantityChange(item.id, item.quantity + 1)}
                          >
                            +
                          </Button>
                        </div>
                      </td>
                      <td>${(item.price * item.quantity).toFixed(2)}</td>
                      <td>
                        <Button 
                          variant="outline-danger" 
                          size="sm"
                          onClick={() => handleRemoveItem(item.id)}
                        >
                          <i className="bi bi-trash"></i>
                        </Button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </div>
          </div>
          
          <div className="d-flex justify-content-between">
            <Link to="/home" className="btn btn-outline-secondary">
              <i className="bi bi-arrow-left me-2"></i>Continue Shopping
            </Link>
            <Button variant="outline-primary" onClick={() => fetchCartData()}>
              <i className="bi bi-arrow-clockwise me-2"></i>Update Cart
            </Button>
          </div>
        </Col>
        
        <Col lg={4}>
          <div className="card">
            <div className="card-body">
              <h5 className="card-title mb-4">Order Summary</h5>
              
              <div className="d-flex justify-content-between mb-2">
                <span>Subtotal:</span>
                <span>${cartTotal.toFixed(2)}</span>
              </div>
              
              <div className="d-flex justify-content-between mb-2">
                <span>Shipping:</span>
                <span>Free</span>
              </div>
              
              <hr />
              
              <div className="d-flex justify-content-between mb-4">
                <strong>Total:</strong>
                <strong>${cartTotal.toFixed(2)}</strong>
              </div>
              
              <Button variant="success" size="lg" className="w-100">
                <i className="bi bi-credit-card me-2"></i>Proceed to Checkout
              </Button>
              
              <div className="mt-3 text-center">
                <small className="text-muted">
                  <i className="bi bi-shield-lock me-1"></i>
                  Secure Checkout
                </small>
              </div>
            </div>
          </div>
          
          <div className="card mt-3">
            <div className="card-body">
              <h6 className="mb-3">Have a coupon?</h6>
              <div className="input-group">
                <Form.Control 
                  type="text" 
                  placeholder="Coupon code"
                />
                <Button variant="outline-secondary">Apply</Button>
              </div>
            </div>
          </div>
        </Col>
      </Row>
    </Container>
  );
}
