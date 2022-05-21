package at.fhv.ec.infrastructure;

import at.fhv.ec.domain.model.playlist.Playlist;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class HibernatePlaylistRepository implements PanacheRepository<Playlist> {

    public Optional<Playlist> findByUserId(String userId) {
        return Optional.ofNullable(find("userId", userId).firstResult());
    }
}
