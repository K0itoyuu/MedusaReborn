package com.gladurbad.medusa.check.impl.movement.jesus;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

@CheckInfo(name = "Jesus (B)",description = "always walk on water")
public class JesusB extends Check {

    public JesusB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition() || packet.isPosLook()) {
            final List<Block> blocks = data.getPositionProcessor().getBoundingBox().expand(1, 1, 1).getBlocks(
                    data.getPlayer().getWorld()
            );
            final boolean check = blocks.stream().noneMatch(block -> block.getType() != Material.AIR && !block.getType().toString().contains("WATER"))
                    && blocks.stream().anyMatch(block -> block.getType().toString().contains("WATER"));
            if (check) {
                boolean invalid = data.getPositionProcessor().isInLiquid() && data.getPositionProcessor().isMathematicallyOnGround();
                if (invalid) {
                    buffer += 1.0;
                }

                if (buffer >= 5.0) {
                    fail();
                    buffer = 2.5;
                }
            }
        }
    }
}
