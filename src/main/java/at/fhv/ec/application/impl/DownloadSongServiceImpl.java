package at.fhv.ec.application.impl;

import at.fhv.ec.application.api.DownloadSongService;
import at.fhv.ec.domain.model.song.SongId;
import at.fhv.ec.infrastructure.HibernateSongRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.UUID;

@ApplicationScoped
public class DownloadSongServiceImpl implements DownloadSongService {

    @Inject
    HibernateSongRepository hibernateSongRepository;

    @Override
    public File downloadSong(UUID songId) throws NoSuchElementException {
        // Ignore return value and check only if song exists
        hibernateSongRepository.findBySongId(new SongId(songId)).orElseThrow(NoSuchElementException::new);

        File songFile = new File("/files/mp3/example.mp3");

        if(!songFile.exists()) {
            throw new NoSuchElementException();
        }

        return songFile;
    }
}