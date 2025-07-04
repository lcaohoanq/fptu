import React, { useEffect, useState } from "react";
import { Alert, Button, Container } from "react-bootstrap";
import { blindBoxAPI, countryAPI } from "../apis/api.config";
import BlindBoxTable from "../components/BlindBoxTable";
import CreateBlindBoxModal from "../components/CreateBlindBoxModal";
import DeleteConfirmationModal from "../components/DeleteConfirmationModal";
import UpdateBlindBoxModal from "../components/UpdateBlindBoxModal";
import type { Country, Car, CreateCarRequest, Role } from "../types";

interface BlindBoxManagementProps {
  user: {
    role: Role;
  } | null;
}

const BlindBoxManagement: React.FC<BlindBoxManagementProps> = ({ user }) => {
  const [blindBoxes, setBlindBoxes] = useState<Car[]>([]);
  const [countries, setCountries] = useState<Country[]>([]);
  const [loading, setLoading] = useState(true);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [selectedBlindBox, setSelectedBlindBox] = useState<Car | null>(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [blindBoxData, countryData] = await Promise.all([
        blindBoxAPI.getAll(),
        countryAPI.getAll(),
      ]);
      setBlindBoxes(blindBoxData);
      setCountries(countryData);
    } catch {
      setError("Failed to fetch data");
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async (formData: CreateCarRequest) => {
    try {
      const newBlindBox = await blindBoxAPI.create(formData);
      setBlindBoxes([newBlindBox, ...blindBoxes]); // Add to top of list
      setShowCreateModal(false);
      setSuccess("Blind box created successfully!");
      setTimeout(() => setSuccess(""), 3000);
    } catch {
      setError("Failed to create blind box");
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
    } catch {
      setError("Failed to delete blind box");
    }
  };

  const handleUpdate = async (id: number, formData: Partial<Car>) => {
    try {
      const updatedCar = await blindBoxAPI.update(id, formData);
      setBlindBoxes(
        blindBoxes.map((box) => (box.id === id ? updatedCar : box))
      );
      setShowUpdateModal(false);
      setSelectedBlindBox(null);
      setSuccess("Blind box updated successfully!");
      setTimeout(() => setSuccess(""), 3000);
    } catch {
      setError("Failed to update blind box");
    }
  };

  const handleInlineUpdate = async (
    id: number,
    field: string,
    value: string | number
  ) => {
    try {
      const updateData = { [field]: value };
      const updatedBlindBox = await blindBoxAPI.update(id, updateData);
      setBlindBoxes(
        blindBoxes.map((box) => (box.id === id ? updatedBlindBox : box))
      );
      setSuccess(`${field} updated successfully!`);
      setTimeout(() => setSuccess(""), 2000);
    } catch {
      setError(`Failed to update ${field}`);
      setTimeout(() => setError(""), 3000);
    }
  };

  const handleUpdateClick = (car: Car) => {
    setSelectedBlindBox(car);
    setShowUpdateModal(true);
  };

  const handleDeleteClick = (car: Car) => {
    setSelectedBlindBox(car);
    setShowDeleteModal(true);
  };

  const isAdmin = user?.role === "ADMIN";
  const isStaff = user?.role === "STAFF";

  if (loading) return <div className="text-center">Loading...</div>;

  return (
    <Container className="mt-4">
      <h2>Blind Boxes Management</h2>

      {error && <Alert variant="danger">{error}</Alert>}
      {success && <Alert variant="success">{success}</Alert>}

      {(isAdmin || isStaff) && (
        <Button
          variant="primary"
          className="mb-3"
          onClick={() => setShowCreateModal(true)}
        >
          Create New Blind Box
        </Button>
      )}

      <BlindBoxTable
        blindBoxes={blindBoxes}
        isAdmin={isAdmin || isStaff}
        onUpdate={handleUpdateClick}
        onDelete={isAdmin ? handleDeleteClick : undefined}
        onInlineUpdate={handleInlineUpdate}
      />

      {/* Create Modal */}
      <CreateBlindBoxModal
        show={showCreateModal}
        onHide={() => setShowCreateModal(false)}
        onSubmit={handleCreate}
        countries={countries}
      />

      {/* Update Modal */}
      <UpdateBlindBoxModal
        show={showUpdateModal}
        onHide={() => setShowUpdateModal(false)}
        onSubmit={handleUpdate}
        countries={countries}
        blindBox={selectedBlindBox}
      />

      {/* Delete Confirmation Modal */}
      <DeleteConfirmationModal
        show={showDeleteModal}
        onHide={() => setShowDeleteModal(false)}
        onConfirm={handleDelete}
        blindBox={selectedBlindBox}
      />
    </Container>
  );
};

export default BlindBoxManagement;
