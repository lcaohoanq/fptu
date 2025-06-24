import "bootstrap/dist/css/bootstrap.min.css";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import { AuthProvider } from "./contexts/auth.context";
import NavBar from "./components/NavBar";
import HomeScreen from "./pages/Home/Home";
import ListOfEmployees from "./pages/Management/EmployeeList/ListOfEmployees";
import ListOfOrchids from "./pages/Management/OrchidList/ListOfOrchids";
import DetailOrchid from "./pages/DetailOrchid/DetailOrchid";
import Login from "./pages/Auth/Login/Login";
import ProtectedRoute from "./components/ProtectedRoute";
import Register from "./pages/Auth/Register/Register";
import Cart from "./pages/Cart/Cart";
import EditOrchid from "./pages/Management/EditOrchid/EditOrchid";
import { Orders } from "./pages/Management/Order";
import { OrderDetail } from "./pages/Management/OrderDetail";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <NavBar />
        <Routes>
          <Route path="/" element={<HomeScreen />} />

          {/* Public Orders Routes */}
          <Route path="/orders">
            <Route index element={
              <ProtectedRoute>
                <Orders />
              </ProtectedRoute>
            } />
            <Route path=":orderId" element={
              <ProtectedRoute>
                <OrderDetail />
              </ProtectedRoute>
            } />
          </Route>

          {/* Management Routes (Admin only) */}
          <Route path="/manage">
            <Route path="employees" element={<ListOfEmployees />} />
            <Route path="orchids">
              <Route index element={<ListOfOrchids />} />
              <Route path="edit/:id" element={<EditOrchid />} />
            </Route>
            <Route path="orders">
              <Route index element={<Orders />} />
              <Route path=":orderId" element={<OrderDetail />} />
            </Route>
          </Route>

          <Route path="/detail/:id" element={<DetailOrchid />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route
            path="/cart"
            element={
              <ProtectedRoute>
                <Cart />
              </ProtectedRoute>
            }
          />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
