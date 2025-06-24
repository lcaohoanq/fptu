import { useEffect, useState } from "react";
import { Button, Form, Image, Modal } from "react-bootstrap";
import Table from "react-bootstrap/Table";
import Container from "react-bootstrap/esm/Container";
import { useForm } from "react-hook-form";
import toast, { Toaster } from "react-hot-toast";
import { employeeApi } from "../../../apis/api.config";

export default function ListOfEmployees() {
  const [data, setData] = useState([]);
  const [show, setShow] = useState(false);

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
    try {
      const response = await employeeApi.get("/accounts");
      const sortedData = response.data.data.sort(
        (a, b) => parseInt(b.empId) - parseInt(a.empId),
      );
      setData(sortedData);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const onSubmit = async (data) => {
    try {
      const response = await employeeApi.post("/accounts/create-new-employee", data);
      setShow(false);
      fetchData();
      reset();
      setValue("");
      toast.success("Employee added successfully!");
    } catch (error) {
      console.log(error);
      toast.error(error.response.data.reason || "Failed to add employee");
    }
  };
  return (
    <Container>
      <Toaster />
      <div className="p-3">
        <button onClick={handleShow} type="button" className="btn btn-primary">
          <i className="bi bi-node-plus"> Add new employee</i>
        </button>
      </div>

      <Table striped bordered hover className="my-5">
        <thead>
          <tr>
            <th>Image</th>
            <th>Name</th>
            <th>Gender</th>
            <th>Designation</th>
          </tr>
        </thead>
        <tbody>
          {data.map((a) => (
            <tr key={a.id}>
              <td>
                <Image src={a.url} width="100%" thumbnail />
              </td>
              <td>{a.name}</td>
              <td>
                {a.gender === "male" ? (
                  <span
                    className="badge"
                    style={{ backgroundColor: "#6ea8fe" }}
                  >
                    Male <i className="bi bi-gender-male"></i>
                  </span>
                ) : a.gender === "female" ? (
                  <span
                    className="badge"
                    style={{ backgroundColor: "#efadce" }}
                  >
                    Female <i className="bi bi-gender-female"></i>
                  </span>
                ) : (
                  <span
                    className="badge"
                    style={{ backgroundColor: "#d3d3d3" }}
                  >
                    Other <i className="bi bi-gender-ambiguous"></i>
                  </span>
                )}
              </td>
              <td>{a.role_name}</td>
            </tr>
          ))}
        </tbody>
      </Table>
      <Modal show={show} onHide={handleClose} backdrop="static">
        <Modal.Header closeButton>
          <Modal.Title>New Employee</Modal.Title>
        </Modal.Header>        <Modal.Body>
          <form onSubmit={handleSubmit(onSubmit)}>
            <Form.Group className="mb-3" controlId="nameInput">
              <Form.Label>Name</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter employee name"
                autoFocus
                {...register("name", { 
                  required: "Name is required",
                  minLength: {
                    value: 2,
                    message: "Name must be at least 2 characters"
                  }
                })}
              />
              {errors.name && (
                <p className="text-warning">{errors.name.message}</p>
              )}
            </Form.Group>
            
            <Form.Group className="mb-3" controlId="emailInput">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                placeholder="Enter email address"
                {...register("email", { 
                  required: "Email is required",
                  pattern: {
                    value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                    message: "Email is not valid"
                  }
                })}
              />
              {errors.email && (
                <p className="text-warning">{errors.email.message}</p>
              )}
            </Form.Group>
            
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
