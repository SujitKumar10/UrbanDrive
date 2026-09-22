import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/booking.css";
import { api } from "../api";

function MyBookings() {
  const navigate = useNavigate();
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showCancelModal, setShowCancelModal] = useState(false);
  const [selectedBooking, setSelectedBooking] = useState(null);
  const [cancelReason, setCancelReason] = useState("");
  const [cancelling, setCancelling] = useState(false);

  useEffect(() => {
    api
      .get("/bookings/my")
      .then((res) => setBookings(res.data))
      .catch(() => setBookings([]))
      .finally(() => setLoading(false));
  }, []);

  const handleCancelClick = (booking) => {
    setSelectedBooking(booking);
    setShowCancelModal(true);
  };

  const handleCancelSubmit = () => {
    if (!cancelReason.trim()) {
      alert("Please provide a reason for cancellation");
      return;
    }

    setCancelling(true);
    api
      .post(`/bookings/${selectedBooking.id}/cancel`, { reason: cancelReason })
      .then(() => {
        alert("Cancellation request submitted successfully");
        setShowCancelModal(false);
        setCancelReason("");
        setSelectedBooking(null);
        // Refresh bookings
        return api.get("/bookings/my");
      })
      .then((res) => setBookings(res.data))
      .catch((err) => {
        console.error("Error submitting cancellation request:", err);
        alert("Failed to submit cancellation request");
      })
      .finally(() => setCancelling(false));
  };

  const canCancelBooking = (booking) => {
    return booking.status === "CONFIRMED" && !booking.cancellationRequestStatus;
  };

  if (loading) {
    return (
      <div className="booking-page">
        <div className="booking-empty">
          <p>Loading your bookings...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="booking-page">
      <div className="my-bookings-container">
        <h1>My Bookings</h1>
        {bookings.length === 0 ? (
          <div className="booking-empty">
            <p>You have no bookings yet.</p>
            <button onClick={() => navigate("/cars")} className="booking-btn">
              Browse Cars
            </button>
          </div>
        ) : (
          <div className="bookings-list">
            {bookings.map((b) => (
              <div key={b.id} className="booking-card">
                <img
                  src={b.car?.imageUrl || b.car?.image}
                  alt={b.car?.name}
                />
                <div className="booking-card-info">
                  <h3>{b.car?.name}</h3>
                  <p className="car-type">{b.car?.type}</p>
                  <p>
                    {b.startDate} to {b.endDate}
                  </p>
                  <p className="total-price">₹{b.totalPrice?.toLocaleString()}</p>
                  <span className={`status-badge ${b.status?.toLowerCase()}`}>
                    {b.status}
                  </span>
                  {b.cancellationRequestStatus && (
                    <span className={`status-badge ${b.cancellationRequestStatus?.toLowerCase()}`}>
                      Cancellation {b.cancellationRequestStatus}
                    </span>
                  )}
                  {canCancelBooking(b) && (
                    <button 
                      onClick={() => handleCancelClick(b)}
                      className="cancel-btn"
                    >
                      Cancel Booking
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
      
      {/* Cancellation Modal */}
      {showCancelModal && selectedBooking && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h2>Cancel Booking</h2>
            <div className="booking-summary">
              <p><strong>Car:</strong> {selectedBooking.car?.name}</p>
              <p><strong>Dates:</strong> {selectedBooking.startDate} to {selectedBooking.endDate}</p>
              <p><strong>Total Price:</strong> ₹{selectedBooking.totalPrice?.toLocaleString()}</p>
            </div>
            <div className="form-group">
              <label htmlFor="cancelReason">Reason for cancellation:</label>
              <textarea
                id="cancelReason"
                value={cancelReason}
                onChange={(e) => setCancelReason(e.target.value)}
                placeholder="Please provide a reason for cancellation..."
                rows="4"
                required
              />
            </div>
            <div className="modal-actions">
              <button
                type="button"
                onClick={() => {
                  setShowCancelModal(false);
                  setCancelReason("");
                  setSelectedBooking(null);
                }}
                className="cancel-modal-btn"
                disabled={cancelling}
              >
                Back
              </button>
              <button
                type="button"
                onClick={handleCancelSubmit}
                className="confirm-cancel-btn"
                disabled={cancelling}
              >
                {cancelling ? "Submitting..." : "Submit Cancellation Request"}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default MyBookings;
