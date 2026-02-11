package dev.edi.socialmedia;

public class UserProfileResponse {

    private String id;
    private String username;
    private String profilePicUrl;
    private int followersCount;
    private int followingCount;
    private boolean isFollowing;

    public UserProfileResponse(String id,
                               String username,
                               String profilePicUrl,
                               int followersCount,
                               int followingCount,
                               boolean isFollowing) {
        this.id = id;
        this.username = username;
        this.profilePicUrl = profilePicUrl;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.isFollowing = isFollowing;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public boolean isFollowing() {
        return isFollowing;
    }
}