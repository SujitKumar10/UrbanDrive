import { Link } from "react-router-dom";
import "../styles/footer.css";

function Footer() {
  return (
    <footer className="footer">
      <div className="footer-container">

        {/* Brand Info */}
        <div className="footer-section">
          <h2 className="footer-logo">UrbanDrive</h2>
          <p>
            Experience luxury and comfort with our premium car rental services.
            Your journey begins with us.
          </p>
        </div>

        {/* Quick Links */}
        <div className="footer-section">
          <h3>Quick Links</h3>
          <Link to="/">Home</Link>
          <Link to="/about">About</Link>
          <Link to="/cars">Cars</Link>
          <Link to="/contact">Contact</Link>
          <Link to="/my-bookings">My Bookings</Link>
          <Link to="/login">Login</Link>
          <Link to="/register">Register</Link>
        </div>

        {/* Contact Info */}
        <div className="footer-section">
          <h3>Contact Us</h3>
          <p>Email: support@urbandrive.com</p>
          <p>Phone: +91 98765 43210</p>
          <p>Location: Mumbai, India</p>
          <p>Working Hours: 24/7 Support</p>
        </div>

        {/* Why Choose Us */}
<div className="footer-section">
  <h3>Why Choose Us</h3>
  <ul className="why-list">
    <li>✔ Premium & Well Maintained Cars</li>
    <li>✔ Affordable Daily & Weekly Plans</li>
    <li>✔ 24/7 Customer Support</li>
    <li>✔ Easy Online Booking</li>
  </ul>
</div>
       

      </div>

      <div className="footer-bottom">
        © {new Date().getFullYear()} UrbanDrive | Designed with by Sujit Kumar
      </div>
    </footer>
  );
}

export default Footer;