package laurencewarne.mcbiomemapserver.handler;

import java.io.IOException;
import java.util.NoSuchElementException;

import com.google.common.collect.Iterables;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.misc.Href;
import org.takes.rq.RqHref;
import org.takes.rs.xe.RsXembly;
import org.takes.rs.xe.XeDirectives;
import org.xembly.Directives;

import amidst.mojangapi.world.World;
import laurencewarne.mcbiomemapserver.minecraft.ChunkToBiomeTable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Processes a request for the biome type at a set of chunk coordinates and sends the
 * found biome types.
 */
public abstract class BiomeAtCoordinateRequestHandler implements Take {

    @NonNull @Getter
    private String notEnoughParamsMsg = "Not enough parameters detected, ensure that" +
	" all of chunkStartX, chunkStartY, chunkEndX, chunkEndY are specified.";
    @NonNull @Getter
    private String badParamsMsg = "Not all of the parameters are of the correct " +
	"type, ensure that all of chunkStartX, chunkStartY, chunkEndX, chunkEndY " +
	"are given as integers.";
    @NonNull @Getter
    private String amidstErrorMsg = "Error finding biomes: ";

    protected abstract World getWorld( @NonNull final Href href );

    @Override
    public Response act(@NonNull final Request request) throws IOException {
	final Href href = new RqHref.Base(request).href();
	int chunkXStart, chunkYStart, chunkXEnd, chunkYEnd;
	try {
	    chunkXStart = Integer.parseInt(Iterables.getOnlyElement(href.param("chunkStartX")));
	    chunkYStart = Integer.parseInt(Iterables.getOnlyElement(href.param("chunkStartY")));
	    chunkXEnd = Integer.parseInt(Iterables.getOnlyElement(href.param("chunkEndX")));
	    chunkYEnd = Integer.parseInt(Iterables.getOnlyElement(href.param("chunkEndY")));
	}
	catch (NoSuchElementException e) {
	    return new RsXembly(new XmlError(notEnoughParamsMsg).xml());
	}
	catch (NumberFormatException e) {
	    return new RsXembly(new XmlError(badParamsMsg).xml());
	}
	try {
	    World world = getWorld(href);
	    final ChunkToBiomeTable table = new ChunkToBiomeTable();
	    for (int i = chunkXStart; i <= chunkXEnd; i++){
		for (int j = chunkYStart; j <= chunkYEnd; j++){
		    table.put(j, i, world.getBiomeDataOracle().getBiomeAtMiddleOfChunk(i, j));
		}
	    }
	    return new RsXembly(table.toXML());
	    
	} catch (Exception e) {
	    return new RsXembly(
		new XmlError(amidstErrorMsg + "'" + e.getMessage() + "'").xml()
	    );
	}
    }

    @RequiredArgsConstructor
    public static class XmlError {

	@NonNull @Getter
	private final String msg;

	public XeDirectives xml() {
	    return new XeDirectives(
		new Directives().add("root").add("error").set(msg)
	    );
	}
    }
}
