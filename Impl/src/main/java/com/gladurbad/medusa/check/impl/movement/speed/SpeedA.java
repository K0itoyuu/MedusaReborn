package com.gladurbad.medusa.check.impl.movement.speed;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.MathUtil;
import com.gladurbad.medusa.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Speed (A)",description = "Checks for horizontal friction.")
public class SpeedA extends Check {

    public SpeedA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isPosition()) {
            final double deltaXZ = data.getPositionProcessor().getDeltaXZ();
            final double lastDeltaXZ = data.getPositionProcessor().getLastDeltaXZ();

            final double prediction = lastDeltaXZ * 0.91F + (data.getActionProcessor().isSprinting() ? 0.026 : 0.02);
            final double difference = deltaXZ - prediction;

            final boolean invalid = difference > 1e-12 &&
                    data.getPositionProcessor().getAirTicks() > 2 &&
                    !data.getPositionProcessor().isFlying() &&
                    !data.getPositionProcessor().isNearVehicle();

            debug("diff=" + difference);
            if (invalid) {
                if ((buffer += buffer < 100 ? 5 : 0) > 40) {
                    fail(String.format("diff=%.4f", difference));
                }
            } else {
                buffer = Math.max(buffer - 1, 0);
            }
        }
    }
}
