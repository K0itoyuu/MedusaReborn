package com.gladurbad.medusa.check.impl.movement.noslow;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.MathUtil;
import com.gladurbad.medusa.util.PlayerUtil;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "NoSlow (C)",description = "Advanced",experimental = true)
public class NoSlowC extends Check {
    public NoSlowC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getJoinTime() < 5000) return;

        if (packet.isFlying()) {
            if (data.getPositionProcessor().getFastBlockTicks() >= 3) {
                if (!data.getVelocityProcessor().isVerifyVelocity()) {
                    double diff = MathUtil.getDiff(data.getPositionProcessor());
                    double maxSpeed = (0.282D + ((0.282D * 0.22D) * PlayerUtil.getPotionLevel(data.getPlayer(), PotionEffectType.SPEED)));

                    if (data.getPositionProcessor().getFastBlockTicks() % 2 == 0) {
                        debug("Speed: " + diff + ", MaxSpeed: " + (maxSpeed - maxSpeed * 0.12f));
                    }

                    if (diff > maxSpeed - maxSpeed * 0.12) {
                        buffer += 0.75f;
                    } else {
                        buffer = Math.max(0D,buffer - 0.25);
                    }

                    if (buffer >= 3) {
                        fail("speed: " + diff);
                        buffer = 0;
                    }
                }
            }
        }
    }
}
