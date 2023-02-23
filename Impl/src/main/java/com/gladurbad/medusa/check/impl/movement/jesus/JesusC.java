package com.gladurbad.medusa.check.impl.movement.jesus;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.exempt.type.ExemptType;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.MathUtil;
import com.gladurbad.medusa.util.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

@CheckInfo(name = "Jesus (C)",description = "Abnormal swimming speed")
public class JesusC extends Check {

    public JesusC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            if (data.getPositionProcessor().isInLiquid() && !data.getPlayer().isOnGround()) {
                final List<Block> blocks = data.getPositionProcessor().getBoundingBox().expand(1, 1, 1).getBlocks(
                        data.getPlayer().getWorld()
                );

                boolean isExempt = isExempt(ExemptType.FLYING,ExemptType.VELOCITY);

                if (isExempt) return;

                final boolean check = blocks.stream().noneMatch(block -> block.getType() != Material.AIR && !block.getType().toString().contains("WATER"))
                        && blocks.stream().anyMatch(block -> block.getType().toString().contains("WATER"));
                double diff = MathUtil.getDiff(data.getPositionProcessor());
                double maxSpeed = (0.282D + ((0.282D * 0.22D) * PlayerUtil.getPotionLevel(data.getPlayer(), PotionEffectType.SPEED))) / 2.05D;
                if (diff > maxSpeed && check) {
                    buffer += 1.0;
                }
                if (buffer >= 3.0) {
                    fail("d:" + diff + ", ms:" + maxSpeed);
                    buffer = 0;
                }
            }
        }
    }
}
