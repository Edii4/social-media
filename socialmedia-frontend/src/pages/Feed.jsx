import React, { useEffect, useState } from "react";
import { getUsername, logout } from "../utils/auth";
import { useNavigate } from "react-router-dom";

function Feed() {
    const [username, setUsername] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        const user = getUsername();
        if (!user) {
            // If no user, redirect to login
            navigate("/login");
        } else {
            setUsername(user);
        }
    }, [navigate]);

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    return (
        <div style={{ padding: "2rem" }}>
            <h1>Welcome, {username}!</h1>
            <button onClick={handleLogout}>Logout</button>

            <div style={{ marginTop: "2rem" }}>
                {/* This is where you can render posts later */}
                <p>Feed posts will appear here...</p>
            </div>
        </div>
    );
}

export default Feed;
