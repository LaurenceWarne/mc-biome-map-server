package laurencewarne.mcbiomemapserver.minecraft;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import amidst.mojangapi.file.DotMinecraftDirectoryNotFoundException;
import amidst.mojangapi.file.LauncherProfile;
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
import amidst.parsing.FormatException;
import lombok.NonNull;

public class WorldProvider {

    /**Acts as a cache so we don't have to keep reloading the same mc world.*/
    @NonNull
    private final Map<Integer, World> mcWorldLookup = new HashMap<>();
 
    public World getWorld(int seed)
	throws DotMinecraftDirectoryNotFoundException,
	       MinecraftInterfaceCreationException,
	       MinecraftInterfaceException,
	       FileNotFoundException,
	       FormatException,
	       IOException
    {
	final WorldBuilder mcWorldBuilder = WorldBuilder.createSilentPlayerless();
	final MinecraftInstallation mcInstallation = MinecraftInstallation.
	    newLocalMinecraftInstallation("~/.minecraft");
	final LauncherProfile launcherProfile = mcInstallation
	    .newLauncherProfile("1.13.2");
	final MinecraftInterface mcInterface = MinecraftInterfaces
	    .fromLocalProfile(launcherProfile);

	final Consumer<World> onDispose = world -> {};
	final WorldOptions worldOptions = new WorldOptions(
	    WorldSeed.random(), WorldType.DEFAULT
	);	
	final World mcWorld = mcWorldBuilder.from(mcInterface, onDispose, worldOptions);
	return mcWorld;
    }
}
