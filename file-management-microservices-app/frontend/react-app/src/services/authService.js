import axios from 'axios';

// Define the base URL for the auth API.
// This should ideally come from an environment variable.
const API_URL = 'http://localhost:8080/api/auth/'; // Assuming auth-service runs on port 8080

class AuthService {
  login(email, password) {
    return axios
      .post(API_URL + 'login', {
        email,
        password,
      })
      .then(response => {
        if (response.data.token) {
          // You might want to store user info or token here if not handled by calling component/context
          // For now, just return the full response data
        }
        return response.data;
      });
  }

  register(email, password) {
    // Assuming your backend /register endpoint expects email and password
    return axios.post(API_URL + 'register', {
      email,
      password,
    });
  }

  // Placeholder for logout if it involves an API call,
  // otherwise, logout is typically handled client-side by removing the token.
  // logout() {
  //   // For example, if backend has a /logout endpoint or needs to invalidate session
  //   // return axios.post(API_URL + 'logout');
  // }

  // Helper to get current user info from token (if stored locally)
  // This is more of a utility if you store user details from token somewhere
  // getCurrentUser() {
  //   // Example: return JSON.parse(localStorage.getItem('user'));
  // }
}

export default new AuthService();
