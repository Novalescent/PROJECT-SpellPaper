package com.destroystokyo.paper.event.block;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BlockFallingParticleEvent extends Event implements Cancellable {

	private static final HandlerList HANDLER_LIST = new HandlerList();

	private final Block mBlock;
	private final Location mLocation;
	public BlockFallingParticleEvent(Block block) {
		mBlock = block;
		mLocation = block.getLocation();
	}

	public Block getBlock() {
		return mBlock;
	}

	public Location getLocation() {
		return mLocation;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {

	}

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
}
