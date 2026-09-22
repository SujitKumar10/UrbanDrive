import { useState } from "react";
import "../styles/contact.css";
import { api } from "../api";

function Contact() {

  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const form = e.target;
    const body = {
      name: form.name.value,
      email: form.email.value,
      subject: form.subject.value,
      message: form.message.value,
    };

    try {
      await api.post("/contact", body);
      setSubmitted(true);
      form.reset();
      setTimeout(() => setSubmitted(false), 3000);
    } catch (err) {
      alert("Failed to send message. Please try again.");
    }
  };

  return (
    <div className="contact-page">

      <section className="contact-hero">
        <h1>Let’s Get In Touch</h1>
        <p>Have questions about rentals or bookings? We’d love to help you.</p>
      </section>

      <section className="contact-container">

        {/* Left Side Info */}
        <div className="contact-info">
          <h2>Contact Information</h2>

          <div className="info-box">
            <h4>📍 Location</h4>
            <p>Mumbai, Maharashtra, India</p>
          </div>

          <div className="info-box">
            <h4>📞 Phone</h4>
            <p>+91 98765 43210</p>
          </div>

          <div className="info-box">
            <h4>✉ Email</h4>
            <p>support@urbandrive.com</p>
          </div>

          <div className="info-box">
            <h4>🕒 Working Hours</h4>
            <p>24/7 Customer Support</p>
          </div>
        </div>

        {/* Right Side Form */}
        <div className="contact-form">
          <h2>Send a Message</h2>

          <form onSubmit={handleSubmit}>
            <input name="name" type="text" placeholder="Your Name" required />
            <input name="email" type="email" placeholder="Your Email" required />
            <input name="subject" type="text" placeholder="Subject" />
            <textarea name="message" rows="5" placeholder="Your Message"></textarea>

            <button type="submit">Send Message</button>
          </form>
        </div>

      </section>

      {/* 🎉 Success Popup */}
      {submitted && (
        <div className="success-overlay">
          <div className="success-box">
            <div className="checkmark">✔</div>
            <h3>Message Sent Successfully!</h3>
          </div>
        </div>
      )}

    </div>
  );
}

export default Contact;