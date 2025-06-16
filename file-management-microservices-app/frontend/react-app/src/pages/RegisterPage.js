import React, { useState } from 'react';
import AuthService from '../services/authService'; // Import the AuthService
import { useNavigate } from 'react-router-dom'; // To redirect after successful registration

function RegisterPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    const handleRegister = (e) => {
        e.preventDefault();
        setMessage('');
        setLoading(true);

        AuthService.register(email, password)
            .then(
                (response) => {
                    // Assuming backend returns a specific success message in response.data.message
                    // Or if it just returns 200 OK, you can set a static message.
                    setMessage(response.data.message || "Registration successful! You can now login.");
                    setLoading(false);
                    // Optionally redirect to login page after a delay or directly
                    // navigate('/login');
                },
                (error) => {
                    const resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();

                    setMessage(resMessage);
                    setLoading(false);
                }
            );
    };

    return (
        <div>
            <h1>Register</h1>
            <form onSubmit={handleRegister}>
                <div>
                    <label htmlFor="email">Email</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        name="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <button type="submit" disabled={loading}>
                        {loading && (
                            <span className="spinner-border spinner-border-sm"></span>
                        )}
                        <span>Register</span>
                    </button>
                </div>
                {message && (
                    <div className="form-group">
                        <div className="alert alert-danger" role="alert"> {/* Or success based on outcome */}
                            {message}
                        </div>
                    </div>
                )}
            </form>
        </div>
    );
}

export default RegisterPage;
