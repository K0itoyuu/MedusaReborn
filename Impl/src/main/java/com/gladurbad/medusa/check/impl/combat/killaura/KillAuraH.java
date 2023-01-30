package com.gladurbad.medusa.check.impl.combat.killaura;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "KillAura (H)",description = "Checks if the player attacked rotation")
public class KillAuraH extends Check {
    public KillAuraH(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {
            if (Double.isNaN(data.getRotationProcessor().getAttackYaw())) return;
            if (data.getCombatProcessor().getHitTicks() == 1) {
                double diff = Math.abs(data.getRotationProcessor().getAttackYaw() - data.getRotationProcessor().getYaw());
                if (diff > 20.0) {
                    buffer += 1.0;
                } else {
                    buffer = Math.max(0,buffer - 0.1);
                }
                if (buffer >= 3.0) {
                    fail("Diff: " + diff);
                    buffer = 0;
                }
            }
        }
    }
}
