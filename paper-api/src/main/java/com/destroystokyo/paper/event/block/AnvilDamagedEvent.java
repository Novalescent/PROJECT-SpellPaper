package com.destroystokyo.paper.event.block;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Called when an anvil is damaged from being used
 */
@NullMarked
public class AnvilDamagedEvent extends InventoryEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private DamageState damageState;
    private boolean cancelled;

    @ApiStatus.Internal
    public AnvilDamagedEvent(final InventoryView inventory, final @Nullable BlockData blockData) {
        super(inventory);
        this.damageState = DamageState.getState(blockData);
    }

    @Override
    public AnvilInventory getInventory() {
        return (AnvilInventory) super.getInventory();
    }

    /**
     * Gets the new state of damage on the anvil
     *
     * @return Damage state
     */
    public DamageState getDamageState() {
        return this.damageState;
    }

    /**
     * Sets the new state of damage on the anvil
     *
     * @param damageState Damage state
     */
    public void setDamageState(final DamageState damageState) {
        this.damageState = damageState;
    }

    /**
     * Gets if anvil is breaking on this use
     *
     * @return {@code true} if breaking
     */
    public boolean isBreaking() {
        return this.damageState == DamageState.BROKEN;
    }

    /**
     * Sets if anvil is breaking on this use
     *
     * @param breaking {@code true} if breaking
     */
    public void setBreaking(final boolean breaking) {
        if (breaking) {
            this.damageState = DamageState.BROKEN;
        } else if (this.damageState == DamageState.BROKEN) {
            this.damageState = DamageState.DAMAGED;
        }
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Represents the amount of damage on an anvil block
     */
    public enum DamageState {
        FULL(Material.ANVIL),
        CHIPPED(Material.CHIPPED_ANVIL),
        DAMAGED(Material.DAMAGED_ANVIL),
        BROKEN(Material.AIR);

        private final Material material;

        DamageState(final Material material) {
            this.material = material;
        }

        /**
         * Get block material of this state
         *
         * @return Material
         */
        public Material getMaterial() {
            return this.material;
        }

        /**
         * Get damaged state by block data
         *
         * @param blockData Block data
         * @return DamageState
         * @throws IllegalArgumentException If non anvil block data is given
         */
        public static DamageState getState(final @Nullable BlockData blockData) {
            return blockData == null ? BROKEN : getState(blockData.getMaterial());
        }

        /**
         * Get damaged state by block material
         *
         * @param material Block material
         * @return DamageState
         * @throws IllegalArgumentException If non anvil material is given
         */
        public static DamageState getState(final @Nullable Material material) {
            if (material == null) {
                return BROKEN;
            }
            for (final DamageState state : values()) {
                if (state.getMaterial() == material) {
                    return state;
                }
            }
            throw new IllegalArgumentException("Material is not an anvil state");
        }
    }
}
