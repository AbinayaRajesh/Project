package com.codepath.myapplication;


import com.google.android.youtube.player.YouTubePlayerView;

public class VideoItem {

    private String title;
    private String description;
    private String thumbnailURL;
    private String id;
    private YouTubePlayerView playerView;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public YouTubePlayerView getPlayerView() {
        return playerView;
    }

    public void setPlayerView(YouTubePlayerView playerView) {
        this.playerView = playerView;
    }
}
