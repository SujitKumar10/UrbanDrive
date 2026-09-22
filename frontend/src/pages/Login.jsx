import { Link, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import "../styles/auth.css";
import { api } from "../api";
import { LoginContext } from "../context/LoginContext";
import { useContext } from "react";

function Login() {
const {login}=useContext(LoginContext );

  
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset
  } = useForm();

  const onSubmit = async (data) => {

    console.log(data);
    try {
      const response=await api.post("/auth/login",data);
      console.log(response);
      login(response.data.userDto,response.data.token)

      console.log();
      
      const role = response.data.userDto?.role?.roleName;
            console.log(role);

      if (role === "ROLE_ADMIN") {
        navigate("/admin/dashboard");
      } else {
        navigate("/");
      }
      
    } catch (error) {
      const msg = error.response?.data || "Invalid email or password";
      alert(typeof msg === "string" ? msg : "Invalid email or password");
    }
  };

  return (
    <div className="auth-wrapper">

      <div className="auth-card">

        <div className="auth-header">
          <h2>Sign In</h2>
          <p>Access your UrbanDrive account</p>
        </div>

        <form onSubmit={handleSubmit(onSubmit)} autoComplete="off">

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
                required: "Password is required"
              })}
            />
            {errors.password && <p className="error-msg">{errors.password.message}</p>}
          </div>

          <button type="submit" className="auth-btn">
            Login
          </button>

          <p className="auth-switch">
            Don't have an account? <Link to="/register">Register</Link>
          </p>
        </form>

      </div>

    </div>
  );
}

export default Login;