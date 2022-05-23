package at.fhv.ec.unit.application;

import at.fhv.ec.application.api.PlaylistService;
import at.fhv.ec.application.dto.SongDTO;
import at.fhv.ec.domain.model.playlist.Playlist;
import at.fhv.ec.domain.model.playlist.PlaylistId;
import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.domain.model.song.SongId;
import at.fhv.ec.infrastructure.HibernatePlaylistRepository;
import at.fhv.ec.infrastructure.HibernateSongRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@Transactional
class PlaylistServiceTests {
    @Inject
    PlaylistService playlistService;

    @InjectMock
    HibernatePlaylistRepository playlistRepository;

    @InjectMock
    HibernateSongRepository songRepository;

    @Test
    void given_playlist_in_repository_when_playlistByUsername_return_equals_playlist () {
        // given
        UUID playlistIdUUIDExpected = UUID.randomUUID();
        PlaylistId playlistIdExpected = new PlaylistId(playlistIdUUIDExpected);
        String usernameExpected = "jdo1111";
        Playlist playlistExpected = Playlist.create(playlistIdExpected, usernameExpected);

        UUID songIdUUDExpected = UUID.randomUUID();
        SongId songIdExpected = new SongId(songIdUUDExpected);
        String albumNameExpected = "SomeAlbum";
        String titleExpected = "SomeTitle";
        String durationExpected = "20:10";

        List<Song> songsExpected = List.of(Song.create(songIdExpected, albumNameExpected, titleExpected, durationExpected));
        playlistExpected.addSongToPlaylist(songIdExpected);

        Mockito.when(playlistRepository.findByUsername(usernameExpected)).thenReturn(Optional.of(playlistExpected));
        Mockito.when(songRepository.findBySongId(songIdExpected)).thenReturn(Optional.of(songsExpected.get(0)));

        // when
        List<SongDTO> songsActual = playlistService.playlistByUsername(usernameExpected);

        // then
        assertEquals(songsExpected.size(), songsActual.size());
        assertEquals(songsExpected.get(0).getSongId().getUUID(), songsActual.get(0).getSongId());
        assertEquals(songsExpected.get(0).getAlbumName(), songsActual.get(0).getAlbumName());
        assertEquals(songsExpected.get(0).getTitle(), songsActual.get(0).getTitle());
        assertEquals(songsExpected.get(0).getDuration(), songsActual.get(0).getDuration());
    }
}
