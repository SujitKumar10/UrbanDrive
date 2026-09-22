import React from "react";
import "../styles/about.css";

const About = () => {
  return (
    <div className="about-container">

      {/* Hero Section */}
      <section className="about-hero">
        <div className="hero-content">
          <h1>Driven by Trust. Powered by Comfort.</h1>
          <p>
            At UrbanDrive, we believe every journey should be smooth, safe, 
            and memorable. Our mission is to redefine car rentals by combining 
            technology, transparency, and premium service.
          </p>
        </div>
      </section>

      {/* Story Section */}
      <section className="about-story">
        <div className="story-text">
          <h2>Our Story</h2>

          <p>
            UrbanDrive was founded with a clear vision — to make car rentals 
            simple, affordable, and accessible to everyone. We noticed how 
            traditional rental services were often complicated, expensive, 
            and lacked transparency.
          </p>

          <p>
            With that challenge in mind, we built UrbanDrive as a modern 
            platform that connects customers to a diverse fleet of vehicles 
            — from budget-friendly economy cars to high-end luxury vehicles.
          </p>

          <p>
            Our focus is not just on providing cars, but on delivering 
            experiences. Whether you're planning a family vacation, 
            business trip, weekend getaway, or daily commute — 
            we ensure you drive with confidence.
          </p>

          <p>
            Today, UrbanDrive proudly serves multiple cities with a growing 
            community of satisfied customers who trust us for reliability, 
            comfort, and seamless booking.
          </p>
        </div>

        <div className="story-image">
          <img
            src="https://images.unsplash.com/photo-1503376780353-7e6692767b70"
            alt="Car"
          />
        </div>
      </section>

      {/* Stats Section */}
      <section className="about-stats">
        <div className="stat-box">
          <h3>500+</h3>
          <p>Happy Customers</p>
        </div>

        <div className="stat-box">
          <h3>120+</h3>
          <p>Premium & Economy Cars</p>
        </div>

        <div className="stat-box">
          <h3>15+</h3>
          <p>Cities Covered Across India</p>
        </div>

        <div className="stat-box">
          <h3>24/7</h3>
          <p>Dedicated Customer Support</p>
        </div>
      </section>

      {/* Why Choose Section */}
      <section className="why-section">
        <h2>Why Choose UrbanDrive</h2>

        <div className="features-grid">
          <div className="feature-card">
            <h4>Transparent Pricing</h4>
            <p>
              We believe in honesty. No hidden charges, no surprise fees — 
              just clear pricing you can trust.
            </p>
          </div>

          <div className="feature-card">
            <h4>Wide Range of Vehicles</h4>
            <p>
              From compact city cars to luxury sedans and spacious SUVs — 
              choose the perfect ride for every occasion.
            </p>
          </div>

          <div className="feature-card">
            <h4>Fast & Easy Booking</h4>
            <p>
              Our simple interface allows you to browse, compare, 
              and book your vehicle in just a few clicks.
            </p>
          </div>

          <div className="feature-card">
            <h4>Secure Payments</h4>
            <p>
              Your safety matters. All transactions are encrypted 
              and processed securely.
            </p>
          </div>

          <div className="feature-card">
            <h4>Well Maintained Fleet</h4>
            <p>
              Every vehicle is regularly serviced and inspected 
              to ensure maximum comfort and safety.
            </p>
          </div>

          <div className="feature-card">
            <h4>Customer First Approach</h4>
            <p>
              Our support team is always ready to assist you — 
              before, during, and after your ride.
            </p>
          </div>
        </div>
      </section>

    </div>
  );
};

export default About;