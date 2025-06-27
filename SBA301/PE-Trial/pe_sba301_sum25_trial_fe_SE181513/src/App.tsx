import "bootstrap/dist/css/bootstrap.min.css";
import { useEffect, useState } from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import BlindBoxManagement from "./pages/BlindBoxManagement";
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
          <Route path="/" element={<Home />} />
          <Route
            path="/blind-boxes"
            element={<BlindBoxManagement user={user} />}
          />
          <Route
            path="/blind-boxes/create"
            element={<BlindBoxManagement user={user} />}
          />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
