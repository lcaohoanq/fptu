import "bootstrap/dist/css/bootstrap.min.css";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import ListOfOrchids from "./components/ListOfOrchids";
import EditOrchid from "./components/EditOrchid";
import HomeScreen from "./components/HomeScreen";
import NavBar from "./components/NavBar";
import ListOfEmployees from "./components/ListOfEmployees";
import DetailOrchid from "./components/DetailOrchid";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";
import Cart from "./pages/Cart";
import { AuthProvider } from "./contexts/auth.context";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <NavBar />
        <Routes>
          <Route path="/" element={<ListOfOrchids />} />
          <Route path="/manage/employees" element={<ListOfEmployees />} />
          <Route path="/home" element={<HomeScreen />} />
          <Route path="/detail/:id" element={<DetailOrchid />} />
          <Route path="/edit/:id" element={<EditOrchid />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/cart" element={<Cart/>} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;