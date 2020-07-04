package laurencewarne.mcbiomemapserver.minecraft;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

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
    private final Table<Long, String,  World> mcWorldLookup = HashBasedTable.create();

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
	    newLocalMinecraftInstallation((new File(mcInstallationLocation)).toPath());
    }
 
    public World getWorld(@NonNull final String launcherProfileName, final long seed)
	throws MinecraftInterfaceCreationException,
	       MinecraftInterfaceException,
	       FormatException,
	       IOException
    {
	if (!mcWorldLookup.contains(seed, launcherProfileName)) {
	    final WorldBuilder mcWorldBuilder = WorldBuilder.createSilentPlayerless();
	    final LauncherProfile launcherProfile = mcInstallation.newLauncherProfile(
		launcherProfileName
	    );
	    final MinecraftInterface mcInterface = MinecraftInterfaces.fromLocalProfile(
		launcherProfile
	    );
	    final WorldOptions worldOptions = new WorldOptions(
		WorldSeed.fromSaveGame(seed), WorldType.DEFAULT
	    );	
	    final World mcWorld = mcWorldBuilder.from(mcInterface, worldOptions);
	    mcWorldLookup.put(seed, launcherProfileName, mcWorld);
	}
	return mcWorldLookup.get(seed, launcherProfileName);
    }

    /**
     * Remove a Minecraft world from this object's storage.
     *
     * @param launcherProfileName launcher associated with the world.
     * @param seed the world seed.
     */
    public void disposeWorld(@NonNull final String launcherProfileName, final long seed) {
	if (mcWorldLookup.contains(seed, launcherProfileName)) {
	    final World world =  mcWorldLookup.get(seed, launcherProfileName);
	    mcWorldLookup.remove(seed, launcherProfileName);
	}
    }
}
