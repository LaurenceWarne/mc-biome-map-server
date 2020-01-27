package laurencewarne.mcbiomemapserver.handler;

import java.io.IOException;

import com.google.common.collect.Iterables;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.misc.Href;
import org.takes.rq.RqHref;
import org.takes.rs.RsHtml;

import amidst.mojangapi.world.World;
import laurencewarne.mcbiomemapserver.minecraft.WorldProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Processes a request for the biome type at a chunk coordinate and sends the found
 * biome type.
 */
@RequiredArgsConstructor
public class BiomeAtCoordinateRequestHandler implements Take {

    @NonNull @Getter @Setter
    private WorldProvider WorldProvider;

    @Override
    public Response act(@NonNull final Request request) throws IOException {
	final Href href = new RqHref.Base(request).href();
	final Iterable<String> chunkXInfo = href.param("chunkX");
	final Iterable<String> chunkYInfo = href.param("chunkY");
	try {
	    World world = WorldProvider.getWorld(0);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return new RsHtml(
	    "Chunk X information: " + Iterables.toString(chunkXInfo) + ", " +
	    "Chunk Y information: " + Iterables.toString(chunkYInfo)
	);
    }
    
}
