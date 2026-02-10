import { useState } from "react";
import api from "../api/api";
import { setToken, setUser } from "../utils/auth.js"
import { useNavigate } from "react-router-dom";

function Login({ onLogin }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post("/users/login", { email, password });
            console.log("LOGIN RESPONSE:", response.data);

            setToken(response.data.token);

            setUser({
                userId: response.data.userId,
                username: response.data.username,
            });

            if(onLogin) onLogin();

            navigate("/feed");
        } catch (err) {
            console.error(err);
            setMessage("Login failed. Check your credentials.");
        }
    };

    return (
        <div style={{ maxWidth: "400px", margin: "0 auto", padding: "2rem" }}>
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit">Login</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
}

export default Login;
