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
    private String username;
    @ElementCollection
    private List<SongId> songs;

    public static Playlist create(PlaylistId playlistId, String username) {
        return new Playlist(playlistId, username);
    }

    protected Playlist() {}

    private Playlist(PlaylistId playlistId, String username) {
        this.playlistId = playlistId;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public List<SongId> getSongs() {
        return songs;
    }
}
