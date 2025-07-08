import React, { useState } from "react";
import { Button, Form, Modal, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { authAPI } from "../apis/api.config";
import type { LoginRequest } from "../types";

interface NavigationBarProps {
  user: any;
  onLogin: (user: any, token: string) => void;
  onLogout: () => void;
}

const NavigationBar: React.FC<NavigationBarProps> = ({
  user,
  onLogin,
  onLogout,
}) => {
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [loginData, setLoginData] = useState<LoginRequest>({
    email: "",
    password: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const response = await authAPI.login(loginData);
      localStorage.setItem("token", response.accessToken);
      localStorage.setItem("user", JSON.stringify(response.account));
      onLogin(response.account, response.accessToken);
      setShowLoginModal(false);
      setLoginData({ email: "", password: "" });

      if (response.account.role === "ADMINISTRATOR") {
        navigate("/blind-boxes");
      }
    } catch (err: any) {
      setError(err.response?.data?.reason || "Login failed");
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    onLogout();
  };

  return (
    <>
      <Navbar bg="dark" variant="dark" expand="lg">
        <Navbar.Brand href="/">
          Luu Cao Hoang SBA301 - PE Summer 25
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link href="/">Home</Nav.Link>
            {user && user.role === "ADMINISTRATOR" && (
              <NavDropdown
                title="Blind Boxes Management"
                id="basic-nav-dropdown"
              >
                <NavDropdown.Item href="/blind-boxes">
                  List all items
                </NavDropdown.Item>
                <NavDropdown.Item href="/blind-boxes">
                  Create a new blind box
                </NavDropdown.Item>
              </NavDropdown>
            )}
          </Nav>
          <Nav>
            {user ? (
              <NavDropdown
                title={`Welcome, ${user.username}`}
                id="user-nav-dropdown"
              >
                <NavDropdown.Item onClick={handleLogout}>
                  Logout
                </NavDropdown.Item>
              </NavDropdown>
            ) : (
              <Nav.Link onClick={() => setShowLoginModal(true)}>Login</Nav.Link>
            )}
          </Nav>
        </Navbar.Collapse>
      </Navbar>

      <Modal show={showLoginModal} onHide={() => setShowLoginModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Login to Blind Boxes Management System</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleLogin}>
            {error && <div className="alert alert-danger">{error}</div>}
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                value={loginData.email}
                onChange={(e) =>
                  setLoginData({ ...loginData, email: e.target.value })
                }
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Password</Form.Label>
              <Form.Control
                type="password"
                value={loginData.password}
                onChange={(e) =>
                  setLoginData({ ...loginData, password: e.target.value })
                }
                required
              />
            </Form.Group>
            <Button type="submit" variant="primary" disabled={loading}>
              {loading ? "Logging in..." : "Login"}
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </>
  );
};

export default NavigationBar;
