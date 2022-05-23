package at.fhv.ec.application.impl;

import at.fhv.ec.application.api.PurchaseService;
import at.fhv.ec.domain.model.playlist.Playlist;
import at.fhv.ec.domain.model.playlist.PlaylistId;
import at.fhv.ec.domain.model.song.Song;
import at.fhv.ec.domain.model.song.SongId;
import at.fhv.ec.infrastructure.HibernatePlaylistRepository;
import at.fhv.ec.infrastructure.HibernateSongRepository;
import at.fhv.ss22.ea.f.communication.dto.DigitalProductPurchasedDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    @Inject
    HibernatePlaylistRepository hibernatePlaylistRepository;

    @Inject
    HibernateSongRepository hibernateSongRepository;


    @Override
    public void receivePurchase(DigitalProductPurchasedDTO event) {
        // Check if playlist for user exists
        Optional<Playlist> playlistOpt = hibernatePlaylistRepository.findByUsername(event.getUsername());
        Playlist playlist;

        if(playlistOpt.isPresent()) {
            playlist = playlistOpt.get();
        } else {
            // Create playlist if not
            playlist = Playlist.create(
                    new PlaylistId(UUID.randomUUID()),
                    event.getUsername()
            );

            hibernatePlaylistRepository.persist(playlist);
        }

        event.getPurchasedSongs().forEach(songDTO -> {
            Optional<Song> songOpt = hibernateSongRepository.findByTitleAndAlbum(songDTO.getTitle(), event.getAlbumName());
            Song song;
            // Check if song already exists
            if(songOpt.isEmpty()) {
                // If not create song
                song = Song.create(
                        new SongId(UUID.randomUUID()),
                        event.getAlbumName(),
                        songDTO.getTitle(),
                        songDTO.getDuration()
                );

                hibernateSongRepository.persist(song);
            } else {
                song = songOpt.get();
            }

            // Add song to playlist
            playlist.addSongToPlaylist(song.getSongId());
        });

    }
}
