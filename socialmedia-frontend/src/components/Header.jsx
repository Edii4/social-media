import { Link, useNavigate } from "react-router-dom";
import { getUsername, logout, getUserId } from "../utils/auth";

function Header() {
    const navigate = useNavigate();
    const username = getUsername();

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    return (
        <div style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            padding: "1rem 2rem",
            borderBottom: "1px solid #ddd",
            backgroundColor: "#f9f9f9"
        }}>
            <div>
                <strong>SocialMedia</strong>
            </div>

            <div style={{ display: "flex", gap: "1rem" }}>
                <Link to="/feed">Feed</Link>
                <Link to="/explore">Explore</Link>
                <Link to={`/profile/${getUserId()}`}>Profile</Link>
            </div>

            <div style={{ display: "flex", gap: "1rem", alignItems: "center" }}>
                <span>Hi, {username}</span>
                <button onClick={handleLogout}>Logout</button>
            </div>
        </div>
    );
}

export default Header;