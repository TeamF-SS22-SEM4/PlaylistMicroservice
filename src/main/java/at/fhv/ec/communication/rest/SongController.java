package at.fhv.ec.communication.rest;

import at.fhv.ec.application.api.DownloadSongService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.UUID;

@Path("/songs")
public class SongController {
    @Inject
    DownloadSongService downloadSongService;

    @GET
    @Path("/{songId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "When the song cannot be found.")
    @APIResponse(responseCode = "500", description = "if the method is not yet implemented")
    @Operation(operationId = "getSong", description = "Responds with the MP3 of the song")
    public Response getSong(@PathParam("songId") UUID songId) {
        try {
            File songFile = downloadSongService.downloadSong(songId);
            return Response.ok(songFile)
                    .header("Content-Disposition", "attachment;filename=" + songFile)
                    .build();
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
