# Admin Panel Routes

## Frontend (React)

| Route | Component | Access |
|-------|-----------|--------|
| `/admin/dashboard` | AdminDashboard | Admin only (redirects to `/` if not admin, `/login` if not authenticated) |

**How to access:** Login as admin → Account dropdown → **Admin Panel**

---

## Backend API (Spring Boot)

Base URL: `http://localhost:8080`

All admin endpoints require:
- **Authentication:** Valid JWT token in `Authorization: Bearer <token>` header
- **Role:** `ROLE_ADMIN`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/cars` | Get all cars (including unavailable) |
| POST | `/admin/cars` | Create new car |
| PUT | `/admin/cars/{id}` | Update car |
| DELETE | `/admin/cars/{id}` | Delete car |
| GET | `/admin/bookings` | Get all bookings |
| GET | `/admin/users` | Get all users |
| DELETE | `/admin/users/{id}` | Delete user (cannot delete admin) |

---

## Example API Calls

```bash
# Get all cars (admin)
curl -H "Authorization: Bearer YOUR_JWT_TOKEN" http://localhost:8080/admin/cars

# Create car
curl -X POST -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"Tesla Model 3","type":"Sedan","pricePerDay":8000,"imageUrl":"https://...","available":true}' \
  http://localhost:8080/admin/cars

# Get all bookings
curl -H "Authorization: Bearer YOUR_JWT_TOKEN" http://localhost:8080/admin/bookings
```

---

## Admin Login

- **Email:** admin@urbandrive.com  
- **Password:** admin123  
