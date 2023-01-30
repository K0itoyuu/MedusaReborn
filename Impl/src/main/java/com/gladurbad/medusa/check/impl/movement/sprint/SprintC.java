package com.gladurbad.medusa.check.impl.movement.sprint;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.MathUtil;
import com.gladurbad.medusa.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Sprint (C)",description = "Sprint Spoof")
public class SprintC extends Check {

    public SprintC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            Player player = data.getPlayer();
            if (!data.getActionProcessor().isSprinting() && data.getCombatProcessor().getHitTicks() > 10) {
                double diff = MathUtil.getDiff(data.getPositionProcessor());
                double maxSpeed = (0.245D + ((0.245D * 0.4D) * PlayerUtil.getPotionLevel(player, PotionEffectType.SPEED)));
                debug("diff:" + diff + ", ms:" + maxSpeed);
                if (diff > maxSpeed) {
                    buffer += 0.75;
                }
                if (buffer >= 4.0) {
                    fail("dxz:" + diff);
                    buffer = 0;
                }
            } else {
                buffer = Math.max(0,buffer - 0.25);
            }
        }
    }
}
