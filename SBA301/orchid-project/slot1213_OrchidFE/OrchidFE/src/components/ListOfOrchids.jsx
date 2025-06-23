import { useEffect, useState } from "react";
import {
  Button,
  Form,
  FormGroup,
  Image,
  Modal,
  Spinner,
} from "react-bootstrap";
import Table from "react-bootstrap/Table";
import Container from "react-bootstrap/esm/Container";
import { useForm } from "react-hook-form";
import toast, { Toaster } from "react-hot-toast";
import { Link } from "react-router";
import { orchidApi } from "../apis/api.config";

export default function ListOfOrchids() {
  const [data, setData] = useState([]);
  const [show, setShow] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const {
    register,
    handleSubmit,
    formState: { errors },
    control,
    reset,
  } = useForm();
  const [value, setValue] = useState("");
  useEffect(() => {
    fetchData();
  }, []);
  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await orchidApi.get("/orchids");
      const sortedData = response.data.sort(
        (a, b) => parseInt(b.id) - parseInt(a.id),
      );
      setData(sortedData);
    } catch (error) {
      console.error("Error fetching data:", error);
    } finally {
      setLoading(false);
    }
  };  const handleDelete = async (id) => {
    try {
      const response = await orchidApi.delete(`/orchids/${id}`);
      fetchData();
      toast.success("Orchid deleted successfully!");
    } catch (error) {
      console.log(error.message);
      toast.error("Orchid deleted failed!");
    }
  };  const onSubmit = async (data) => {
    try {
      const response = await orchidApi.post("/orchids", data, {
        headers: { "Content-Type": "application/json" },
      });
      setShow(false);
      fetchData();
      reset();
      setValue("");
      toast.success("Orchid added successfully!");
    } catch (error) {
      console.error("Error adding orchid:", error);
      let errorMessage = "Orchid added fail!";
      
      if (error.response) {
        // Server responded with error status
        if (error.response.data) {
          if (typeof error.response.data === 'string') {
            errorMessage = error.response.data;
          } else if (error.response.data.message) {
            errorMessage = error.response.data.message;
          } else if (error.response.data.reason) {
            errorMessage = error.response.data.reason;
          }
        }
      } else if (error.request) {
        errorMessage = "Network error - please check your connection";
      } else {
        errorMessage = error.message || "Unknown error occurred";
      }
      
      toast.error(errorMessage);
    }
  };
  return (
    <Container>
      <Toaster />
      {loading ? (
        <div className="d-flex justify-content-center align-items-center my-5">
          <Spinner animation="border" variant="primary" role="status">
            <span className="visually-hidden">Loading...</span>
          </Spinner>
        </div>
      ) : (
        <Table striped bordered hover my-5>
          <thead>
            <tr>
              <th>Image</th>
              <th>Orchid name</th>
              <th>Original</th>
              <th>
                <button
                  onClick={handleShow}
                  type="button"
                  className="btn btn-primary"
                >
                  <i className="bi bi-node-plus"> Add new orchid</i>
                </button>{" "}
              </th>
            </tr>
          </thead>
          <tbody>
            {data.map((a) => (
              <tr key={a.id}>
                <td>
                  <Image src={a.image} width={40} rounded />
                </td>
                <td>{a.orchidName}</td>

                <td>
                  {a.isNatural ? (
                    <span className="badge text-bg-success">Natural</span>
                  ) : (
                    <span className="badge text-bg-warning">Industry</span>
                  )}
                </td>
                <td>
                  <Link to={`/edit/${a.id}`}>
                    <span className="badge text-bg-primary rounded-2">
                      <i className="bi bi-pencil-square"> Edit </i>
                    </span>
                  </Link>

                  <span
                    className="badge text-bg-danger rounded-2"
                    onClick={() => {
                      if (
                        confirm("Are you sure you want to delete this orchid?")
                      ) {
                        handleDelete(a.id);
                      }
                    }}
                  >
                    <i className="bi bi-trash3"> Delete </i>
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}

      <Modal show={show} onHide={handleClose} backdrop="static">
        <Modal.Header closeButton>
          <Modal.Title>New Orchid</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form onSubmit={handleSubmit(onSubmit)}>
            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                autoFocus
                {...register("orchidName", { required: true })}
              />
              {errors.orchidName && errors.orchidName.type === "required" && (
                <p className="text-warning">Name is required</p>
              )}
            </Form.Group>
            <Form.Group
              className="mb-3"
              controlId="exampleForm.ControlTextarea1"
            >
              <Form.Label>Image</Form.Label>
              <Form.Control
                type="text"
                {...register("image", {
                  required: true,
                  pattern:
                    /(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]+\.[^\s]{2,}|www\.[a-zA-Z0-9]+\.[^\s]{2,})/gi,
                })}
              />
              {errors.image && errors.image.type === "pattern" && (
                <p className="text-warning">Image must be a valid URL</p>
              )}
            </Form.Group>
            <FormGroup>
              <Form.Check
                type="switch"
                id="custom-switch"
                label="Natural"
                {...register("isNatural")}
              />
            </FormGroup>
            <Modal.Footer>
              <Button variant="secondary" onClick={handleClose}>
                Close
              </Button>
              <Button variant="primary" type="submit">
                Save Changes
              </Button>
            </Modal.Footer>
          </form>
        </Modal.Body>
      </Modal>
    </Container>
  );
}
