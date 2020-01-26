package laurencewarne.mcbiomemapserver.handler;

import java.io.IOException;

import com.google.common.collect.Iterables;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.misc.Href;
import org.takes.rq.RqHref;
import org.takes.rs.RsHtml;

/**
 * Processes a request for the biome type at a chunk coordinate and sends the found
 * biome type.
 */
public class BiomeAtCoordinateRequestHandler implements Take {

    @Override
    public Response act(final Request request) throws IOException {
	final Href href = new RqHref.Base(request).href();
	final Iterable<String> chunkXInfo = href.param("chunkX");
	final Iterable<String> chunkYInfo = href.param("chunkY");
	return new RsHtml(
	    "Chunk X information: " + Iterables.toString(chunkXInfo) + "\n" +
	    "Chunk Y information: " + Iterables.toString(chunkYInfo)
	);
    }
    
}
