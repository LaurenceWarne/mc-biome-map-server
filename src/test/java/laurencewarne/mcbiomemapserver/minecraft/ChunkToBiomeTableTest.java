package laurencewarne.mcbiomemapserver.minecraft;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.xembly.Xembler;

import amidst.mojangapi.world.biome.Biome;

public class ChunkToBiomeTableTest {

    private ChunkToBiomeTable table;
    @Mock
    private Biome b1, b2, b3;

    private static String removeXmlDeclaration(String xml) {
	return xml.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
    }

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	table = new ChunkToBiomeTable();
	when(b1.getName()).thenReturn("biome1");
	when(b2.getName()).thenReturn("biome2");
	when(b3.getName()).thenReturn("biome3");
	when(b1.getIndex()).thenReturn(1);
	when(b2.getIndex()).thenReturn(2);
	when(b3.getIndex()).thenReturn(3);
    }

    @Test
    public void testEmptyTableReturnsEmptyXML() {
	Xembler xml = table.toXML();
	assertThat(removeXmlDeclaration(xml.xmlQuietly())).isXmlEqualTo("<root></root>");
    }

    @Test
    public void testTableOneElementReturnsValidXML() {
	table.put(0, 0, b1);
	Xembler xml = table.toXML();
	assertThat(removeXmlDeclaration(xml.xmlQuietly())).isXmlEqualTo(
	    "<root><chunk><x>0</x><y>0</y><biome id='1'>biome1</biome></chunk></root>"
	);
    }

    @Test
    public void testTableWithMultipleElementsReturnsValidXML() {
	table.put(0, 0, b1);
	table.put(-9, 4, b2);
	Xembler xml = table.toXML();	
	assertThat(removeXmlDeclaration(xml.xmlQuietly())).isXmlEqualTo(
	    "<root><chunk><x>0</x><y>0</y><biome id='1'>biome1</biome></chunk>" +
	    "<chunk><x>4</x><y>-9</y><biome id='2'>biome2</biome></chunk></root>"
	);	
    }

}
