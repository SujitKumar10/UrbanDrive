import { useState, useEffect } from "react";
import { useContext } from "react";
import "../styles/admin.css";
import { LoginContext } from "../context/LoginContext";
import { api } from "../api";

function AdminDashboard() {
  const { user } = useContext(LoginContext);
  const [tab, setTab] = useState("cars");
  const [cars, setCars] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [users, setUsers] = useState([]);
  const [contacts, setContacts] = useState([]);
  const [cancellations, setCancellations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modal, setModal] = useState(null);
  const [formData, setFormData] = useState({ name: "", type: "", pricePerDay: "", imageUrl: "", available: true });
  const [cancellationModal, setCancellationModal] = useState(null);
  const [adminComment, setAdminComment] = useState("");

  const fetchCars = () => api.get("/admin/cars").then((r) => setCars(r.data));
  const fetchBookings = () => api.get("/admin/bookings").then((r) => setBookings(r.data));
  const fetchUsers = () => api.get("/admin/users").then((r) => setUsers(r.data));
  const fetchContacts = () => api.get("/admin/contacts").then((r) => setContacts(r.data));
  const fetchCancellations = () => api.get("/admin/cancellations").then((r) => setCancellations(r.data));

  useEffect(() => {
    setLoading(true);
    const token = localStorage.getItem("token");
    if (!token) {
      setLoading(false);
      return;
    }
    const t = setTimeout(() => {
      Promise.all([fetchCars(), fetchBookings(), fetchUsers(), fetchContacts(), fetchCancellations()])
        .catch(() => {})
        .finally(() => setLoading(false));
    }, 150);
    return () => clearTimeout(t);
  }, []);

  useEffect(() => {
    if (tab === "cars") fetchCars();
    else if (tab === "bookings") fetchBookings();
    else if (tab === "users") fetchUsers();
    else if (tab === "contacts") fetchContacts();
    else if (tab === "cancellations") fetchCancellations();
  }, [tab]);

  const openAddCar = () => {
    setFormData({ name: "", type: "", pricePerDay: "", imageUrl: "", available: true });
    setModal("add");
  };

  const openEditCar = (car) => {
    setFormData({
      id: car.id,
      name: car.name,
      type: car.type,
      pricePerDay: car.pricePerDay?.toString() || "",
      imageUrl: car.imageUrl || "",
      available: car.available ?? true,
    });
    setModal("edit");
  };

  const saveCar = async () => {
    const payload = {
      name: formData.name,
      type: formData.type,
      pricePerDay: parseFloat(formData.pricePerDay) || 0,
      imageUrl: formData.imageUrl || null,
      available: formData.available,
    };
    try {
      if (modal === "add") await api.post("/admin/cars", payload);
      else await api.put(`/admin/cars/${formData.id}`, payload);
      setModal(null);
      fetchCars();
    } catch (e) {
      alert(e.response?.data?.message || "Error saving car");
    }
  };

  const deleteCar = async (id) => {
    if (!confirm("Delete this car?")) return;
    try {
      await api.delete(`/admin/cars/${id}`);
      fetchCars();
    } catch (e) {
      alert(e.response?.data?.message || "Error deleting car");
    }
  };

  const deleteUser = async (id) => {
    if (!confirm("Delete this user?")) return;
    try {
      await api.delete(`/admin/users/${id}`);
      fetchUsers();
    } catch (e) {
      alert(e.response?.data?.message || "Error deleting user");
    }
  };

  const handleCancellationAction = async (bookingId, action) => {
    if (!adminComment.trim() && action === "REJECT") {
      alert("Please provide a comment for rejection");
      return;
    }
    
    try {
      await api.put(`/admin/cancellations/${bookingId}`, {
        action: action,
        adminComment: adminComment.trim()
      });
      setCancellationModal(null);
      setAdminComment("");
      fetchCancellations();
      fetchBookings();
      alert(`Cancellation request ${action.toLowerCase()}d successfully`);
    } catch (e) {
      alert(e.response?.data?.message || "Error processing cancellation request");
    }
  };

  const openCancellationModal = (cancellation) => {
    setCancellationModal(cancellation);
    setAdminComment("");
  };

  if (loading) {
    return (
      <div className="admin-page">
        <p style={{ textAlign: "center", color: "#94a3b8" }}>Loading...</p>
      </div>
    );
  }

  return (
    <div className="admin-page">
      <div className="admin-header">
        <h1>Admin Dashboard</h1>
        <p>Welcome, {user?.name}. Manage cars, bookings, and users.</p>
      </div>

      <div className="admin-tabs">
        <button className={`admin-tab ${tab === "cars" ? "active" : ""}`} onClick={() => setTab("cars")}>
          Cars ({cars.length})
        </button>
        <button className={`admin-tab ${tab === "bookings" ? "active" : ""}`} onClick={() => setTab("bookings")}>
          Bookings ({bookings.length})
        </button>
        <button className={`admin-tab ${tab === "users" ? "active" : ""}`} onClick={() => setTab("users")}>
          Users ({users.length})
        </button>
        <button className={`admin-tab ${tab === "cancellations" ? "active" : ""}`} onClick={() => setTab("cancellations")}>
          Cancellations ({cancellations.length})
        </button>
        <button className={`admin-tab ${tab === "contacts" ? "active" : ""}`} onClick={() => setTab("contacts")}>
          Contacts ({contacts.length})
        </button>
      </div>

      <div className="admin-section">
        {tab === "cars" && (
          <>
            <button className="admin-btn admin-btn-add" onClick={openAddCar}>
              + Add Car
            </button>
            <table className="admin-table">
              <thead>
                <tr>
                  <th>Image</th>
                  <th>Name</th>
                  <th>Type</th>
                  <th>Price/Day</th>
                  <th>Available</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {cars.map((c) => (
                  <tr key={c.id}>
                    <td>
                      <img src={c.imageUrl || "https://via.placeholder.com/80x50"} alt={c.name} />
                    </td>
                    <td>{c.name}</td>
                    <td>{c.type}</td>
                    <td>₹{c.pricePerDay?.toLocaleString()}</td>
                    <td>{c.available ? "Yes" : "No"}</td>
                    <td>
                      <button className="admin-btn admin-btn-edit" onClick={() => openEditCar(c)}>
                        Edit
                      </button>
                      <button className="admin-btn admin-btn-delete" onClick={() => deleteCar(c.id)}>
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            {cars.length === 0 && <div className="admin-empty">No cars yet. Add one!</div>}
          </>
        )}

        {tab === "bookings" && (
          <>
            <h2>All Bookings</h2>
            <table className="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Car</th>
                  <th>User</th>
                  <th>Dates</th>
                  <th>Total</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {bookings.map((b) => (
                  <tr key={b.id}>
                    <td>{b.id}</td>
                    <td>{b.car?.name}</td>
                    <td>{b.userEmail || b.userId}</td>
                    <td>
                      {b.startDate} to {b.endDate}
                    </td>
                    <td>₹{b.totalPrice?.toLocaleString()}</td>
                    <td>{b.status}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            {bookings.length === 0 && <div className="admin-empty">No bookings yet.</div>}
          </>
        )}

        {tab === "users" && (
          <>
            <h2>All Users</h2>
            <table className="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {users.map((u) => (
                  <tr key={u.id}>
                    <td>{u.id}</td>
                    <td>{u.name}</td>
                    <td>{u.email}</td>
                    <td>{u.role?.roleName || "-"}</td>
                    <td>
                      {u.role?.roleName !== "ROLE_ADMIN" && (
                        <button className="admin-btn admin-btn-delete" onClick={() => deleteUser(u.id)}>
                          Delete
                        </button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            {users.length === 0 && <div className="admin-empty">No users.</div>}
          </>
        )}

        {tab === "contacts" && (
          <>
            <h2>Contact Queries</h2>
            <table className="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Subject</th>
                  <th>Message</th>
                  <th>Created</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {contacts.map((c) => (
                  <tr key={c.id}>
                    <td>{c.id}</td>
                    <td>{c.name}</td>
                    <td>{c.email}</td>
                    <td>{c.subject || "-"}</td>
                    <td style={{ maxWidth: "360px", whiteSpace: "pre-wrap" }}>{c.message}</td>
                    <td>{c.createdAt}</td>
                    <td>{c.resolved ? "Resolved" : "Open"}</td>
                    <td>
                      {!c.resolved && (
                        <button
                          className="admin-btn admin-btn-edit"
                          onClick={async () => {
                            await api.patch(`/admin/contacts/${c.id}/resolve`);
                            fetchContacts();
                          }}
                        >
                          Mark Resolved
                        </button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            {contacts.length === 0 && <div className="admin-empty">No contact queries.</div>}
          </>
        )}

        {tab === "cancellations" && (
          <>
            <h2>Cancellation Requests</h2>
            <table className="admin-table">
              <thead>
                <tr>
                  <th>Booking ID</th>
                  <th>Customer</th>
                  <th>Car</th>
                  <th>Dates</th>
                  <th>Total</th>
                  <th>Reason</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {cancellations.map((c) => (
                  <tr key={c.bookingId}>
                    <td>{c.bookingId}</td>
                    <td>{c.userName}<br/>{c.userEmail}</td>
                    <td>{c.carName}</td>
                    <td>{c.startDate} to {c.endDate}</td>
                    <td>₹{c.totalPrice?.toLocaleString()}</td>
                    <td style={{ maxWidth: "200px", whiteSpace: "pre-wrap" }}>{c.reason}</td>
                    <td>
                      <button 
                        className="admin-btn admin-btn-edit" 
                        onClick={() => openCancellationModal(c)}
                      >
                        Review
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            {cancellations.length === 0 && <div className="admin-empty">No pending cancellation requests.</div>}
          </>
        )}
      </div>

      {modal && (modal === "add" || modal === "edit") && (
        <div className="admin-modal-overlay" onClick={() => setModal(null)}>
          <div className="admin-modal" onClick={(e) => e.stopPropagation()}>
            <h3>{modal === "add" ? "Add Car" : "Edit Car"}</h3>
            <div className="form-row">
              <label>Name</label>
              <input
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                placeholder="Car name"
              />
            </div>
            <div className="form-row">
              <label>Type</label>
              <input
                value={formData.type}
                onChange={(e) => setFormData({ ...formData, type: e.target.value })}
                placeholder="e.g. SUV, Sedan"
              />
            </div>
            <div className="form-row">
              <label>Price per day (₹)</label>
              <input
                type="number"
                value={formData.pricePerDay}
                onChange={(e) => setFormData({ ...formData, pricePerDay: e.target.value })}
                placeholder="0"
              />
            </div>
            <div className="form-row">
              <label>Image URL</label>
              <input
                value={formData.imageUrl}
                onChange={(e) => setFormData({ ...formData, imageUrl: e.target.value })}
                placeholder="https://..."
              />
            </div>
            <div className="form-row">
              <label>
                <input
                  type="checkbox"
                  checked={formData.available}
                  onChange={(e) => setFormData({ ...formData, available: e.target.checked })}
                />{" "}
                Available
              </label>
            </div>
            <div className="admin-modal-actions">
              <button className="btn-cancel" onClick={() => setModal(null)}>
                Cancel
              </button>
              <button className="btn-save" onClick={saveCar}>
                Save
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Cancellation Review Modal */}
      {cancellationModal && (
        <div className="admin-modal-overlay" onClick={() => setCancellationModal(null)}>
          <div className="admin-modal" onClick={(e) => e.stopPropagation()}>
            <h3>Review Cancellation Request</h3>
            <div className="cancellation-details">
              <p><strong>Booking ID:</strong> {cancellationModal.bookingId}</p>
              <p><strong>Customer:</strong> {cancellationModal.userName} ({cancellationModal.userEmail})</p>
              <p><strong>Car:</strong> {cancellationModal.carName}</p>
              <p><strong>Dates:</strong> {cancellationModal.startDate} to {cancellationModal.endDate}</p>
              <p><strong>Total Price:</strong> ₹{cancellationModal.totalPrice?.toLocaleString()}</p>
              <p><strong>Reason:</strong> {cancellationModal.reason}</p>
            </div>
            <div className="form-row">
              <label>Admin Comment (optional)</label>
              <textarea
                value={adminComment}
                onChange={(e) => setAdminComment(e.target.value)}
                placeholder="Add a comment for the customer..."
                rows="3"
              />
            </div>
            <div className="admin-modal-actions">
              <button 
                className="btn-cancel" 
                onClick={() => setCancellationModal(null)}
              >
                Cancel
              </button>
              <button 
                className="btn-reject" 
                onClick={() => handleCancellationAction(cancellationModal.bookingId, "REJECT")}
              >
                Reject Request
              </button>
              <button 
                className="btn-approve" 
                onClick={() => handleCancellationAction(cancellationModal.bookingId, "APPROVE")}
              >
                Approve Request
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default AdminDashboard;
