package laurencewarne.mcbiomemapserver.handler;

import java.io.IOException;
import java.util.NoSuchElementException;

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
	String chunkXStart, chunkYStart, chunkXEnd, chunkYEnd;
	try {
	    chunkXStart = Iterables.getOnlyElement(href.param("chunkStartX"));
	    chunkYStart = Iterables.getOnlyElement(href.param("chunkStartY"));
	    chunkXEnd = Iterables.getOnlyElement(href.param("chunkEndX"));
	    chunkYEnd = Iterables.getOnlyElement(href.param("chunkEndY"));
	}
	catch (NoSuchElementException e) {
	    chunkXStart = chunkYStart = chunkXEnd = chunkYEnd = "0";
	}
	try {
	    World world = WorldProvider.getWorld(0);
	    return new RsHtml(world.getGeneratorOptions() + " " + world.getBiomeDataOracle().getBiomeAtMiddleOfChunk(0, 0).getName());
	    
	} catch (Exception e) {
	    return new RsHtml(e.getMessage());
	}
    }
    
}
