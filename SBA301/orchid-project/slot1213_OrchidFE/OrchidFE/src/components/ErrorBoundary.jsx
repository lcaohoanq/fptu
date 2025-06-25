import React from "react";
import { Alert, Button, Container } from "react-bootstrap";
import PropTypes from "prop-types";

class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, error: null, errorInfo: null };
  }

  static getDerivedStateFromError() {
    // Update state so the next render will show the fallback UI
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {
    // Log the error to console or error reporting service
    console.error("ErrorBoundary caught an error:", error, errorInfo);
    
    this.setState({
      error: error,
      errorInfo: errorInfo
    });
  }

  handleReload = () => {
    // Clear error state and reload the page
    this.setState({ hasError: false, error: null, errorInfo: null });
    window.location.reload();
  };

  render() {
    if (this.state.hasError) {
      return (
        <Container className="py-5">
          <Alert variant="danger">
            <Alert.Heading>
              <i className="bi bi-exclamation-triangle me-2"></i>
              Oops! Something went wrong
            </Alert.Heading>
            <p>
              We&apos;re sorry, but something unexpected happened. The application has encountered an error.
            </p>
            
            {import.meta.env.DEV && this.state.error && (
              <details className="mt-3">
                <summary className="mb-2">Error Details (Development Mode)</summary>
                <pre className="text-small bg-light p-2 rounded">
                  {this.state.error && this.state.error.toString()}
                  <br />
                  {this.state.errorInfo.componentStack}
                </pre>
              </details>
            )}
            
            <hr />
            <div className="d-flex gap-2">
              <Button variant="primary" onClick={this.handleReload}>
                <i className="bi bi-arrow-clockwise me-2"></i>
                Reload Page
              </Button>
              <Button 
                variant="outline-secondary" 
                onClick={() => window.location.href = '/'}
              >
                <i className="bi bi-house me-2"></i>
                Go Home
              </Button>
            </div>
          </Alert>
        </Container>
      );
    }

    // If no error, render children normally
    return this.props.children;
  }
}

ErrorBoundary.propTypes = {
  children: PropTypes.node.isRequired,
};

export default ErrorBoundary;
