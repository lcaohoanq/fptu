import React, { useEffect, useState } from "react";
import { Button, Container, Table, Modal, Form } from "react-bootstrap";
import { carAPI, countryAPI } from "../apis/api.config";
import type { Country, Car, CreateCarRequest, Role } from "../types";

interface CarManagementProps {
  user: {
    role: Role;
  } | null;
}

// Car Table Component
const CarTable: React.FC<{
  cares: Car[];
  isAdmin: boolean;
  onDelete?: (car: Car) => void;
  onUpdate: (car: Car) => void;
  onInlineUpdate?: (id: number, field: string, value: string | number) => void;
}> = ({ cares, isAdmin, onDelete, onUpdate, onInlineUpdate }) => {
  const [editingCell, setEditingCell] = useState<{
    id: number;
    field: string;
  } | null>(null);
  const [editValue, setEditValue] = useState<string>("");

  const handleCellDoubleClick = (car: Car, field: string) => {
    if (!isAdmin) return;
    setEditingCell({ id: car.id, field });
    setEditValue(String(car[field as keyof Car]));
  };

  const handleEditSave = () => {
    if (!editingCell || !onInlineUpdate) return;
    const value =
      editingCell.field === "price" || editingCell.field === "stock"
        ? Number(editValue)
        : editValue;
    onInlineUpdate(editingCell.id, editingCell.field, value);
    setEditingCell(null);
    setEditValue("");
  };

  const handleEditCancel = () => {
    setEditingCell(null);
    setEditValue("");
  };

  const renderEditableCell = (
    car: Car,
    field: string,
    value: string | number
  ) => {
    const isEditing =
      editingCell?.id === car.id && editingCell?.field === field;

    if (isEditing) {
      return (
        <Form.Control
          type={field === "price" || field === "stock" ? "number" : "text"}
          value={editValue}
          onChange={(e) => setEditValue(e.target.value)}
          onBlur={handleEditSave}
          onKeyDown={(e) => {
            if (e.key === "Enter") handleEditSave();
            if (e.key === "Escape") handleEditCancel();
          }}
          autoFocus
          size="sm"
        />
      );
    }

    return (
      <span
        onDoubleClick={() => handleCellDoubleClick(car, field)}
        style={{ cursor: isAdmin ? "pointer" : "default" }}
        title={isAdmin ? "Double-click to edit" : ""}
      >
        {field === "price" ? `$${value}` : value}
      </span>
    );
  };

  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Price</th>
          <th>Created Date</th>
          <th>Updated Date</th>
          <th>Stock</th>
          <th>Country</th>
          {isAdmin && <th>Actions</th>}
        </tr>
      </thead>
      <tbody>
        {cares.map((car) => (
          <tr key={car.id}>
            <td>{car.id}</td>
            <td>{renderEditableCell(car, "name", car.name)}</td>
            <td>{renderEditableCell(car, "price", car.price)}</td>
            <td>{new Date(car.createdAt).toLocaleDateString()}</td>
            <td>{new Date(car.updatedAt).toLocaleDateString()}</td>
            <td>{renderEditableCell(car, "stock", car.stock)}</td>
            <td>{car.country.name}</td>
            {isAdmin && (
              <td>
                <Button
                  variant="warning"
                  size="sm"
                  className="me-2"
                  onClick={() => onUpdate(car)}
                >
                  Update
                </Button>
                {onDelete && (
                  <Button
                    variant="danger"
                    size="sm"
                    onClick={() => onDelete(car)}
                  >
                    Delete
                  </Button>
                )}
              </td>
            )}
          </tr>
        ))}
      </tbody>
    </Table>
  );
};

// Create Car Modal Component
const CreateCarModal: React.FC<{
  show: boolean;
  onHide: () => void;
  onSubmit: (formData: CreateCarRequest) => Promise<void>;
  countries: Country[];
}> = ({ show, onHide, onSubmit, countries }) => {
  const [formData, setFormData] = useState<CreateCarRequest>({
    name: "",
    price: 0,
    stock: 1,
    countryId: 0,
  });

  // Reset form when modal opens and countries are loaded
  useEffect(() => {
    if (show && countries.length > 0) {
      setFormData((prev) => ({
        ...prev,
        countryId: countries[0].id, // Set to first country when modal opens
      }));
    }
  }, [show, countries]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await onSubmit(formData);
      setFormData({
        name: "",
        price: 0,
        stock: 1,
        countryId: countries[0]?.id || 0,
      });
      onHide();
    } catch (error) {
      console.error("Form submission error:", error);
    }
  };

  const handleClose = () => {
    setFormData({
      name: "",
      price: 0,
      stock: 1,
      countryId: countries[0]?.id || 0,
    });
    onHide();
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Create New Car</Modal.Title>
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
                setFormData({ ...formData, price: parseFloat(e.target.value) })
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
              min={5}
              max={100}
              required
            />
            <Form.Text className="text-muted">
              Stock must be between 5 and 100
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

// Update Car Modal Component
const UpdateCarModal: React.FC<{
  show: boolean;
  onHide: () => void;
  onSubmit: (id: number, formData: Partial<Car>) => Promise<void>;
  countries: Country[];
  car: Car | null;
}> = ({ show, onHide, onSubmit, countries, car }) => {
  const [formData, setFormData] = useState({
    name: "",
    price: 0,
    stock: 0,
    countryId: 0,
  });

  useEffect(() => {
    if (car && countries.length > 0) {
      setFormData({
        name: car.name,
        price: car.price,
        stock: car.stock,
        countryId: car.country.id,
      });
    }
  }, [car, countries]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!car) return;

    try {
      await onSubmit(car.id, formData);
      onHide();
    } catch (error) {
      console.error("Form submission error:", error);
    }
  };

  const handleClose = () => {
    setFormData({ name: "", price: 0, stock: 0, countryId: 0 });
    onHide();
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Update Car</Modal.Title>
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
                setFormData({ ...formData, price: parseFloat(e.target.value) })
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
              min={5}
              max={100}
              required
            />
            <Form.Text className="text-muted">
              Stock must be between 5 and 100
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
            Update
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

// Delete Confirmation Modal Component
const DeleteConfirmationModal: React.FC<{
  show: boolean;
  onHide: () => void;
  onConfirm: () => Promise<void>;
  car: Car | null;
}> = ({ show, onHide, onConfirm, car }) => {
  const handleConfirm = async () => {
    await onConfirm();
  };

  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>Confirm Delete</Modal.Title>
      </Modal.Header>
      <Modal.Body>Are you sure you want to delete "{car?.name}"?</Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onHide}>
          Cancel
        </Button>
        <Button variant="danger" onClick={handleConfirm}>
          Delete
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

// Main Car Management Component
const CarManagement: React.FC<CarManagementProps> = ({ user }) => {
  const [cares, setCares] = useState<Car[]>([]);
  const [countries, setCountries] = useState<Country[]>([]);
  const [loading, setLoading] = useState(true);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [selectedCar, setSelectedCar] = useState<Car | null>(null);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [carData, countryData] = await Promise.all([
        carAPI.getAll(),
        countryAPI.getAll(),
      ]);
      setCares(carData);
      setCountries(countryData);
    } catch {
      // Error is handled by the axios interceptor with toast notifications
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async (formData: CreateCarRequest) => {
    try {
      const newCar = await carAPI.create(formData);
      setCares([newCar, ...cares]);
      setShowCreateModal(false);
    } catch {
      // Error is handled by the axios interceptor with toast notifications
    }
  };

  const handleDelete = async () => {
    if (!selectedCar) return;

    try {
      await carAPI.delete(selectedCar.id);
      setCares(cares.filter((car) => car.id !== selectedCar.id));
      setShowDeleteModal(false);
      setSelectedCar(null);
    } catch {
      // Error is handled by the axios interceptor with toast notifications
    }
  };

  const handleUpdate = async (id: number, formData: Partial<Car>) => {
    try {
      const updatedCar = await carAPI.update(id, formData);
      setCares(cares.map((car) => (car.id === id ? updatedCar : car)));
      setShowUpdateModal(false);
      setSelectedCar(null);
    } catch {
      // Error is handled by the axios interceptor with toast notifications
    }
  };

  const handleInlineUpdate = async (
    id: number,
    field: string,
    value: string | number
  ) => {
    try {
      const updateData = { [field]: value };
      const updatedCar = await carAPI.update(id, updateData);
      setCares(cares.map((car) => (car.id === id ? updatedCar : car)));
    } catch {
      // Error is handled by the axios interceptor with toast notifications
    }
  };

  const handleUpdateClick = (car: Car) => {
    setSelectedCar(car);
    setShowUpdateModal(true);
  };

  const handleDeleteClick = (car: Car) => {
    setSelectedCar(car);
    setShowDeleteModal(true);
  };

  const isAdmin = user?.role === "ADMIN";
  const isStaff = user?.role === "STAFF";

  if (loading) return <div className="text-center">Loading...</div>;

  return (
    <Container className="mt-4">
      <h2>Car Management</h2>

      {(isAdmin || isStaff) && (
        <Button
          variant="primary"
          className="mb-3"
          onClick={() => setShowCreateModal(true)}
        >
          Create New Car
        </Button>
      )}

      <CarTable
        cares={cares}
        isAdmin={isAdmin || isStaff}
        onUpdate={handleUpdateClick}
        onDelete={isAdmin ? handleDeleteClick : undefined}
        onInlineUpdate={handleInlineUpdate}
      />

      {/* Create Modal */}
      <CreateCarModal
        show={showCreateModal}
        onHide={() => setShowCreateModal(false)}
        onSubmit={handleCreate}
        countries={countries}
      />

      {/* Update Modal */}
      <UpdateCarModal
        show={showUpdateModal}
        onHide={() => setShowUpdateModal(false)}
        onSubmit={handleUpdate}
        countries={countries}
        car={selectedCar}
      />

      {/* Delete Confirmation Modal */}
      <DeleteConfirmationModal
        show={showDeleteModal}
        onHide={() => setShowDeleteModal(false)}
        onConfirm={handleDelete}
        car={selectedCar}
      />
    </Container>
  );
};

export default CarManagement;
