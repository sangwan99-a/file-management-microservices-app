import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

function ProtectedRoute() {
    const { isAuthenticated } = useAuth();

    if (!isAuthenticated) {
        // If not authenticated, redirect to the /login page
        // You can also pass the current location to redirect back after login
        // e.g., return <Navigate to="/login" state={{ from: location }} replace />;
        return <Navigate to="/login" replace />;
    }

    // If authenticated, render the child routes/component
    return <Outlet />;
    // Using <Outlet /> is suitable if ProtectedRoute is used as a layout route for nested routes.
    // If ProtectedRoute wraps a single component directly like <ProtectedRoute><DashboardPage /></ProtectedRoute>,
    // then you might pass `children` as a prop and render `{children}`.
    // For typical route protection as an element in <Route element={<ProtectedRoute />} />,
    // <Outlet /> is the standard for React Router v6 to render nested <Route> elements.
    // If you intend to use it as <Route path="/dashboard" element={<ProtectedRoute><DashboardPage/></ProtectedRoute>} />,
    // then it should be:
    // function ProtectedRoute({ children }) { ... return children; }
    // Let's stick to the <Outlet /> approach for now, assuming it's used as a parent route element.
}

export default ProtectedRoute;
