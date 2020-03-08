package laurencewarne.mcbiomemapserver;

import java.io.IOException;

import org.takes.http.Exit;

import laurencewarne.mcbiomemapserver.server.McBiomeMapServerInitializer;

public class App {

    // Pass minecraft install directory in cmdline args
    public static void main(String[] args) {
	final String mcInstallLocation = (args.length > 0)? args[0] :
	    System.getProperty("user.home") + "/.minecraft";
	int port = 29171;
	try {
	    port = Integer.parseInt(args[1]);
	} catch (Exception e) {
	    System.out.println("No valid port specified, using port: " + port);
	}
	try {
	    new McBiomeMapServerInitializer()
		.initServer(mcInstallLocation, port)
		.start(Exit.NEVER);;
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
