package at.fhv.ec.domain.model.playlist;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class PlaylistId implements Serializable {
    @Column
    @Type(type = "uuid-char")
    private UUID playlistId;

    public PlaylistId(UUID playlistId) {
        this.playlistId = playlistId;
    }

    protected PlaylistId() {
    }

    public UUID getUUID() {
        return playlistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistId that = (PlaylistId) o;
        return Objects.equals(playlistId, that.playlistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId);
    }
}
