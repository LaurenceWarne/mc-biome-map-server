package laurencewarne.mcbiomemapserver.server;

import java.io.IOException;

import org.takes.Take;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.FtBasic;

import laurencewarne.mcbiomemapserver.handler.BiomeAtCoordinateRequestHandler;
import laurencewarne.mcbiomemapserver.minecraft.WorldProvider;

public class McBiomeMapServerInitializer {

    public FtBasic initServer() throws IOException {
	final WorldProvider worldProvider = new WorldProvider("~/.minecraft");
	final Take biomeCoordHandler = new BiomeAtCoordinateRequestHandler(worldProvider);
	return new FtBasic(
	    new TkFork(
		new FkRegex("/biome", biomeCoordHandler),
		new FkRegex("/", "Hello world")
	    ), 8080
	);
    }    
}
