import { Link, useNavigate } from "react-router-dom";
import { useState, useRef, useEffect, useContext } from "react";
import "../styles/navbar.css";
import { LoginContext } from "../context/LoginContext";

function Navbar() {
  const { isAuthenticated, user, logout } = useContext(LoginContext);
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const menuRef = useRef();

  useEffect(() => {
    const handler = (e) => {
      if (menuRef.current && !menuRef.current.contains(e.target)) {
        setOpen(false);
      }
    };
    document.addEventListener("mousedown", handler);
    return () => document.removeEventListener("mousedown", handler);
  }, []);

  const handleLogout = () => {
    logout();
    setOpen(false);
    navigate("/");
  };

  return (
    <header className="navbar">
      <div className="nav-container">
        <div className="logo">
          <Link to="/">UrbanDrive</Link>
        </div>

        <nav className="nav-links">
          <Link to="/">Home</Link>
          <Link to="/about">About</Link>
          <Link to="/cars">Cars</Link>
          <Link to="/contact">Contact</Link>

          <div className="account-menu" ref={menuRef}>
            <button
              className="account-btn"
              onClick={() => setOpen(!open)}
            >
              {isAuthenticated ? (user?.name || "Account") + " ▾" : "Account ▾"}
            </button>

            {open && (
              <div className="dropdown">
                {isAuthenticated ? (
                  <>
                    <span className="dropdown-email">{user?.email}</span>
                    <Link to="/my-bookings" onClick={() => setOpen(false)}>My Bookings</Link>
                    {user?.role?.roleName === "ROLE_ADMIN" && (
                      <Link to="/admin/dashboard" onClick={() => setOpen(false)}>Admin Panel</Link>
                    )}
                    <button type="button" className="dropdown-logout" onClick={handleLogout}>
                      Logout
                    </button>
                  </>
                ) : (
                  <>
                    <Link to="/login" onClick={() => setOpen(false)}>Login</Link>
                    <Link to="/register" onClick={() => setOpen(false)}>Register</Link>
                  </>
                )}
              </div>
            )}
          </div>
        </nav>
      </div>
    </header>
  );
}

export default Navbar;