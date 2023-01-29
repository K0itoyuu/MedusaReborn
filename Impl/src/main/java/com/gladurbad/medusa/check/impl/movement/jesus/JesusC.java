package com.gladurbad.medusa.check.impl.movement.jesus;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.MathUtil;
import com.gladurbad.medusa.util.PlayerUtil;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Jesus (C)",description = "swimming speed")
public class JesusC extends Check {

    public JesusC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            if (data.getPositionProcessor().isInLiquid() && !data.getPlayer().isOnGround()) {
                double diff = MathUtil.getDiff(data.getPositionProcessor());
                double maxSpeed = (0.282D + ((0.282D * 0.22D) * PlayerUtil.getPotionLevel(data.getPlayer(), PotionEffectType.SPEED))) / 2.05D;
                if (diff > maxSpeed) {
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
