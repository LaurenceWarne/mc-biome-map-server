package laurencewarne.mcbiomemapserver.handler;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import com.google.common.collect.Iterables;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.misc.Href;
import org.takes.rq.RqHref;
import org.takes.rs.RsHtml;

import amidst.mojangapi.file.SaveGame;
import amidst.mojangapi.file.directory.SaveDirectory;
import amidst.mojangapi.file.service.SaveDirectoryService;
import laurencewarne.mcbiomemapserver.minecraft.WorldProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class WorldDisposerHandler implements Take {

    @NonNull @Getter @Setter
    private WorldProvider worldProvider;        
    @NonNull @Getter @Setter
    private String defaultProfile;
    @NonNull
    private SaveDirectoryService saveDirectoryService = new SaveDirectoryService();    

    @Override
    public Response act(@NonNull final Request request) throws IOException {
	final Href href = new RqHref.Base(request).href();
	long seed;
	try {
	    seed = Long.parseLong(Iterables.getOnlyElement(href.param("seed")));
	} catch (NumberFormatException e) {
	    return new RsHtml("Failure");
	} catch (NoSuchElementException e) {
	    // try to get a save now
	    try {
		final SaveDirectory dir = saveDirectoryService.newSaveDirectory(
		    new File(Iterables.getOnlyElement(href.param("save")))
		);
		final SaveGame game = new SaveGame(dir, saveDirectoryService.readLevelDat(dir));
		seed = game.getSeed();
	    } catch (Exception e2) {
		return new RsHtml("Failure");
	    }
	}
	worldProvider.disposeWorld(
	    Iterables.getFirst(href.param("profile"), defaultProfile), seed
	);
	return new RsHtml("Success");
    }    
}
