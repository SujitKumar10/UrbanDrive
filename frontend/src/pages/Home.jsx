import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import "../styles/home.css";
import { api } from "../api";

function Home() {
  const navigate = useNavigate();
  const [featuredCars, setFeaturedCars] = useState([]);

  useEffect(() => {
    api.get("/cars").then((res) => setFeaturedCars(res.data.slice(0, 3))).catch(() => setFeaturedCars([]));
  }, []);

  const handleBrowse = () => navigate("/cars");
  const handleLearnMore = () => navigate("/about");

  return (
    <div className="home">
      <section className="hero">
        <div className="hero-overlay"></div>
        <div className="hero-content">
          <h1>Premium Car Rentals for Every Journey</h1>
          <p>
            Book luxury, SUVs, and economy cars instantly.
            Trusted by hundreds of happy customers.
          </p>
          <div className="hero-buttons">
            <button className="primary-btn" onClick={handleBrowse}>Browse Cars</button>
            <button className="secondary-btn" onClick={handleLearnMore}>Learn More</button>
          </div>
        </div>
      </section>

      {featuredCars.length > 0 && (
        <section className="featured">
          <h2>Featured Cars</h2>
          <div className="featured-grid">
            {featuredCars.map((c) => (
              <Link key={c.id} to="/cars" className="featured-card">
                <img src={c.imageUrl || c.image} alt={c.name} />
                <div className="featured-card-info">
                  <h3>{c.name}</h3>
                  <p>{c.type}</p>
                  <span className="featured-price">₹{c.pricePerDay?.toLocaleString()}/day</span>
                </div>
              </Link>
            ))}
          </div>
          <div className="featured-cta">
            <button className="primary-btn" onClick={handleBrowse}>View All Cars</button>
          </div>
        </section>
      )}

      <section className="why-section">
        <h2>Why Choose UrbanDrive</h2>
        <div className="why-grid">
          <div className="why-card">
            <h4>🚗 Wide Selection</h4>
            <p>Luxury, SUV, Sedan & Sports cars</p>
          </div>
          <div className="why-card">
            <h4>💳 Secure Payments</h4>
            <p>Razorpay powered checkout</p>
          </div>
          <div className="why-card">
            <h4>🛡️ 24/7 Support</h4>
            <p>We're here when you need us</p>
          </div>
        </div>
      </section>
    </div>
  );
}

export default Home;