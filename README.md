# UrbanDrive - Car Rental Project

A full-stack car rental application with React frontend and Spring Boot backend.

## Quick Start

**See [HOW_TO_RUN.md](HOW_TO_RUN.md) for detailed step-by-step instructions.**

```powershell
# 1. Create MySQL database: CREATE DATABASE carrental;
# 2. Update backend/carrental/src/main/resources/application.properties (MySQL username/password)

# 3. Start backend
cd backend\carrental
.\mvnw.cmd spring-boot:run

# 4. In a new terminal - start frontend
cd frontend
npm install
npm run dev

# 5. Open http://localhost:5173
```

## Tech Stack

- **Frontend**: React, Vite, React Router, Axios, React Hook Form
- **Backend**: Spring Boot 4, Spring Security, JWT, MySQL, JPA

## Features

- **User Auth**: Register, Login (JWT)
- **Cars**: Browse & filter by type (SUV, Sedan, Luxury, Sports)
- **Booking**: Select dates, confirm rental
- **My Bookings**: View your booking history
- **Admin**: Basic admin dashboard

## API Endpoints

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /auth/login | No | Login |
| POST | /users/register | No | Register |
| GET | /cars | No | List cars |
| GET | /cars?type=SUV | No | Filter by type |
| GET | /cars/{id} | No | Get car by ID |
| POST | /bookings | Yes | Create booking |
| GET | /bookings/my | Yes | My bookings |

## Razorpay (Payments)

Booking uses Razorpay for payments. Configure in `application.properties`:

```
Razorpay.key=rzp_test_xxxxx
Razorpay.secret=your_secret
```

Get keys from [Razorpay Dashboard](https://dashboard.razorpay.com/) (use test keys for development).

## Default Data

On first run, the backend seeds:
- Roles: ROLE_USER, ROLE_ADMIN
- 10 sample cars (BMW, Audi, Mercedes, Ferrari, etc.)
