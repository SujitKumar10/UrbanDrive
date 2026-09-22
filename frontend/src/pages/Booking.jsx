import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "../styles/booking.css";
import { api } from "../api";

const loadRazorpay = () => {
  return new Promise((resolve) => {
    if (window.Razorpay) {
      resolve(window.Razorpay);
      return;
    }
    const script = document.createElement("script");
    script.src = "https://checkout.razorpay.com/v1/checkout.js";
    script.onload = () => resolve(window.Razorpay);
    document.body.appendChild(script);
  });
};

function Booking() {
  const location = useLocation();
  const navigate = useNavigate();
  const carFromState = location.state?.car;
  const carFromStorage = (() => {
    try {
      const p = sessionStorage.getItem("bookingCar");
      return p ? JSON.parse(p) : null;
    } catch { return null; }
  })();
  const car = carFromState || carFromStorage;
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const today = new Date().toISOString().split("T")[0];
    setStartDate(today);
    const nextWeek = new Date();
    nextWeek.setDate(nextWeek.getDate() + 15);
    setEndDate(nextWeek.toISOString().split("T")[0]);
  }, []);

  if (!car) {
    return (
      <div className="booking-page">
        <div className="booking-empty">
          <h2>No Car Selected</h2>
          <p>Please select a car from our fleet to proceed with booking.</p>
          <button onClick={() => navigate("/cars")} className="booking-btn">
            Browse Cars
          </button>
        </div>
      </div>
    );
  }

  const pricePerDay = car.price ?? car.pricePerDay ?? 0;
  const getDays = () => {
    if (!startDate || !endDate) return 0;
    const start = new Date(startDate);
    const end = new Date(endDate);
    const diff = Math.ceil((end - start) / (1000 * 60 * 60 * 24)) + 1;
    return Math.max(0, diff);
  };
  const days = getDays();
  const totalPrice = days * pricePerDay;

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    if (days <= 0) {
      setError("End date must be after start date.");
      return;
    }
    const token = localStorage.getItem("token");
    if (!token) {
      setError("Please log in again to continue.");
      setTimeout(() => navigate("/login"), 1500);
      return;
    }
    setSubmitting(true);
    try {
      const { data: orderData } = await api.post("/payments/create-order", {
        amount: totalPrice,
      }, {
        headers: { Authorization: `Bearer ${token}` },
      });
      const Razorpay = await loadRazorpay();
      const options = {
        key: orderData.razorpayKey,
        order_id: orderData.orderId,
        name: "UrbanDrive",
        description: `Booking: ${car.name} (${days} days)`,
        handler: async (response) => {
          const t = localStorage.getItem("token");
          try {
            await api.post("/payments/verify", {
              razorpayOrderId: response.razorpay_order_id,
              razorpayPaymentId: response.razorpay_payment_id,
              razorpaySignature: response.razorpay_signature,
              carId: car.id,
              startDate,
              endDate,
            }, t ? { headers: { Authorization: `Bearer ${t}` } } : {});
            sessionStorage.removeItem("bookingCar");
            alert("Payment successful! Booking confirmed. Thank you for choosing UrbanDrive.");
            navigate("/my-bookings");
          } catch (err) {
            setError(err.response?.data?.message || "Payment verification failed.");
          } finally {
            setSubmitting(false);
          }
        },
        prefill: { email: JSON.parse(localStorage.getItem("user") || "{}")?.email },
        theme: { color: "#00f5ff" },
      };
      const rzp = new Razorpay(options);
      rzp.on("payment.failed", () => {
        setError("Payment failed. Please try again.");
        setSubmitting(false);
      });
      rzp.open();
    } catch (err) {
      if (err.response?.status === 401) {
        setError("Session expired. Please log in again.");
        setTimeout(() => navigate("/login"), 2000);
      } else {
        setError(err.response?.data?.message || "Failed to initiate payment.");
      }
      setSubmitting(false);
    }
  };

  return (
    <div className="booking-page">
      <div className="booking-container">
        <div className="booking-car">
          <img src={car.image || car.imageUrl} alt={car.name} />
          <div className="booking-car-info">
            <h1>{car.name}</h1>
            <p className="car-type">{car.type}</p>
            <p className="car-price">₹{pricePerDay} / day</p>
          </div>
        </div>

        <form className="booking-form" onSubmit={handleSubmit}>
          <h2>Select Dates</h2>
          <div className="form-row">
            <label>Start Date</label>
            <input
              type="date"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
              min={new Date().toISOString().split("T")[0]}
              required
            />
          </div>
          <div className="form-row">
            <label>End Date</label>
            <input
              type="date"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
              min={startDate || new Date().toISOString().split("T")[0]}
              required
            />
          </div>
          <div className="booking-summary">
            <p>{days} day{days !== 1 ? "s" : ""} × ₹{pricePerDay}</p>
            <h3>Total: ₹{totalPrice.toLocaleString()}</h3>
          </div>
          {error && <p className="booking-error">{error}</p>}
          <button type="submit" className="booking-btn" disabled={submitting}>
            {submitting ? "Opening payment..." : "Pay ₹" + totalPrice.toLocaleString() + " with Razorpay"}
          </button>
        </form>
      </div>
    </div>
  );
}

export default Booking;
