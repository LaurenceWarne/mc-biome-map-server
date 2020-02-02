package laurencewarne.mcbiomemapserver.handler;

import java.util.NoSuchElementException;

import com.google.common.collect.Iterables;

import org.takes.misc.Href;

import amidst.mojangapi.world.World;
import laurencewarne.mcbiomemapserver.minecraft.WorldProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class SeededBiomeHandler extends BiomeAtCoordinateRequestHandler {

    @NonNull @Getter @Setter
    private WorldProvider worldProvider;    
    @NonNull @Getter @Setter
    private String defaultProfile;

    @Override
    protected World getWorld(@NonNull Href href) {
	final int seed;
	try {
	    seed = Integer.parseInt(Iterables.getOnlyElement(href.param("seed")));
	}
	catch (NoSuchElementException e) {
	    throw new IllegalArgumentException("Href has no seed parameter");
	}
	catch (NumberFormatException e) {
	    throw new IllegalArgumentException("Passed seed parameter is not numeric");
	}
	try {
	    return worldProvider.getWorld(
		Iterables.getFirst(href.param("profile"), defaultProfile),
		seed
	    );
	} catch (Exception e) {
	    throw new IllegalStateException("Error initialising amidst: " + e.getMessage());
	}
    }
}
