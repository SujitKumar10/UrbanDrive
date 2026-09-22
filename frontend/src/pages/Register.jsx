import { Link, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import "../styles/auth.css";
import { api } from "../api";

function Register() {
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
    setError
  } = useForm();

  const password = watch("password");

  const onSubmit = async (data) => {
    try {
      await api.post("/users/register", {
        name: data.name,
        email: data.email,
        password: data.password,
      });
      alert("Account Created Successfully ✔");
      setTimeout(() => navigate("/login"), 1500);
    } catch (error) {
      const msg = error.response?.data?.message || error.response?.data?.error || "Registration failed. Try again.";
      setError("root", { message: msg });
      alert(msg);
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card">

        <div className="auth-header">
          <h2>Create Account</h2>
          <p>Join UrbanDrive today</p>
        </div>

        <form onSubmit={handleSubmit(onSubmit)}>

          <div className="input-group">
            <label>Full Name</label>
            <input
              type="text"
              {...register("name", {
                required: "Name is required"
              })}
            />
            {errors.name && <p className="error-msg">{errors.name.message}</p>}
          </div>

          <div className="input-group">
            <label>Email Address</label>
            <input
              type="email"
              {...register("email", {
                required: "Email is required"
              })}
            />
            {errors.email && <p className="error-msg">{errors.email.message}</p>}
          </div>

          <div className="input-group">
            <label>Password</label>
            <input
              type="password"
              {...register("password", {
                required: "Password is required",
                minLength: {
                  value: 6,
                  message: "Password must be at least 6 characters"
                }
              })}
            />
            {errors.password && <p className="error-msg">{errors.password.message}</p>}
          </div>

          <div className="input-group">
            <label>Confirm Password</label>
            <input
              type="password"
              {...register("confirmPassword", {
                required: "Confirm your password",
                validate: (value) =>
                  value === password || "Passwords do not match"
              })}
            />
            {errors.confirmPassword && (
              <p className="error-msg">{errors.confirmPassword.message}</p>
            )}
          </div>

          <button type="submit" className="auth-btn">
            Register
          </button>

          <p className="auth-switch">
            Already have an account? <Link to="/login">Login</Link>
          </p>
        </form>

      </div>
    </div>
  );
}

export default Register;