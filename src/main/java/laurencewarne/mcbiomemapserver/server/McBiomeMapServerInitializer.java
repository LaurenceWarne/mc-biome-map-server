package laurencewarne.mcbiomemapserver.server;

import java.io.IOException;

import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.FtBasic;

public class McBiomeMapServerInitializer {

    public FtBasic initServer() throws IOException {
	return new FtBasic(
	    new TkFork(
		new FkRegex("/biome", "The biome is: ?"),
		new FkRegex("/", "Hello world")
	    ), 8080
	);
    }    
}
