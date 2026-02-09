import React, { useEffect, useState } from "react";
import { getUsername, logout, getUserId } from "../utils/auth";
import api from "../api/api.js"
import { useNavigate } from "react-router-dom";


function Feed() {
    const [username, setUsername] = useState("");
    const navigate = useNavigate();
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const [posts, setPosts] = useState([]);
    const userId = getUserId();

    useEffect(() => {
        const user = getUsername();
        if (!user) {
            // If no user, redirect to login
            navigate("/login");
        } else {
            setUsername(user);
            fetchPosts();
        }
    }, [navigate]);

    const handleToggleLike = async (post) => {
        try {
            if (post.likedByCurrentUser) {
                await api.post(`/posts/${post.id}/unlike?userId=${userId}`);
            } else {
                await api.post(`/posts/${post.id}/like?userId=${userId}`);
            }

            fetchPosts();
        } catch (err) {
            console.log("Like toggle failed", err);
        }
    }

    const fetchPosts = async () => {
        try {
            const userId = getUserId();

            const res = await api.get(`/posts?userId=${userId}`);
            console.log("CURRENT USER ID:", userId);
            setPosts(res.data);
            console.log("FETCH POSTS RESPONSE:", res.data);
        } catch (error) {
            console.log(error);
            setError("There was an error fetching posts.");
        } finally {
            setLoading(false);
        }
    }

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    if (loading) return <p style={{ padding: "2rem" }}>Loading feed...</p>;
    if (error) return <p style={{ padding: "2rem" }}>{error}</p>;

    return (
        <div style={{ maxWidth: "600px", margin: "0 auto", padding: "2rem" }}>
            <div style={{ display: "flex", justifyContent: "space-between" }}>
                <h2>Feed</h2>
                <button onClick={handleLogout}>Logout</button>
            </div>

            {posts.length === 0 && <p>No posts yet.</p>}

            {posts.map((post) => (
                <div
                    key={post.id}
                    style={{
                        border: "1px solid #ccc",
                        borderRadius: "6px",
                        padding: "1rem",
                        marginBottom: "1rem",
                    }}
                >
                    <strong>@{post.username}</strong>
                    <p>{post.content}</p>

                    {post.imageUrl && (
                        <img
                            src={post.imageUrl}
                            alt="post"
                            style={{ maxWidth: "100%", marginTop: "0.5rem" }}
                        />
                    )}

                    <small style={{ color: "#666" }}>
                        {new Date(post.createdAt).toLocaleString()}
                    </small>

                    <div style={{ marginTop: "0.5rem" }}>
                        ❤️ {post.likeCount ?? 0} likes
                    </div>

                    <button onClick={() => handleToggleLike(post)}>
                        {post.likedByCurrentUser ? "Unlike" : "Like"}
                    </button>
                </div>
            ))}
        </div>
    );
}

export default Feed;
