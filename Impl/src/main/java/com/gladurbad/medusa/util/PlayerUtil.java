package com.gladurbad.medusa.util;

import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.data.processor.PositionProcessor;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.utils.player.ClientVersion;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
public final class PlayerUtil {

    public boolean isOnGround(PlayerData data) {
        return data.getPositionProcessor().getY() % 0.015625 == 0.0;
    }

    public List<Block> getNearBlocks(PlayerData data) {
        return data.getPositionProcessor().getBoundingBox().expand(1, 1, 1).getBlocks(data.getPlayer().getWorld());
    }

    public List<Block> getNearBlocks(Player player,int expand) {
        List<Block> blocks = new ArrayList<>();
        int minX = player.getLocation().getBlockX() - expand;
        int maxX = player.getLocation().getBlockX() + expand;

        int minZ = player.getLocation().getBlockZ() - expand;
        int maxZ = player.getLocation().getBlockZ() - expand;

        for (int x=minX;x<=maxX;x=maxX-minX) {
            for (int z=minZ;z<=maxZ;z=maxZ-minZ) {
                Block block = new Location(player.getWorld(),x,player.getLocation().getBlockY(),z).getBlock();
                if (!block.isEmpty()) blocks.add(block);
            }
        }

        return blocks;
    }

    public double maxMotionY(Player player) {
        double effectSpeed = 0.24D * getPotionLevel(player,PotionEffectType.JUMP);
        return 0.42 + effectSpeed;
    }

    public double maxSpeed(Player player) {
        double d = 0.75;
        double e = 0.5D * getPotionLevel(player,PotionEffectType.JUMP);
        return d + e;
    }

    public double maxGroundSpeed(Player player) {
        double d = 0.3;
        double e = 0.16D * getPotionLevel(player,PotionEffectType.JUMP);
        return d + e;
    }

    public ClientVersion getClientVersion(final Player player) {
        return PacketEvents.get().getPlayerUtils().getClientVersion(player);
    }

    public int getPing(final Player player) {
        return PacketEvents.get().getPlayerUtils().getPing(player);
    }

    public int getDepthStriderLevel(final Player player) {
        if (player.getInventory().getBoots() != null && !ServerUtil.isLowerThan1_8()) {
            return player.getInventory().getBoots().getEnchantmentLevel(Enchantment.DEPTH_STRIDER);
        }
        return 0;
    }

    public int getPotionLevel(final Player player, final PotionEffectType effect) {
        final int effectId = effect.getId();

        if (!player.hasPotionEffect(effect)) return 0;

        return player.getActivePotionEffects().stream().filter(potionEffect -> potionEffect.getType().getId() == effectId).map(PotionEffect::getAmplifier).findAny().orElse(0) + 1;
    }

    public boolean shouldCheckJesus(final PlayerData data) {
        final boolean onWater = data.getPositionProcessor().isCollidingAtLocation(
                -0.001,
                material -> material.toString().contains("WATER"),
                PositionProcessor.CollisionType.ANY
        );

        final boolean hasEdgeCases = data.getPositionProcessor().isCollidingAtLocation(
                -0.001,
                material -> material == Material.CARPET || material == Material.WATER_LILY || material.isSolid(),
                PositionProcessor.CollisionType.ANY
        );

        return onWater && !hasEdgeCases;
    }

    /**
     * Gets the block in the given distance, If the block is null we immediately break to avoid issues
     *
     * @param player The player
     * @param distance The distance
     * @return The block in the given distance, otherwise null
     */
    public Block getLookingBlock(final Player player, final int distance) {
        final Location loc = player.getEyeLocation();

        final Vector v = loc.getDirection().normalize();

        for (int i = 1; i <= distance; i++) {
            loc.add(v);

            Block b = loc.getBlock();
            /*
            I'd recommend moving fiona's getBlock method in a seperate class
            So we can use it in instances like this.
             */
            if (b == null) break;

            if (b.getType() != Material.AIR) return b;
        }

        return null;
    }

    /**
     * Bukkit's getNearbyEntities method looks for all entities in all chunks
     * This is a lighter method and can also be used Asynchronously since we won't load any chunks
     *
     * @param location The location to scan for nearby entities
     * @param radius   The radius to expand
     * @return The entities within that radius
     * @author Nik
     */
    public List<Entity> getEntitiesWithinRadius(final Location location, final double radius) {

        final double expander = 16.0D;

        final double x = location.getX();
        final double z = location.getZ();

        final int minX = (int) Math.floor((x - radius) / expander);
        final int maxX = (int) Math.floor((x + radius) / expander);

        final int minZ = (int) Math.floor((z - radius) / expander);
        final int maxZ = (int) Math.floor((z + radius) / expander);

        final World world = location.getWorld();

        List<Entity> entities = new LinkedList<>();

        for (int xVal = minX; xVal <= maxX; xVal++) {

            for (int zVal = minZ; zVal <= maxZ; zVal++) {

                if (!world.isChunkLoaded(xVal, zVal)) continue;

                for (Entity entity : world.getChunkAt(xVal, zVal).getEntities()) {
                    //We have to do this due to stupidness
                    if (entity == null) continue;

                    //Make sure the entity is within the radius specified
                    if (entity.getLocation().distanceSquared(location) > radius * radius) continue;

                    entities.add(entity);
                }
            }
        }

        return entities;
    }

    public boolean isNearVehicle(final Player player) {
        for (final Entity entity : getEntitiesWithinRadius(player.getLocation(), 2)) {
            if (entity instanceof Vehicle) return true;
        }

        return false;
    }
}