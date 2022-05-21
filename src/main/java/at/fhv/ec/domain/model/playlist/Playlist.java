package at.fhv.ec.domain.model.playlist;

import at.fhv.ec.domain.model.song.SongId;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Playlist {
    @EmbeddedId
    private PlaylistId playlistId;
    private String userId;
    @ElementCollection
    private List<SongId> songs;

    public static Playlist create(PlaylistId playlistId, String userId) {
        return new Playlist(playlistId, userId);
    }

    protected Playlist() {}

    private Playlist(PlaylistId playlistId, String userId) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.songs = new ArrayList<>();
    }

    public void addSongToPlaylist(SongId songId) {
        if(songs.contains(songId)) {
            throw new UnsupportedOperationException();
        }

        songs.add(songId);
    }

    public PlaylistId getPlaylistId() {
        return playlistId;
    }

    public String getUserId() {
        return userId;
    }

    public List<SongId> getSongs() {
        return songs;
    }
}
