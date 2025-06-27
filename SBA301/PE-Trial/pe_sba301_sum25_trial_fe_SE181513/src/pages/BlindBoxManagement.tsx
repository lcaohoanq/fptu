import React, { useState, useEffect } from "react";
import { Container, Table, Button, Modal, Form, Alert } from "react-bootstrap";
import type { BlindBox, Category, CreateBlindBoxRequest } from "../types";
import { blindBoxAPI, categoryAPI } from "../apis/api.config";

interface BlindBoxManagementProps {
  user: any;
}

const BlindBoxManagement: React.FC<BlindBoxManagementProps> = ({ user }) => {
  const [blindBoxes, setBlindBoxes] = useState<BlindBox[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [selectedBlindBox, setSelectedBlindBox] = useState<BlindBox | null>(
    null
  );
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const [formData, setFormData] = useState<CreateBlindBoxRequest>({
    name: "",
    rarity: "",
    price: 0,
    stock: 1,
    categoryId: 0,
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [blindBoxData, categoryData] = await Promise.all([
        blindBoxAPI.getAll(),
        categoryAPI.getAll(),
      ]);
      setBlindBoxes(blindBoxData);
      setCategories(categoryData);
    } catch (err: any) {
      setError("Failed to fetch data");
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const newBlindBox = await blindBoxAPI.create(formData);
      setBlindBoxes([newBlindBox, ...blindBoxes]); // Add to top of list
      setShowCreateModal(false);
      setFormData({ name: "", rarity: "", price: 0, stock: 1, categoryId: 0 });
      setSuccess("Blind box created successfully!");
      setTimeout(() => setSuccess(""), 3000);
    } catch (err: any) {
      setError(err.response?.data?.reason || "Failed to create blind box");
    }
  };

  const handleDelete = async () => {
    if (!selectedBlindBox) return;

    try {
      await blindBoxAPI.delete(selectedBlindBox.id);
      setBlindBoxes(blindBoxes.filter((box) => box.id !== selectedBlindBox.id));
      setShowDeleteModal(false);
      setSelectedBlindBox(null);
      setSuccess("Blind box deleted successfully!");
      setTimeout(() => setSuccess(""), 3000);
    } catch (err: any) {
      setError(err.response?.data?.reason || "Failed to delete blind box");
    }
  };

  const isAdmin = user?.role === "ADMINISTRATOR";

  if (loading) return <div className="text-center">Loading...</div>;

  return (
    <Container className="mt-4">
      <h2>Blind Boxes Management</h2>

      {error && <Alert variant="danger">{error}</Alert>}
      {success && <Alert variant="success">{success}</Alert>}

      {isAdmin && (
        <Button
          variant="primary"
          className="mb-3"
          onClick={() => setShowCreateModal(true)}
        >
          Create New Blind Box
        </Button>
      )}

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Rarity</th>
            <th>Price</th>
            <th>Release Date</th>
            <th>Stock</th>
            <th>Category</th>
            {isAdmin && <th>Actions</th>}
          </tr>
        </thead>
        <tbody>
          {blindBoxes.map((blindBox) => (
            <tr key={blindBox.id}>
              <td>{blindBox.id}</td>
              <td>{blindBox.name}</td>
              <td>{blindBox.rarity}</td>
              <td>${blindBox.price}</td>
              <td>{new Date(blindBox.releaseDate).toLocaleDateString()}</td>
              <td>{blindBox.stock}</td>
              <td>{blindBox.categoryName}</td>
              {isAdmin && (
                <td>
                  <Button
                    variant="danger"
                    size="sm"
                    onClick={() => {
                      setSelectedBlindBox(blindBox);
                      setShowDeleteModal(true);
                    }}
                  >
                    Delete
                  </Button>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </Table>

      {/* Create Modal */}
      <Modal show={showCreateModal} onHide={() => setShowCreateModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Create New Blind Box</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleCreate}>
            <Form.Group className="mb-3">
              <Form.Label>Name *</Form.Label>
              <Form.Control
                type="text"
                value={formData.name}
                onChange={(e) =>
                  setFormData({ ...formData, name: e.target.value })
                }
                required
                minLength={10}
              />
              <Form.Text className="text-muted">
                Name must be greater than 10 characters
              </Form.Text>
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Rarity *</Form.Label>
              <Form.Control
                type="text"
                value={formData.rarity}
                onChange={(e) =>
                  setFormData({ ...formData, rarity: e.target.value })
                }
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Price *</Form.Label>
              <Form.Control
                type="number"
                step="0.01"
                value={formData.price}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    price: parseFloat(e.target.value),
                  })
                }
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Stock *</Form.Label>
              <Form.Control
                type="number"
                value={formData.stock}
                onChange={(e) =>
                  setFormData({ ...formData, stock: parseInt(e.target.value) })
                }
                min={1}
                max={100}
                required
              />
              <Form.Text className="text-muted">
                Stock must be between 1 and 100
              </Form.Text>
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Category *</Form.Label>
              <Form.Select
                value={formData.categoryId}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    categoryId: parseInt(e.target.value),
                  })
                }
                required
              >
                <option value={0}>Select a category</option>
                {categories.map((category) => (
                  <option key={category.id} value={category.id}>
                    {category.name}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>

            <Button type="submit" variant="primary">
              Create
            </Button>
          </Form>
        </Modal.Body>
      </Modal>

      {/* Delete Confirmation Modal */}
      <Modal show={showDeleteModal} onHide={() => setShowDeleteModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Delete</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Are you sure you want to delete "{selectedBlindBox?.name}"?
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowDeleteModal(false)}>
            Cancel
          </Button>
          <Button variant="danger" onClick={handleDelete}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default BlindBoxManagement;
