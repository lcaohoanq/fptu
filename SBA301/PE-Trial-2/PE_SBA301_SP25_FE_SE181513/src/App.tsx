import "bootstrap/dist/css/bootstrap.min.css";
import { useEffect, useState } from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import { Toaster } from "react-hot-toast";
import NavigationBar from "./components/NavigationBar";
import CarManagement from "./pages/CarManagement";
import Home from "./pages/Home";

function App() {
  const [user, setUser] = useState<any>(null);
  const [token, setToken] = useState<string | null>(null);

  useEffect(() => {
    // Check if user is already logged in
    const savedToken = localStorage.getItem("token");
    const savedUser = localStorage.getItem("user");

    if (savedToken && savedUser) {
      setToken(savedToken);
      setUser(JSON.parse(savedUser));
    }
  }, []);

  const handleLogin = (userData: any, accessToken: string) => {
    setUser(userData);
    setToken(accessToken);
  };

  const handleLogout = () => {
    setUser(null);
    setToken(null);
  };

  return (
    <Router>
      <div className="App">
        <NavigationBar
          user={user}
          onLogin={handleLogin}
          onLogout={handleLogout}
        />

        <Routes>
          {/* <Route path="/" element={<Home />} /> */}
          <Route path="/infor-cars" element={<CarManagement user={user} />} />
          <Route
            path="/infor-cars/create"
            element={<CarManagement user={user} />}
          />
        </Routes>

        {/* Toast notifications container */}
        <Toaster
          position="top-right"
          reverseOrder={false}
          gutter={8}
          containerClassName=""
          containerStyle={{
            zIndex: 9999, // Higher than Bootstrap modal z-index (1055)
          }}
          toastOptions={{
            // Default options
            duration: 4000,
            style: {
              background: "#363636",
              color: "#fff",
              zIndex: 9999,
            },
            // Success
            success: {
              duration: 3000,
              style: {
                background: "#10b981",
                zIndex: 9999,
              },
            },
            // Error
            error: {
              duration: 5000,
              style: {
                background: "#ef4444",
                zIndex: 9999,
              },
            },
          }}
        />
      </div>
    </Router>
  );
}

export default App;
