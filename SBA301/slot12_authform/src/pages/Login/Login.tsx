import React, { useState } from "react";
import "./Login.css";
import axios from "axios";
import { FaEnvelope, FaLock } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    setErrorMsg("");

    if (!email || !pwd) {
      setErrorMsg("Please fill in both email and password.");
      return;
    }

    try {
      const res = await axios.post("http://localhost:8081/students/login", {
        username: email,
        pass: pwd,
      });

      if (res.status === 200) {
        alert("Login successful");
        // Redirect logic here
      }
    } catch (error) {
      setErrorMsg("Invalid email or password.");
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={(e) => e.preventDefault()}>
        <h1>Welcome Back</h1>

        {errorMsg && <div className="error-message">{errorMsg}</div>}

        <div className="form-group">
          <label>Email</label>
          <div className="input-wrapper">
            <FaEnvelope className="input-icon" />
            <input
              className="form-control"
              placeholder="Enter your email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
        </div>

        <div className="form-group">
          <label>Password</label>
          <div className="input-wrapper">
            <FaLock className="input-icon" />
            <input
              className="form-control"
              placeholder="Enter your password"
              type="password"
              value={pwd}
              onChange={(e) => setPwd(e.target.value)}
            />
          </div>
        </div>

        <button className="login-button" type="submit" onClick={handleLogin}>
          Login
        </button>

        <div>
          <button onClick={() => navigate("/register")}>Register here</button>
        </div>
      </form>
    </div>
  );
};

export default Login;
