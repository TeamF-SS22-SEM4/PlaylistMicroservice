package at.fhv.ec.domain.model.song;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Song {
    @EmbeddedId
    private SongId songId;
    private String albumName;
    private String title;
    private String duration;

    public static Song create(SongId songId, String albumName, String title, String duration) {
        return new Song(songId, albumName, title, duration);
    }

    protected Song(){}

    public Song(SongId songId, String albumName, String title, String duration) {
        this.songId = songId;
        this.albumName = albumName;
        this.title = title;
        this.duration = duration;
    }

    public SongId getSongId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(albumName, song.albumName) && Objects.equals(title, song.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumName, title);
    }
}
