import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import CarCard from "../components/CarCard";
import "../styles/cars.css";
import { api } from "../api";

function Cars() {
  const navigate = useNavigate();
  const [allCars, setAllCars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState("All");

  useEffect(() => {
    const fetchCars = async () => {
      try {
        const type = filter === "All" ? null : filter;
        const { data } = await api.get("/cars", type ? { params: { type } } : {});
        setAllCars(data.map((c) => ({ ...c, price: c.pricePerDay, image: c.imageUrl })));
      } catch (err) {
        console.error(err);
        setAllCars([]);
      } finally {
        setLoading(false);
      }
    };
    fetchCars();
  }, [filter]);

  const filteredCars = allCars;

  // 🔥 RENT NOW LOGIC
  const handleRentNow = (car) => {
    const user = localStorage.getItem("user");
    if (!user) {
      sessionStorage.setItem("pendingCar", JSON.stringify({ ...car, price: car.price ?? car.pricePerDay, image: car.image ?? car.imageUrl }));
      navigate("/login");
    } else {
      sessionStorage.setItem("bookingCar", JSON.stringify({ ...car, price: car.price ?? car.pricePerDay, image: car.image ?? car.imageUrl }));
      navigate("/booking", { state: { car } });
    }
  };

  return (
    <div className="cars-page">
      <div className="cars-hero">
        <h1>Explore Our Premium Cars</h1>
        <p>Luxury, SUVs, Sports, and compact cars – choose your perfect ride.</p>
      </div>

      <div className="filter-bar">
        {["All", "SUV", "Sedan", "Luxury", "Sports", "Compact"].map((f) => (
          <button
            key={f}
            className={`filter-btn ${filter === f ? "active" : ""}`}
            onClick={() => setFilter(f)}
          >
            {f}
          </button>
        ))}
      </div>

      <div className="cars-grid">
        {loading ? (
          <p style={{ gridColumn: "1/-1", textAlign: "center", color: "#fff" }}>Loading cars...</p>
        ) : filteredCars.length === 0 ? (
          <p style={{ gridColumn: "1/-1", textAlign: "center", color: "#fff" }}>No cars found.</p>
        ) : (
          filteredCars.map((car) => (
            <CarCard key={car.id} car={car} onRent={handleRentNow} />
          ))
        )}
      </div>
    </div>
  );
}

export default Cars;