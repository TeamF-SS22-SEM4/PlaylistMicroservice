package at.fhv.ec.infrastructure;

import at.fhv.ec.domain.model.song.Song;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class HibernateSongRepository implements PanacheRepository<Song> {
    public Optional<Song> findByTitleAndAlbum(String title, String album) {
        return Optional.of(find("title and albumName", title, album).firstResult());
    }
}
