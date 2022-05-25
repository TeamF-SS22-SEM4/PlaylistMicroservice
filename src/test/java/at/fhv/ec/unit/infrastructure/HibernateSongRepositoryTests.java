package at.fhv.ec.unit.infrastructure;

import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.domain.model.song.SongId;
import at.fhv.ec.infrastructure.HibernateSongRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
@Transactional
class HibernateSongRepositoryTests {
    @Inject
    HibernateSongRepository hibernateSongRepository;

    @Test
    void given_song_in_repository_when_findByTitleAndAlbum_then_return_equals_song() {
        // given
        UUID songIdUUDExpected = UUID.randomUUID();
        SongId songIdExpected = new SongId(songIdUUDExpected);
        String albumNameExpected = "SomeAlbum";
        String titleExpected = "SomeTitle";
        String durationExpected = "20:10";
        Song songExpected = Song.create(songIdExpected, albumNameExpected, titleExpected, durationExpected, "");

        hibernateSongRepository.persist(songExpected);

        // when
        Optional<Song> songActualOpt = hibernateSongRepository.findByTitleAndAlbum(titleExpected, albumNameExpected);

        // then
        assertFalse(songActualOpt.isEmpty());

        Song songActual = songActualOpt.get();
        assertEquals(songExpected, songActual);
        assertEquals(songIdUUDExpected, songActual.getSongId().getUUID());
        assertEquals(songIdExpected, songActual.getSongId());
        assertEquals(albumNameExpected, songActual.getAlbumName());
        assertEquals(titleExpected, songActual.getTitle());
        assertEquals(durationExpected, songActual.getDuration());
    }

    @Test
    void given_song_in_repository_when_findBySongId_then_return_equals_song() {
        // given
        UUID songIdUUDExpected = UUID.randomUUID();
        SongId songIdExpected = new SongId(songIdUUDExpected);
        String albumNameExpected = "SomeAlbum";
        String titleExpected = "SomeTitle";
        String durationExpected = "20:10";
        Song songExpected = Song.create(songIdExpected, albumNameExpected, titleExpected, durationExpected, "");

        hibernateSongRepository.persist(songExpected);

        // when
        Optional<Song> songActualOpt = hibernateSongRepository.findBySongId(songIdExpected);

        // then
        assertFalse(songActualOpt.isEmpty());

        Song songActual = songActualOpt.get();
        assertEquals(songExpected, songActual);
        assertEquals(songIdUUDExpected, songActual.getSongId().getUUID());
        assertEquals(songIdExpected, songActual.getSongId());
        assertEquals(albumNameExpected, songActual.getAlbumName());
        assertEquals(titleExpected, songActual.getTitle());
        assertEquals(durationExpected, songActual.getDuration());
    }
}
