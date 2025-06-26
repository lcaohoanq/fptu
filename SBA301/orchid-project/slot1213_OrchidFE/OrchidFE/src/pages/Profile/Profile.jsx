import { useCallback, useEffect, useState } from "react";
import { Alert, Badge, Button, Card, Col, Container, Form, Modal, Row, Spinner } from "react-bootstrap";
import { orchidApi } from "../../apis/api.config";
import { useAuth } from "../../contexts/auth.context";

export default function Profile() {
  const { user } = useAuth();
  const [loading, setLoading] = useState(false);
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState(null);
  const [showEditModal, setShowEditModal] = useState(false);
  const [editForm, setEditForm] = useState({ name: "", email: "" });
  const [saving, setSaving] = useState(false);

  const fetchProfile = useCallback(async () => {
    if (!user?.id) return;
    
    setLoading(true);
    setError(null);
    try {
      // Use the user ID from auth context to fetch profile
      const response = await orchidApi.get(`/accounts/${user.id}`);
      setProfile(response.data.data);
      setEditForm({
        name: response.data.data.name,
        email: response.data.data.email
      });
    } catch (error) {
      console.error("Error fetching profile:", error);
      setError("Failed to load profile. Please try again.");
    } finally {
      setLoading(false);
    }
  }, [user?.id]);

  useEffect(() => {
    fetchProfile();
  }, [fetchProfile]);

  const handleEditSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      const response = await orchidApi.put(`/accounts/profile`, editForm);
      setProfile(response.data.data);
      setShowEditModal(false);
      // You might want to show a success toast here
    } catch (error) {
      console.error("Error updating profile:", error);
      // You might want to show an error toast here
    } finally {
      setSaving(false);
    }
  };

  const getRoleBadge = (role) => {
    const roleColors = {
      "Admin": "danger",
      "User": "primary",
      "Manager": "warning"
    };

    return (
      <Badge bg={roleColors[role?.name] || "secondary"} className="fs-6">
        {role?.name || "Unknown"}
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
          <Button variant="outline-danger" onClick={fetchProfile}>
            <i className="bi bi-arrow-clockwise me-1"></i>
            Try Again
          </Button>
        </Alert>
      </Container>
    );
  }

  if (!profile) {
    return (
      <Container className="py-5">
        <div className="text-center">
          <i className="bi bi-person-circle fs-1 text-muted mb-3 d-block"></i>
          <h3>Profile not found</h3>
          <p className="text-muted">Unable to load your profile information.</p>
        </div>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <Row>
        <Col lg={8} className="mx-auto">
          <div className="d-flex justify-content-between align-items-center mb-4">
            <h2 className="mb-0">My Profile</h2>
            <Button
              variant="outline-primary"
              onClick={() => setShowEditModal(true)}
            >
              <i className="bi bi-pencil me-1"></i>
              Edit Profile
            </Button>
          </div>

          <Card>
            <Card.Body className="p-4">
              <Row>
                <Col md={4} className="text-center mb-4 mb-md-0">
                  <div className="profile-avatar mb-3">
                    <i className="bi bi-person-circle display-1 text-muted"></i>
                  </div>
                  <div className="mb-2">
                    {getRoleBadge(profile.role)}
                  </div>
                  <div className="text-muted small">
                    Account Status: {' '}
                    <Badge bg={profile.enabled ? "success" : "danger"}>
                      {profile.enabled ? "Active" : "Inactive"}
                    </Badge>
                  </div>
                </Col>
                
                <Col md={8}>
                  <div className="profile-info">
                    <div className="mb-3">
                      <label className="text-muted small">Full Name</label>
                      <h5 className="mb-0">{profile.name}</h5>
                    </div>
                    
                    <div className="mb-3">
                      <label className="text-muted small">Email Address</label>
                      <h6 className="mb-0">{profile.email}</h6>
                    </div>
                    
                    <div className="mb-3">
                      <label className="text-muted small">Username</label>
                      <p className="mb-0">{profile.username}</p>
                    </div>
                    
                    <div className="mb-3">
                      <label className="text-muted small">Role</label>
                      <p className="mb-0">{profile.role?.name}</p>
                    </div>
                    
                    <div className="row">
                      <div className="col-sm-6 mb-2">
                        <label className="text-muted small">Account Status</label>
                        <p className="mb-0">
                          <i className={`bi ${profile.enabled ? 'bi-check-circle text-success' : 'bi-x-circle text-danger'} me-1`}></i>
                          {profile.enabled ? 'Active' : 'Inactive'}
                        </p>
                      </div>
                      <div className="col-sm-6 mb-2">
                        <label className="text-muted small">Account Locked</label>
                        <p className="mb-0">
                          <i className={`bi ${profile.accountNonLocked ? 'bi-unlock text-success' : 'bi-lock text-danger'} me-1`}></i>
                          {profile.accountNonLocked ? 'Unlocked' : 'Locked'}
                        </p>
                      </div>
                    </div>
                  </div>
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Edit Profile Modal */}
      <Modal show={showEditModal} onHide={() => setShowEditModal(false)} backdrop="static">
        <Modal.Header closeButton>
          <Modal.Title>Edit Profile</Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleEditSubmit}>
          <Modal.Body>
            <Form.Group className="mb-3">
              <Form.Label>Full Name</Form.Label>
              <Form.Control
                type="text"
                value={editForm.name}
                onChange={(e) => setEditForm({ ...editForm, name: e.target.value })}
                required
                disabled={saving}
              />
            </Form.Group>
            
            <Form.Group className="mb-3">
              <Form.Label>Email Address</Form.Label>
              <Form.Control
                type="email"
                value={editForm.email}
                onChange={(e) => setEditForm({ ...editForm, email: e.target.value })}
                required
                disabled={saving}
              />
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button 
              variant="secondary" 
              onClick={() => setShowEditModal(false)}
              disabled={saving}
            >
              Cancel
            </Button>
            <Button 
              variant="primary" 
              type="submit"
              disabled={saving}
            >
              {saving ? (
                <>
                  <Spinner animation="border" size="sm" className="me-1" />
                  Saving...
                </>
              ) : (
                <>
                  <i className="bi bi-check me-1"></i>
                  Save Changes
                </>
              )}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </Container>
  );
}
