package laurencewarne.mcbiomemapserver.minecraft;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import amidst.mojangapi.file.DotMinecraftDirectoryNotFoundException;
import amidst.mojangapi.file.MinecraftInstallation;
import amidst.mojangapi.minecraftinterface.MinecraftInterface;
import amidst.mojangapi.minecraftinterface.MinecraftInterfaceCreationException;
import amidst.mojangapi.minecraftinterface.MinecraftInterfaceException;
import amidst.mojangapi.minecraftinterface.MinecraftInterfaces;
import amidst.mojangapi.world.World;
import amidst.mojangapi.world.WorldBuilder;
import amidst.mojangapi.world.WorldOptions;
import amidst.mojangapi.world.WorldSeed;
import amidst.mojangapi.world.WorldType;
import lombok.NonNull;

public class WorldProvider {

    @NonNull
    private final Map<Integer, World> mcWorldLookup = new HashMap<>();
 
    public World getWorld(int seed) {
	WorldBuilder mcWorldBuilder = WorldBuilder.createSilentPlayerless();
	try {
	    MinecraftInstallation mcInstallation = MinecraftInstallation.
		newLocalMinecraftInstallation();
	} catch (DotMinecraftDirectoryNotFoundException e2) {
	    e2.printStackTrace();
	}
	Consumer<World> onDispose = world -> {};
	WorldOptions worldOptions = new WorldOptions(
	    WorldSeed.random(), WorldType.DEFAULT
	);
	MinecraftInterface mcInterface = null;
	try {
	    mcInterface = MinecraftInterfaces.fromLocalProfile(null);
	} catch (MinecraftInterfaceCreationException e1) {
	    e1.printStackTrace();
	}
	World mcWorld = null;
	try {
	    mcWorld = mcWorldBuilder.from(mcInterface, onDispose, worldOptions);
	} catch (MinecraftInterfaceException e) {
	    e.printStackTrace();
	}
	//mcWorld.getBiomeDataOracle().getBiomeAtMiddleOfChunk(0, 0);
	return mcWorld;
    }
}
