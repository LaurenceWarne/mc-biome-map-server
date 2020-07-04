package laurencewarne.mcbiomemapserver.handler;

import java.io.File;

import com.google.common.collect.Iterables;

import org.takes.misc.Href;

import amidst.mojangapi.file.SaveGame;
import amidst.mojangapi.file.directory.SaveDirectory;
import amidst.mojangapi.file.service.SaveDirectoryService;
import amidst.mojangapi.world.World;
import laurencewarne.mcbiomemapserver.minecraft.WorldProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class FromSaveBiomeHandler extends BiomeAtCoordinateRequestHandler {

    @NonNull @Getter @Setter
    private WorldProvider worldProvider;    
    @NonNull @Getter @Setter
    private String defaultProfile;
    @NonNull
    private SaveDirectoryService saveDirectoryService = new SaveDirectoryService();

    @Override
    protected World getWorld(@NonNull Href href) {
	final SaveGame game;
	try {
	    final SaveDirectory dir = saveDirectoryService.newSaveDirectory(
		(new File(Iterables.getOnlyElement(href.param("save")))).toPath()
	    );
	    game = new SaveGame(dir, saveDirectoryService.readLevelDat(dir));
	} catch (Exception e) {
	    throw new IllegalStateException("Error reading save file data: " + e.getMessage());
	}
	try {
	    return worldProvider.getWorld(
		Iterables.getFirst(href.param("profile"), defaultProfile),
		game.getSeed()
	    );
	} catch (Exception e) {
	    throw new IllegalStateException("Error initialising amidst: " + e.getMessage());
	}	
    }
}
