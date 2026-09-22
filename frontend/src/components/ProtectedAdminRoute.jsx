import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { LoginContext } from "../context/LoginContext";

function ProtectedAdminRoute({ children }) {
  const { isAuthenticated, user } = useContext(LoginContext);
  const isAdmin = user?.role?.roleName === "ROLE_ADMIN";

  if (!isAuthenticated) return <Navigate to="/login" replace />;
  if (!isAdmin) return <Navigate to="/" replace />;
  return children;
}

export default ProtectedAdminRoute;
