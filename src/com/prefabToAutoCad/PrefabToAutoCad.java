package com.prefabToAutoCad;

import java.util.List;

public class PrefabToAutoCad {

    List<BlocksToFill> blocksToFill;

    public PrefabToAutoCad (List<BlocksToFill> blocks) {
        this.blocksToFill = blocks;
    }

    public List createAutoCadPointsList() {
        for (BlocksToFill block : blocksToFill) {

        }
    }
}
