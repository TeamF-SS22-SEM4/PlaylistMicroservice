package at.fhv.ec.unit.domain;

import at.fhv.ec.domain.model.playlist.Playlist;
import at.fhv.ec.domain.model.playlist.PlaylistId;
import at.fhv.ec.domain.model.song.SongId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTests {
    @Test
    void given_playlistdetail_when_creating_playlist_then_details_equals_and_playlist_empty() {
        // given
        UUID playlistIdUUIDExpected = UUID.randomUUID();
        PlaylistId playlistIdExpected = new PlaylistId(playlistIdUUIDExpected);
        String usernameExpected = "jdo1111";

        // when
        Playlist playlist = Playlist.create(playlistIdExpected, usernameExpected);

        // then
        assertEquals(playlistIdUUIDExpected, playlist.getPlaylistId().getUUID());
        assertEquals(playlistIdExpected, playlist.getPlaylistId());
        assertEquals(usernameExpected, playlist.getUsername());
        assertNotNull(playlist.getSongs());
        assertEquals(0, playlist.getSongs().size());
    }

    @Test
    void given_3songIds_when_adding_to_playlist_then_size_equals3() {
        // given
        List<SongId> songIds = List.of(
                new SongId(UUID.randomUUID()),
                new SongId(UUID.randomUUID()),
                new SongId(UUID.randomUUID())
        );

        Playlist playlist = Playlist.create(new PlaylistId(UUID.randomUUID()), "jdo1111");

        // when
        songIds.forEach(playlist::addSongToPlaylist);

        // then
        assertEquals(songIds.size(), playlist.getSongs().size());
    }

    @Test
    void given_3songIds_2same_when_adding_to_playlist_then_exception_is_thrown() {
        // given
        SongId songId_1 = new SongId(UUID.randomUUID());
        SongId songId_2 = new SongId(UUID.randomUUID());
        SongId songId_1_2 = songId_1;

        Playlist playlist = Playlist.create(new PlaylistId(UUID.randomUUID()), "jdo1111");

        // when ... then
        playlist.addSongToPlaylist(songId_1);
        playlist.addSongToPlaylist(songId_2);

        assertThrows(UnsupportedOperationException.class, () -> {
           playlist.addSongToPlaylist(songId_1_2);
        });
    }
}
