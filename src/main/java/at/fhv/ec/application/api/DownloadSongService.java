package at.fhv.ec.application.api;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface DownloadSongService {
    byte[] downloadSong(UUID songId) throws NoSuchElementException, IOException;
}
