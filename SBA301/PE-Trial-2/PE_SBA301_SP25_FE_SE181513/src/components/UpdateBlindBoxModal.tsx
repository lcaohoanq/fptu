import React, { useState, useEffect } from "react";
import { Modal, Form, Button } from "react-bootstrap";
import type { Car, Country } from "../types";

interface UpdateBlindBoxModalProps {
  show: boolean;
  onHide: () => void;
  onSubmit: (id: number, formData: Partial<Car>) => Promise<void>;
  countries: Country[];
  blindBox: Car | null;
}

const UpdateBlindBoxModal: React.FC<UpdateBlindBoxModalProps> = ({
  show,
  onHide,
  onSubmit,
  countries,
  blindBox,
}) => {
  const [formData, setFormData] = useState({
    name: "",
    price: 0,
    stock: 0,
    countryId: 0,
  });

  useEffect(() => {
    if (blindBox) {
      setFormData({
        name: blindBox.name,
        price: blindBox.price,
        stock: blindBox.stock,
        countryId: blindBox.countryId,
      });
    }
  }, [blindBox]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!blindBox) return;

    await onSubmit(blindBox.id, formData);
  };

  const handleClose = () => {
    setFormData({ name: "", price: 0, stock: 0, countryId: 0 });
    onHide();
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Update Blind Box</Modal.Title>
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
            <Form.Label>Category *</Form.Label>
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
            Update
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default UpdateBlindBoxModal;
