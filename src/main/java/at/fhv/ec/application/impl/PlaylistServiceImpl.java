package at.fhv.ec.application.impl;

import at.fhv.ec.application.api.PlaylistService;
import at.fhv.ec.application.dto.SongDTO;
import at.fhv.ec.domain.model.playlist.Playlist;
import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.infrastructure.HibernatePlaylistRepository;
import at.fhv.ec.infrastructure.HibernateSongRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ApplicationScoped
@Transactional
public class PlaylistServiceImpl implements PlaylistService {

    @Inject
    HibernatePlaylistRepository playlistRepository;

    @Inject
    HibernateSongRepository songRepository;

    @Override
    public List<SongDTO> playlistByUsername(String username) throws NoSuchElementException {
        Playlist playlist = playlistRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

        List<SongDTO> songs = new ArrayList<>();

        playlist.getSongs().forEach(songId -> {
            Song song = songRepository.findBySongId(songId).orElseThrow(NoSuchElementException::new);

            songs.add(dtoFromSong(song));
        });

        return songs;
    }

    private SongDTO dtoFromSong(Song song) {
        return SongDTO.builder()
                .withSongId(song.getSongId().getUUID())
                .withAlbumName(song.getAlbumName())
                .withTitle(song.getTitle())
                .withDuration(song.getDuration())
                .build();
    }
}
