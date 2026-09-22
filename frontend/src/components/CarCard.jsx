import "../styles/cars.css";

function CarCard({ car, onRent }) {
  return (
    <div className="car-card">

      <div className="car-image">
        <img src={car.image} alt={car.name} />
        <span className="car-badge">{car.type}</span>
      </div>

      <div className="car-info">
        <h3>{car.name}</h3>
        <p className="car-price">₹{car.price} / day</p>

        <div className="car-actions">
          <button 
            className="rent-btn"
            onClick={() => onRent(car)}
          >
            Rent Now
          </button>
        </div>
      </div>

    </div>
  );
}

export default CarCard;