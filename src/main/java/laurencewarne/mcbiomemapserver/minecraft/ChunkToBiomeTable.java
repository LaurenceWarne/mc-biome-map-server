package laurencewarne.mcbiomemapserver.minecraft;

import com.google.common.collect.ForwardingTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import org.xembly.Directives;
import org.xembly.Xembler;

import amidst.mojangapi.world.biome.Biome;
import lombok.NonNull;

public class ChunkToBiomeTable extends ForwardingTable<Integer, Integer, Biome> {

    @NonNull
    private final Table<Integer, Integer, Biome> delegate = HashBasedTable.create();

    @Override
    protected Table<Integer, Integer, Biome> delegate() {
	return delegate;
    }

    public Xembler toXML() {
	final Directives directives = new Directives();
	directives.add("root");
	for (Cell<Integer, Integer, Biome> chunk : delegate.cellSet()) {
	    directives.add("chunk");
	    directives.add("x").set(chunk.getColumnKey()).up();
	    directives.add("y").set(chunk.getRowKey()).up();
	    directives.add("biome").set(chunk.getValue().getName())
		.attr("id", chunk.getValue().getIndex()).up().up();
	}
	return new Xembler(directives);
    }
}
