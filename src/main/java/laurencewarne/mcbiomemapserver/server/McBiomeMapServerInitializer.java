package laurencewarne.mcbiomemapserver.server;

import java.io.IOException;

import org.takes.Take;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.FtBasic;

import laurencewarne.mcbiomemapserver.handler.SeededBiomeHandler;
import laurencewarne.mcbiomemapserver.minecraft.WorldProvider;
import lombok.NonNull;

public class McBiomeMapServerInitializer {

    public FtBasic initServer(@NonNull final String mcInstallLocation) throws IOException {
	final WorldProvider worldProvider = new WorldProvider(mcInstallLocation);
	final Take biomeSeededHandler = new SeededBiomeHandler(worldProvider);
	return new FtBasic(
	    new TkFork(
		new FkRegex("/biome/seed", biomeSeededHandler),
		new FkRegex("/", "Hello world")
	    ), 8080
	);
    }    
}
