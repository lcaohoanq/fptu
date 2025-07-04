import { useEffect, useState } from "react";
import { Alert, Container, Table } from "react-bootstrap";
import { blindBoxAPI } from "../apis/api.config";
import type { Car } from "../types";

const Home = () => {
  const [blindBoxes, setBlindBoxes] = useState<Car[]>([]);
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
      <h1>Welcome to Car Management System</h1>
      <p>Browse our available cars below:</p>

      {error && <Alert variant="danger">{error}</Alert>}

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Created At</th>
            <th>Updated At</th>
            <th>Stock</th>
            <th>Country</th>
          </tr>
        </thead>
        <tbody>
          {blindBoxes.map((blindBox) => (
            <tr key={blindBox.id}>
              <td>{blindBox.id}</td>
              <td>{blindBox.name}</td>
              <td>${blindBox.price}</td>
              <td>{new Date(blindBox.createdAt).toLocaleDateString()}</td>
              <td>{new Date(blindBox.updatedAt).toLocaleDateString()}</td>
              <td>{blindBox.stock}</td>
              <td>{blindBox.country.name}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </Container>
  );
};

export default Home;
