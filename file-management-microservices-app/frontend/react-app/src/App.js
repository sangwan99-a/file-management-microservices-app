import React from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import ProtectedRoute from './components/ProtectedRoute'; // Import ProtectedRoute
import './App.css'; // Default CRA CSS

function App() {
  return (
    <div>
      <nav>
        <ul>
          <li><Link to="/">Home</Link></li>
          <li><Link to="/login">Login</Link></li>
          <li><Link to="/register">Register</Link></li>
          <li><Link to="/dashboard">Dashboard</Link></li>
        </ul>
      </nav>

      <hr />

      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        {/* Protected Route for Dashboard */}
        <Route path="/dashboard" element={<ProtectedRoute />}>
          <Route index element={<DashboardPage />} />
          {/* Or if DashboardPage is simple and has no children: <Route path="" element={<DashboardPage />} /> */}
          {/* Add other nested protected routes here if any */}
        </Route>

        {/* Example of protecting a route directly without children (alternative)
        <Route
          path="/dashboard-alternative"
          element={
            <ProtectedRoute>
              <DashboardPage />
            </ProtectedRoute>
          }
        />
        For this alternative, ProtectedRoute would need to render {children} instead of <Outlet/>
        Let's use the <Outlet/> approach as it's more common for layout routes.
        So the /dashboard route should be a parent route.
        */}
      </Routes>
    </div>
  );
}

export default App;
