import React, { useEffect, useState } from "react";
import { getUsername, logout, getUserId } from "../utils/auth";
import api from "../api/api.js"
import { useNavigate } from "react-router-dom";
import { formatDistanceToNow } from "date-fns";
import Header from "../components/Header.jsx";


function Explore() {
    const [username, setUsername] = useState("");
    const navigate = useNavigate();
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const [posts, setPosts] = useState([]);
    const userId = getUserId();
    const [commentText, setCommentText] = useState({});

    useEffect(() => {
        const user = getUsername();
        setUsername(user);
        fetchPosts();

    });

    const handleAddComment = async (postId) => {
        try {
            const userId = getUserId();
            const content = commentText[postId];

            await api.post(`/posts/${postId}/comment?userId=${userId}&content=${content}`);

            setCommentText({ ...commentText, [postId]: "" });

            fetchPosts();
        } catch (err) {
            console.log("Comment failed" ,err);
        }
    };

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
    };

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
    };

    if (loading) return <p style={{ padding: "2rem" }}>Loading feed...</p>;
    if (error) return <p style={{ padding: "2rem" }}>{error}</p>;

    return (
        <>
            <div style={{ maxWidth: "600px", margin: "0 auto", padding: "2rem" }}>
                <div style={{ display: "flex", justifyContent: "space-between" }}>
                    <h2>Explore</h2>
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

                        <small title={new Date(post.createdAt).toLocaleString()} style={{ color: "#666" }} >
                            {formatDistanceToNow(new Date(post.createdAt), { addSuffix: true })}
                        </small>

                        <div style={{ marginTop: "0.5rem" }}>
                            ❤️ {post.likeCount ?? 0} likes
                        </div>

                        <button onClick={() => handleToggleLike(post)}>
                            {post.likedByCurrentUser ? "Unlike" : "Like"}
                        </button>

                        {post.comments?.map((comment) => (
                            <div key={comment.id} style={{ display: "flex", gap: "8px" }}>
                                <img src={comment.profilePicUrl} width="30" />
                                <div>
                                    <strong>{comment.username}</strong>: {comment.content}
                                    <div style={{ fontSize: "0.8rem", color: "#888" }}>
                                        {formatDistanceToNow(new Date(comment.createdAt), { addSuffix: true })}
                                    </div>
                                </div>
                            </div>
                        ))}

                        <input
                            type="text"
                            placeholder="Write a comment..."
                            value={commentText[post.id] || ""}
                            onChange={(e) =>
                                setCommentText({ ...commentText, [post.id]: e.target.value })
                            }
                        />

                        <button onClick={() => handleAddComment(post.id)}>
                            Comment
                        </button>

                    </div>
                ))}
            </div>
        </>
    );
}

export default Explore;
