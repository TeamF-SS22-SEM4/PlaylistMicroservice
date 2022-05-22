package at.fhv.ec.unit.infrastructure;

import at.fhv.ec.domain.model.playlist.Playlist;
import at.fhv.ec.domain.model.playlist.PlaylistId;
import at.fhv.ec.infrastructure.HibernatePlaylistRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@Transactional
class HibernatePlaylistRepositoryTests {

    @Inject
    HibernatePlaylistRepository hibernatePlaylistRepository;

    @Test
    void given_playlist_in_repository_when_findByUsername_then_return_equals_playlist() {
        // given
        UUID playlistIdUUIDExpected = UUID.randomUUID();
        PlaylistId playlistIdExpected = new PlaylistId(playlistIdUUIDExpected);
        String usernameExpected = "jdo1111";
        Playlist playlistExpected = Playlist.create(playlistIdExpected, usernameExpected);

        hibernatePlaylistRepository.persist(playlistExpected);

        // when
        Optional<Playlist> playlistActualOpt = hibernatePlaylistRepository.findByUsername(usernameExpected);

        // then
        assertFalse(playlistActualOpt.isEmpty());
        Playlist playlistActual = playlistActualOpt.get();

        assertEquals(playlistExpected, playlistActual);
        assertEquals(playlistIdUUIDExpected, playlistActual.getPlaylistId().getUUID());
        assertEquals(playlistIdExpected, playlistActual.getPlaylistId());
        assertEquals(usernameExpected, playlistActual.getUsername());
        assertNotNull(playlistActual.getSongs());
        assertEquals(0, playlistActual.getSongs().size());
    }
}
