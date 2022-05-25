package at.fhv.ec.application.api;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface DownloadSongService {
    File downloadSong(UUID songId) throws NoSuchElementException;
}
