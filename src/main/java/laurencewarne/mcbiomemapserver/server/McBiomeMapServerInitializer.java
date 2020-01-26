package laurencewarne.mcbiomemapserver.server;

import java.io.IOException;

import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.FtBasic;

public class McBiomeMapServerInitializer {

    public void initServer() {
	try {
	    new FtBasic(
		new TkFork(
		    new FkRegex("/biomeAt", "The biome is: ?"),
		    new FkRegex("/", "Hello world")
		)
	    );
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }    
}
