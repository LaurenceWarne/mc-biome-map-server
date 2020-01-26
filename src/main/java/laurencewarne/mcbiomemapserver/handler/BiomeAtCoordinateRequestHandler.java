package laurencewarne.mcbiomemapserver.handler;

import java.io.IOException;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;

/**
 * Processes a request for the biome type at a chunk coordinate and sends the found
 * biome type.
 */
public class BiomeAtCoordinateRequestHandler implements Take {

    @Override
    public Response act(final Request req) throws IOException {
	return null;
    }
    
}
