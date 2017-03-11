package com.jfixby.util.terain.test.api.landscape;

import com.jfixby.scarabei.api.collections.Collection;

public interface LandscapeOperationResult<T> {

	Collection<TerrainBlock<T>> removedBlocks();

	Collection<TerrainBlock<T>> addedBlocks();

}
