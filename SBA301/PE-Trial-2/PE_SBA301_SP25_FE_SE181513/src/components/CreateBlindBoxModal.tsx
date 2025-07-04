import React, { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import type { Country, CreateCarRequest } from "../types";

interface CreateBlindBoxModalProps {
  show: boolean;
  onHide: () => void;
  onSubmit: (formData: CreateCarRequest) => Promise<void>;
  countries: Country[];
}

const CreateBlindBoxModal: React.FC<CreateBlindBoxModalProps> = ({
  show,
  onHide,
  onSubmit,
  countries,
}) => {
  const [formData, setFormData] = useState<CreateCarRequest>({
    name: "",
    price: 0,
    stock: 1,
    countryId: 0,
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSubmit(formData);
    // Reset form after successful submission
    setFormData({ name: "", price: 0, stock: 1, countryId: 0 });
  };

  const handleClose = () => {
    setFormData({ name: "", price: 0, stock: 1, countryId: 0 });
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
            <Form.Label>Country *</Form.Label>
            <Form.Select
              value={formData.countryId}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  countryId: parseInt(e.target.value),
                })
              }
              required
            >
              <option value={0}>Select a country</option>
              {countries.map((country) => (
                <option key={country.id} value={country.id}>
                  {country.name}
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
