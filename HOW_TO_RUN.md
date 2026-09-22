# UrbanDrive - How to Run (Step by Step)

## Prerequisites

Install these before starting:

| Tool | Version | Download |
|------|---------|----------|
| **Node.js** | 18+ | https://nodejs.org |
| **Java** | 17+ | https://adoptium.net |
| **MySQL** | 8+ | https://dev.mysql.com/downloads |
| **Maven** | (optional) | Comes with project (`mvnw`) |

---

## Step 1: Create MySQL Database

1. Open **MySQL Workbench** or **MySQL Command Line**
2. Run:

```sql
CREATE DATABASE carrental;
```

3. Note your MySQL **username** and **password**

---

## Step 2: Configure Backend Database

1. Open: `backend/carrental/src/main/resources/application.properties`
2. Update these lines with your MySQL credentials:

```properties
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

Example (if your MySQL user is `root` and password is `1234`):

```properties
spring.datasource.username=root
spring.datasource.password=1234
```

---

## Step 2.1: Configure Email (SMTP)

UrbanDrive sends:
- **Welcome email** after registration
- **Admin notification email** when someone submits Contact Us

In `backend/carrental/src/main/resources/application.properties`, set:

```properties
spring.mail.username=YOUR_EMAIL@gmail.com
spring.mail.password=YOUR_APP_PASSWORD
app.admin.email=YOUR_EMAIL@gmail.com
```

For Gmail you must generate an **App Password** (Google Account → Security → App passwords).

---

## Step 3: Start Backend (Spring Boot)

**On Windows (PowerShell or CMD):**

```powershell
cd backend\carrental
.\mvnw.cmd spring-boot:run
```

**On Mac/Linux:**

```bash
cd backend/carrental
./mvnw spring-boot:run
```

**Wait until you see:** `Started CarrentalApplication` in the console.

Backend runs at: **http://localhost:8080**

> First run may take 2–3 minutes (Maven downloads dependencies).  
> Database tables and sample cars are created automatically.

---

## Step 4: Install Frontend Dependencies

Open a **new terminal** (keep backend running):

```powershell
cd frontend
npm install
```

---

## Step 5: Start Frontend (React + Vite)

```powershell
npm run dev
```

Frontend runs at: **http://localhost:5173**

---

## Step 6: Use the App

1. Open browser: **http://localhost:5173**
2. **Register** a new account (Register → Create account)
3. **Login** with your email and password
4. **Browse Cars** → Click "Rent Now" on any car
5. **Select dates** and **Confirm Booking**
6. View **My Bookings** from the Account dropdown

### Admin Login

- **Email:** admin@urbandrive.com  
- **Password:** admin123  

After login as admin, use **Account → Admin Panel** to manage cars, view all bookings, and manage users.

See **[ADMIN_ROUTES.md](ADMIN_ROUTES.md)** for full admin API routes.

---

## Quick Reference

| Service | URL | Port |
|---------|-----|------|
| Frontend | http://localhost:5173 | 5173 |
| Backend API | http://localhost:8080 | 8080 |

---

## Troubleshooting

### Backend won't start
- **MySQL not running?** Start MySQL service
- **Wrong credentials?** Check `application.properties`
- **Port 8080 in use?** Stop other apps using 8080, or change port in `application.properties`:
  ```properties
  server.port=8081
  ```
  Then update `frontend/src/api.jsx` baseURL to `http://localhost:8081`

### Frontend can't reach backend
- Ensure backend is running first
- Check CORS: backend allows `http://localhost:5173`

### "Invalid email or password"
- Register first, then login
- Passwords are hashed; use the exact password you registered with

### Cars not loading
- Backend must be running
- Check browser console (F12) for errors
- First run seeds 10 cars; if DB was already created, restart backend

---

## Project Structure

```
UrbanDrive/
├── frontend/          # React app (Vite)
│   └── src/
│       ├── pages/     # Home, Cars, Booking, Login, etc.
│       ├── components/
│       └── api.jsx    # API config + JWT
├── backend/
│   └── carrental/    # Spring Boot app
│       └── src/main/java/.../ecotrack/
│           ├── controller/
│           ├── service/
│           ├── entities/
│           └── security/
└── HOW_TO_RUN.md     # This file
```
