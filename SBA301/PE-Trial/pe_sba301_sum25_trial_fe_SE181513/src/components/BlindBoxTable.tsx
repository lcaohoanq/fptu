import React, { useState } from "react";
import { Table, Button, Form } from "react-bootstrap";
import type { BlindBox } from "../types";

interface BlindBoxTableProps {
  blindBoxes: BlindBox[];
  isAdmin: boolean;
  onDelete: (blindBox: BlindBox) => void;
  onUpdate: (blindBox: BlindBox) => void;
  onInlineUpdate?: (id: number, field: string, value: string | number) => void;
}

const BlindBoxTable: React.FC<BlindBoxTableProps> = ({
  blindBoxes,
  isAdmin,
  onDelete,
  onUpdate,
  onInlineUpdate,
}) => {
  const [editingCell, setEditingCell] = useState<{
    id: number;
    field: string;
  } | null>(null);
  const [editValue, setEditValue] = useState<string>("");

  const handleCellDoubleClick = (blindBox: BlindBox, field: string) => {
    if (!isAdmin) return;
    setEditingCell({ id: blindBox.id, field });
    setEditValue(String(blindBox[field as keyof BlindBox]));
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
    blindBox: BlindBox,
    field: string,
    value: string | number
  ) => {
    const isEditing =
      editingCell?.id === blindBox.id && editingCell?.field === field;

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
        onDoubleClick={() => handleCellDoubleClick(blindBox, field)}
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
          <th>Rarity</th>
          <th>Price</th>
          <th>Release Date</th>
          <th>Stock</th>
          <th>Category Name</th>
          {isAdmin && <th>Actions</th>}
        </tr>
      </thead>
      <tbody>
        {blindBoxes.map((blindBox) => (
          <tr key={blindBox.id}>
            <td>{blindBox.id}</td>
            <td>{renderEditableCell(blindBox, "name", blindBox.name)}</td>
            <td>{renderEditableCell(blindBox, "rarity", blindBox.rarity)}</td>
            <td>{renderEditableCell(blindBox, "price", blindBox.price)}</td>
            <td>{new Date(blindBox.releaseDate).toLocaleDateString()}</td>
            <td>{renderEditableCell(blindBox, "stock", blindBox.stock)}</td>
            <td>{blindBox.categoryName}</td>
            {isAdmin && (
              <td>
                <Button
                  variant="warning"
                  size="sm"
                  className="me-2"
                  onClick={() => onUpdate(blindBox)}
                >
                  Update
                </Button>
                <Button
                  variant="danger"
                  size="sm"
                  onClick={() => onDelete(blindBox)}
                >
                  Delete
                </Button>
              </td>
            )}
          </tr>
        ))}
      </tbody>
    </Table>
  );
};

export default BlindBoxTable;
