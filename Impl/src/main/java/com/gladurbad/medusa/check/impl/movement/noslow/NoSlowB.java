package com.gladurbad.medusa.check.impl.movement.noslow;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "NoSlow (B)",description = "Blocking & Sprinting")
public class NoSlowB extends Check {

    public NoSlowB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (data.getActionProcessor().isSprinting() && data.getActionProcessor().isBlocking()) {
            boolean invalid = data.getActionProcessor().getSprintingTicks() > 8 && data.getPositionProcessor().getFastBlockTicks() > 8;
            if (invalid) {
                buffer += 1;
            }
            if (buffer >= 5) {
                fail("SprintingTicks: " + data.getActionProcessor().getSprintingTicks() + ", BlockTicks: " + data.getPositionProcessor().getFastBlockTicks());
                buffer = 0;
            }
        }
    }
}
