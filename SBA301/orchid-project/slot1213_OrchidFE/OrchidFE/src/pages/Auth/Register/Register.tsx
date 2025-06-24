import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { useForm } from "react-hook-form";
import { Alert, Form, Button, Spinner } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { useAuth } from "../../../contexts/auth.context";
import { registerApi } from "../../../apis/auth.api";

type RegisterFormData = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  confirmPassword: string;
  acceptTerms: boolean;
};

const Register = () => {
  const { isAuthenticated, isLoading: authLoading } = useAuth();
  const navigate = useNavigate();

  // Redirect if already authenticated
  useEffect(() => {
    if (!authLoading && isAuthenticated) {
      navigate("/");
    }
  }, [isAuthenticated, authLoading, navigate]);

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<RegisterFormData>({
    defaultValues: {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      confirmPassword: "",
      acceptTerms: false,
    },
  });

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  // Used to compare password and confirm password
  const password = watch("password");

  const onSubmit = async (data: RegisterFormData) => {
    setIsLoading(true);
    setError(null);
    setSuccess(null);

    try {
      // Combine first and last name for the API
      const fullName = `${data.firstName} ${data.lastName}`.trim();

      // Call the register API
      const response = await registerApi(fullName, data.email, data.password);

      console.log("Registration successful", response);

      // Show success message
      setSuccess("Registration successful! Redirecting to login page...");

      // Redirect to login page after 2 seconds
      setTimeout(() => {
        navigate("/login");
      }, 2000);
    } catch (error) {
      console.error("Registration failed", error);
      setError(
        "Registration failed. Please try again or use a different email.",
      );
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="container-fluid bg-light min-vh-100 d-flex align-items-center justify-content-center py-5">
      <div className="col-12 col-md-6 col-lg-4">
        <div className="card shadow-sm">
          <div className="card-body p-4 p-md-5">
            <h2 className="text-center mb-4">Create an Account</h2>

            {error && (
              <Alert variant="danger" className="mb-3">
                {error}
              </Alert>
            )}

            {success && (
              <Alert variant="success" className="mb-3">
                {success}
              </Alert>
            )}

            <Form onSubmit={handleSubmit(onSubmit)}>
              <div className="row mb-3">
                <div className="col-md-6 mb-3 mb-md-0">
                  <Form.Group controlId="firstName">
                    <Form.Label>First Name</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="First name"
                      isInvalid={!!errors.firstName}
                      disabled={isLoading}
                      {...register("firstName", {
                        required: "First name is required",
                      })}
                    />
                    <Form.Control.Feedback type="invalid">
                      {errors.firstName?.message}
                    </Form.Control.Feedback>
                  </Form.Group>
                </div>

                <div className="col-md-6">
                  <Form.Group controlId="lastName">
                    <Form.Label>Last Name</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Last name"
                      isInvalid={!!errors.lastName}
                      disabled={isLoading}
                      {...register("lastName", {
                        required: "Last name is required",
                      })}
                    />
                    <Form.Control.Feedback type="invalid">
                      {errors.lastName?.message}
                    </Form.Control.Feedback>
                  </Form.Group>
                </div>
              </div>

              <Form.Group className="mb-3" controlId="email">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
                  placeholder="Enter your email"
                  isInvalid={!!errors.email}
                  disabled={isLoading}
                  {...register("email", {
                    required: "Email is required",
                    pattern: {
                      value: /\S+@\S+\.\S+/,
                      message: "Email is invalid",
                    },
                  })}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.email?.message}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className="mb-3" controlId="password">
                <Form.Label>Password</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Enter your password"
                  isInvalid={!!errors.password}
                  disabled={isLoading}
                  {...register("password", {
                    required: "Password is required",
                    minLength: {
                      value: 6,
                      message: "Password must be at least 6 characters",
                    },
                  })}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.password?.message}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className="mb-3" controlId="confirmPassword">
                <Form.Label>Confirm Password</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Confirm your password"
                  isInvalid={!!errors.confirmPassword}
                  disabled={isLoading}
                  {...register("confirmPassword", {
                    required: "Please confirm your password",
                    validate: (value) =>
                      value === password || "Passwords do not match",
                  })}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.confirmPassword?.message}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className="mb-4" controlId="acceptTerms">
                <Form.Check
                  type="checkbox"
                  label="I agree to the terms and conditions"
                  isInvalid={!!errors.acceptTerms}
                  disabled={isLoading}
                  {...register("acceptTerms", {
                    required: "You must accept the terms and conditions",
                  })}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.acceptTerms?.message}
                </Form.Control.Feedback>
              </Form.Group>

              <div className="d-grid">
                <Button
                  type="submit"
                  variant="primary"
                  className="py-2"
                  disabled={isLoading}
                >
                  {isLoading ? (
                    <>
                      <Spinner
                        as="span"
                        animation="border"
                        size="sm"
                        role="status"
                        aria-hidden="true"
                        className="me-2"
                      />
                      Registering...
                    </>
                  ) : (
                    "Register"
                  )}
                </Button>
              </div>
            </Form>

            <div className="text-center mt-4">
              Already have an account?{" "}
              <a href="/login" className="text-decoration-none">
                Sign in
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;
