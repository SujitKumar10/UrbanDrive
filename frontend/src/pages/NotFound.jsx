import { Link } from "react-router-dom";
import "../styles/booking.css";

function NotFound() {
  return (
    <div className="booking-page">
      <div className="booking-empty">
        <h1 style={{ fontSize: "72px", marginBottom: "16px" }}>404</h1>
        <h2>Page Not Found</h2>
        <p>The page you're looking for doesn't exist or has been moved.</p>
        <Link to="/" className="booking-btn" style={{ display: "inline-block", marginTop: "24px" }}>
          Back to Home
        </Link>
      </div>
    </div>
  );
}

export default NotFound;
