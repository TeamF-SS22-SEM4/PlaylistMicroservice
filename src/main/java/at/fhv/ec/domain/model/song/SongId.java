package at.fhv.ec.domain.model.song;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SongId implements Serializable {
    @Column
    @Type(type = "uuid-char")
    private UUID songId;

    public SongId(UUID songId) {
        this.songId = songId;
    }

    protected SongId() {
    }

    public UUID getUUID() {
        return songId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongId songId1 = (SongId) o;
        return Objects.equals(songId, songId1.songId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songId);
    }
}
