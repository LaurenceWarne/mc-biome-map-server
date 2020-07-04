package laurencewarne.mcbiomemapserver.minecraft;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import amidst.mojangapi.file.LauncherProfile;
import amidst.mojangapi.file.MinecraftInstallation;
import amidst.mojangapi.minecraftinterface.MinecraftInterface;
import amidst.mojangapi.minecraftinterface.MinecraftInterfaces;
import amidst.mojangapi.world.World;
import amidst.mojangapi.world.WorldBuilder;
import amidst.mojangapi.world.WorldOptions;
import amidst.mojangapi.world.WorldSeed;
import amidst.mojangapi.world.WorldType;

public class WorldProviderTest {

    private MinecraftInstallation mcInstallation;

    @Before
    public void setUp() throws Exception {
	mcInstallation = MinecraftInstallation.
	    newLocalMinecraftInstallation((new File("~/.minecraft")).toPath());
    }

    @Test
    public void test() throws Exception {
	final WorldBuilder mcWorldBuilder = WorldBuilder.createSilentPlayerless();
	final LauncherProfile launcherProfile = mcInstallation.newLauncherProfile("1.15.1");
	final MinecraftInterface mcInterface = MinecraftInterfaces.fromLocalProfile(
	    launcherProfile
	);
	final WorldOptions worldOptions = new WorldOptions(
	    WorldSeed.fromSaveGame(3), WorldType.DEFAULT
	);	
	final World mcWorld = mcWorldBuilder.from(mcInterface, worldOptions);
    }

}
