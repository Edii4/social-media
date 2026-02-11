import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/api";
import { getUserId } from "../utils/auth";

function Profile() {
    const { userId } = useParams();
    const currentUserId = getUserId();

    const [profile, setProfile] = useState(null);
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        fetchProfile();
        fetchPosts();
    }, [userId]);

    const fetchProfile = async () => {
        try {
            const res = await api.get(
                `/users/${userId}?currentUserId=${currentUserId}`
            );


            console.log("PROFILE RESPONSE:", res.data);
            setProfile(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const fetchPosts = async () => {
        try {
            const res = await api.get(`/posts/users/${userId}`);
            setPosts(res.data);
        } catch (err) {
            console.error(err);
        }
    };

    const handleFollowToggle = async () => {
        try {
            if (profile.following) {
                await api.post(`/users/${currentUserId}/unfollow/${userId}?currentUserId=${currentUserId}`);
            } else {
                await api.post(`/users/${currentUserId}/follow/${userId}?currentUserId=${currentUserId}`);
            }

            fetchProfile();
        } catch (err) {
            console.error(err);
        }
    };

    if (!profile) return <div>Loading...</div>;

    return (
        <div>
            <h2>{profile.username}</h2>
            <img
                src={profile.profilePicUrl}
                alt="profilePic"
                width="100"
            />

            <p>Followers: {profile.followersCount}</p>
            <p>Following: {profile.followingCount}</p>

            {currentUserId !== userId && (
                <button onClick={handleFollowToggle}>
                    {profile.following ? "Unfollow" : "Follow"}
                </button>
            )}

            <hr />

            <h3>Posts</h3>

            {posts.map(post => (
                <div key={post.id}>
                    <p>{post.content}</p>
                </div>
            ))}
        </div>
    );
}

export default Profile;
