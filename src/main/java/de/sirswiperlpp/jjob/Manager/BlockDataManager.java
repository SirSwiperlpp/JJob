package de.sirswiperlpp.jjob.Manager;

import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class BlockDataManager {
    private static HashMap<Block, UUID> placedBlocks;

    public BlockDataManager() {
        this.placedBlocks = new HashMap<>();
    }

    public static void setBlockPlacedByPlayer(Block block, UUID playerUUID) {
        placedBlocks.put(block, playerUUID);
    }

    public UUID getPlayerWhoPlacedBlock(Block block) {
        return placedBlocks.get(block);
    }

    public boolean isBlockPlacedByPlayer(Block block) {
        return placedBlocks.containsKey(block);
    }
}
