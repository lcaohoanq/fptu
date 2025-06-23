import "bootstrap/dist/css/bootstrap.min.css";
import { Routes, Route } from "react-router";
import ListOfOrchids from "./components/ListOfOrchids";
import EditOrchid from "./components/EditOrchid";
import HomeScreen from "./components/HomeScreen";
import NavBar from "./components/NavBar";
import ListOfEmployees from "./components/ListOfEmployees";
import DetailOrchid from "./components/DetailOrchid";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";
import Cart from "./pages/Cart";
function App() {
  return (
    <>
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
    </>
  );
}

export default App;
