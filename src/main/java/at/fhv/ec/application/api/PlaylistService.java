package at.fhv.ec.application.api;

import at.fhv.ec.application.dto.SongDTO;

import java.util.List;
import java.util.NoSuchElementException;

public interface PlaylistService {
    List<SongDTO> playlistByUsername(String username) throws NoSuchElementException;
}
