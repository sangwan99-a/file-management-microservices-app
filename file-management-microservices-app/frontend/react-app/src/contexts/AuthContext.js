import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export function useAuth() {
    return useContext(AuthContext);
}

export function AuthProvider({ children }) {
    const [currentUser, setCurrentUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem('token')); // Initialize token from localStorage
    const [isAuthenticated, setIsAuthenticated] = useState(!!localStorage.getItem('token')); // Check if token exists

    useEffect(() => {
        // This effect syncs the `isAuthenticated` state with the `token` state.
        // It also tries to load user from localStorage if a token exists but currentUser is null.
        if (token) {
            localStorage.setItem('token', token); // Ensure token is in localStorage
            setIsAuthenticated(true);
            // Optionally, try to load user from localStorage if not already set
            if (!currentUser) {
                const storedUser = localStorage.getItem('user');
                if (storedUser) {
                    try {
                        setCurrentUser(JSON.parse(storedUser));
                    } catch (e) {
                        console.error("Error parsing stored user:", e);
                        // Clear corrupted user data
                        localStorage.removeItem('user');
                    }
                }
            }
        } else {
            localStorage.removeItem('token');
            localStorage.removeItem('user'); // Also remove user info
            setIsAuthenticated(false);
            setCurrentUser(null);
        }
    }, [token, currentUser]); // Added currentUser to dependencies

    const login = (userData) => { // userData is expected to be { id, email, token } from JwtResponse
        setToken(userData.token);
        setCurrentUser({ id: userData.id, email: userData.email });
        localStorage.setItem('user', JSON.stringify({ id: userData.id, email: userData.email }));
        // `token` state change will trigger useEffect to set localStorage for token and isAuthenticated
    };

    const logout = () => {
        setToken(null); // This will trigger useEffect to clear localStorage and update isAuthenticated
    };

    const value = {
        currentUser,
        token,
        isAuthenticated,
        login,
        logout,
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
