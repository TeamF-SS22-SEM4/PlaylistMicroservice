package at.fhv.ec.communication.rest;

import at.fhv.ec.application.dto.PlayableSongDTO;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/songs")
public class SongController {

    @GET
    @Path("/{songId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "When the song cannot be found.")
    @APIResponse(responseCode = "500", description = "if the method is not yet implemented")
    @Operation(operationId = "getSong", description = "Responds with the MP3 of the song")
    public Response getSong(@PathParam("songId") UUID songId) {
        // TODO: Implement --> Return MP3 file for streaming
        return Response.serverError().build();
    }
}
