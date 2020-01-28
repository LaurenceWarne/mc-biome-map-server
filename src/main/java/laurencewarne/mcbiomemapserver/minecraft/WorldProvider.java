package laurencewarne.mcbiomemapserver.minecraft;

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
import lombok.Getter;
import lombok.NonNull;

/**
 * Provides Amidst {@link World} objects from launcher profiles and seeds.
 */
public class WorldProvider {

    @NonNull @Getter
    private final MinecraftInstallation mcInstallation;
    /**Acts as a cache so we don't have to keep reloading the same mc world.*/
    @NonNull
    private final Map<Integer, World> mcWorldLookup = new HashMap<>();

    /**
     * Create a new WorldProvider object using the minecraft installation at the
     * specified directory.
     *
     * @param mcInstallation location on disk of the minecraft isntallation
     * @throws DotMinecraftDirectoryNotFoundException if the directory was not found
     * or does not point to a minecraft installation
     */
    public WorldProvider(@NonNull final String mcInstallationLocation)
	throws DotMinecraftDirectoryNotFoundException {
	mcInstallation = MinecraftInstallation.
	    newLocalMinecraftInstallation(mcInstallationLocation);
    }
 
    public World getWorld(@NonNull final String launcherProfileName, final int seed)
	throws MinecraftInterfaceCreationException,
	       MinecraftInterfaceException,
	       FormatException,
	       IOException
    {
	final WorldBuilder mcWorldBuilder = WorldBuilder.createSilentPlayerless();
	final LauncherProfile launcherProfile = mcInstallation.newLauncherProfile(
	    launcherProfileName
	);
	final MinecraftInterface mcInterface = MinecraftInterfaces.fromLocalProfile(
	    launcherProfile
	);
	final Consumer<World> onDispose = world -> {};
	final WorldOptions worldOptions = new WorldOptions(
	    WorldSeed.random(), WorldType.DEFAULT
	);	
	final World mcWorld = mcWorldBuilder.from(mcInterface, onDispose, worldOptions);
	return mcWorld;
    }
}
