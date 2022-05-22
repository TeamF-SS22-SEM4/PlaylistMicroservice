package at.fhv.ec.application.dto;

import java.util.UUID;

public class SongDTO {
    private UUID songId;
    private String albumName;
    private String title;
    private String duration;

    private SongDTO(){}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final SongDTO instance;

        public Builder() {
            this.instance = new SongDTO();
        }

        public Builder withSongId(UUID songId) {
            this.instance.songId = songId;
            return this;
        }

        public Builder withAlbumName(String albumName) {
            this.instance.albumName = albumName;
            return this;
        }

        public Builder withTitle(String title) {
            this.instance.title = title;
            return this;
        }

        public Builder withDuration(String duration) {
            this.instance.duration = duration;
            return this;
        }

        public SongDTO build() {
            return this.instance;
        }
    }

    public UUID getSongId() {
        return songId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }
}
