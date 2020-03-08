package laurencewarne.mcbiomemapserver.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.takes.Request;
import org.takes.misc.Href;
import org.takes.rq.RqFake;
import org.takes.rs.RsPrint;

import amidst.mojangapi.world.World;
import amidst.mojangapi.world.biome.Biome;
import amidst.mojangapi.world.oracle.BiomeDataOracle;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class BiomeAtCoordinateRequestHandlerTest {

    @Mock
    private World world;
    @Mock
    private BiomeDataOracle biomeDataOracle;
    @Mock
    private Biome biome;
    private BiomeAtCoordinateRequestHandler handler;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	handler = new ShadowImpl(world);
	when(world.getBiomeDataOracle()).thenReturn(biomeDataOracle);
	try {
	    when(biomeDataOracle.getBiomeAtMiddleOfChunk(anyInt(), anyInt()))
		.thenReturn(biome);
	} catch (Exception e) {
	    fail("Can't mock BiomeDataOracle");
	}
	when(biome.getName()).thenReturn("Test Biome");
    }

    @Test
    public void testNoErrorXmlOnValidParams() throws IOException {
	final Request request = new RqFake(
	    "GET", "/?chunkStartX=3&chunkStartY=4&chunkEndX=10&chunkEndY=10"
	);
	System.out.println(new RsPrint(handler.act(request)).printBody());
	assertTrue(
	    !removeXmlDeclaration(
		new RsPrint(handler.act(request)).printBody()	
	    ).contains("error")
	);
    }

    @Test
    public void testErrorXmlReturnedOnNoParams() throws IOException {
	assertEquals(
	    "<root><error>" + handler.getNotEnoughParamsMsg() + "</error></root>",
	    removeXmlDeclaration(
		new RsPrint(handler.act(new RqFake("GET", "/"))).printBody()	
	    )
	);
    }

    @Test
    public void testErrorXmlReturnedOnSomeParams() throws IOException {
	final Request request = new RqFake(
	    "GET", "/?chunkStartX=3&chunkStartY=4&chunkEndX=10"
	);
	assertEquals(
	    "<root><error>" + handler.getNotEnoughParamsMsg() + "</error></root>",
	    removeXmlDeclaration(
		new RsPrint(handler.act(request)).printBody()	
	    )
	);
    }


    private static String removeXmlDeclaration(String xml) {
	return xml.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
    }

    @RequiredArgsConstructor
    private static class ShadowImpl extends BiomeAtCoordinateRequestHandler {

	@NonNull
	private World world;

	protected World getWorld(Href href) {
	    return world;
	}
    }
}
