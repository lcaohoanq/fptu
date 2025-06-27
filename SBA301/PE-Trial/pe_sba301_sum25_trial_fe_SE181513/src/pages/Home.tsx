import React, { useState, useEffect } from "react";
import { Container, Table, Alert } from "react-bootstrap";
import type { BlindBox, Category } from "../types";
import { blindBoxAPI, categoryAPI } from "../apis/api.config";

const Home = () => {
  const [blindBoxes, setBlindBoxes] = useState<BlindBox[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const blindBoxData = await blindBoxAPI.getAll();
      setBlindBoxes(blindBoxData);
    } catch (err: any) {
      setError("Failed to fetch data");
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="text-center">Loading...</div>;

  return (
    <Container className="mt-4">
      <h1>Welcome to Blind Boxes Management System</h1>
      <p>Browse our available blind boxes below:</p>

      {error && <Alert variant="danger">{error}</Alert>}

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
            </tr>
          ))}
        </tbody>
      </Table>
    </Container>
  );
};

export default Home;
