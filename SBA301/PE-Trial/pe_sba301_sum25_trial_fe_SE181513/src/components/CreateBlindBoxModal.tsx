import React, { useState } from "react";
import { Modal, Form, Button } from "react-bootstrap";
import type { CreateBlindBoxRequest, Category } from "../types";

interface CreateBlindBoxModalProps {
  show: boolean;
  onHide: () => void;
  onSubmit: (formData: CreateBlindBoxRequest) => Promise<void>;
  categories: Category[];
}

const CreateBlindBoxModal: React.FC<CreateBlindBoxModalProps> = ({
  show,
  onHide,
  onSubmit,
  categories,
}) => {
  const [formData, setFormData] = useState<CreateBlindBoxRequest>({
    name: "",
    rarity: "",
    price: 0,
    stock: 1,
    categoryId: 0,
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSubmit(formData);
    // Reset form after successful submission
    setFormData({ name: "", rarity: "", price: 0, stock: 1, categoryId: 0 });
  };

  const handleClose = () => {
    setFormData({ name: "", rarity: "", price: 0, stock: 1, categoryId: 0 });
    onHide();
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Create New Blind Box</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
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
  );
};

export default CreateBlindBoxModal;
