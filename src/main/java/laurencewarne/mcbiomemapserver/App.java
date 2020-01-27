/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package laurencewarne.mcbiomemapserver;

import java.io.IOException;

import org.takes.http.Exit;

import laurencewarne.mcbiomemapserver.server.McBiomeMapServerInitializer;

public class App {

    // Pass in minecraft install directory and optionally a launch profile
    public static void main(String[] args) {
	try {
	    new McBiomeMapServerInitializer().initServer().start(Exit.NEVER);;
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
