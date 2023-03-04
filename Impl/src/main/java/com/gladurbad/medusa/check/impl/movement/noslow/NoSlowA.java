package com.gladurbad.medusa.check.impl.movement.noslow;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.MathUtil;
import com.gladurbad.medusa.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "NoSlow (A)",description = "Blocking Movement Speed.")
public class NoSlowA extends Check {
    public NoSlowA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        Player player = data.getPlayer();
        if (!data.getPositionProcessor().isInAir() && player.isBlocking() && !data.getPositionProcessor().isTeleporting() && data.getPositionProcessor().getFastBlockTicks() > 5 && data.getVelocityProcessor().getBypassTicks() == 0) {
            double diff = MathUtil.getDiff(data.getPositionProcessor());
            double maxSpeed = (0.282D + ((0.282D * 0.22D) * PlayerUtil.getPotionLevel(player, PotionEffectType.SPEED))) / 2D;

            if (diff >= maxSpeed) {
                buffer+=1;
            }

            if (buffer>=20) {
                setBack();
                fail("MovementSpeed: " + diff + ", BlockTicks: " + data.getPositionProcessor().getBlockTicks());
                buffer = 0;
            }

        } else {
            buffer = Math.max(buffer-0.55,0);
        }
    }
}
