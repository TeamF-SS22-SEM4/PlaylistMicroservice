package at.fhv.ec.unit.domain;

import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.domain.model.song.SongId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SongTests {
    @Test
    void given_songdetails_when_creating_song_then_details_equals() {
        // given
        UUID songIdUUDExpected = UUID.randomUUID();
        SongId songIdExpected = new SongId(songIdUUDExpected);
        String albumNameExpected = "SomeAlbum";
        String titleExpected = "SomeTitle";
        String durationExpected = "20:10";

        // when
        Song song = Song.create(songIdExpected, albumNameExpected, titleExpected, durationExpected, "");

        // then
        assertEquals(songIdUUDExpected, song.getSongId().getUUID());
        assertEquals(songIdExpected, song.getSongId());
        assertEquals(albumNameExpected, song.getAlbumName());
        assertEquals(titleExpected, song.getTitle());
        assertEquals(durationExpected, song.getDuration());
    }
}
