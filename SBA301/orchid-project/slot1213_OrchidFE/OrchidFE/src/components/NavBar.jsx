import React from "react";
import { useNavigate } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import Badge from "react-bootstrap/Badge";
import Button from "react-bootstrap/Button";
import { useAuth } from "../contexts/auth.context";
import { useCart } from "../contexts/cart.context";

function NavBar() {
  const { isAuthenticated, user, logout } = useAuth();
  const { cartCount } = useCart();
  const navigate = useNavigate();
  
  const handleLogout = () => {
    logout();
    navigate('/login');
  };
  
  return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand href="/">Orchids</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            {/* <Nav.Link href="/home">Home</Nav.Link> */}
            {/* <Nav.Link href="/products">Products</Nav.Link> */}
            
            {/* Show admin/management options only if authenticated */}
            {isAuthenticated && user.role === "Admin" && (
              <NavDropdown title="Management" id="management-dropdown">
                <NavDropdown.Item href="/manage/employees">
                  User Management
                </NavDropdown.Item>
                <NavDropdown.Item href="/manage/orchids">
                  Orchid Management
                </NavDropdown.Item>
                <NavDropdown.Item href="/manage/orders">
                  Order Management
                </NavDropdown.Item>
              </NavDropdown>
            )}
          </Nav>
          
          {/* Right side of navbar */}
          <Nav>
            {/* Always show cart */}
            <Nav.Link href="/cart" className="position-relative me-2">
              <Button variant="outline-primary">
                <i className="bi bi-cart3 me-1"></i>
                Cart
                {cartCount > 0 && (
                  <Badge 
                    bg="danger" 
                    pill 
                    className="position-absolute top-0 start-100 translate-middle"
                  >
                    {cartCount}
                  </Badge>
                )}
              </Button>
            </Nav.Link>

            {/* Conditional rendering based on authentication state */}
            {isAuthenticated ? (
              <div className="d-flex flex-column">
              <NavDropdown 
                title={user?.email || "Account"} 
                id="user-dropdown"
                align="end"
              >
                <NavDropdown.Item href="/my-profile">My Profile</NavDropdown.Item>
                <NavDropdown.Item href="/my-orders">My Orders</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogout}>
                  Logout
                </NavDropdown.Item>
              </NavDropdown>
              <Badge bg="secondary">{user?.role}</Badge>
              </div>
            ) : (
              <div className="d-flex flex-column justify-content-center">
                <Nav.Link href="/login">Login</Nav.Link>
              </div>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavBar;