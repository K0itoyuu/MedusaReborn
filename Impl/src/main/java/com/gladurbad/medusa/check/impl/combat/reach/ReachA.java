package com.gladurbad.medusa.check.impl.combat.reach;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.config.ConfigValue;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.MathUtil;
import com.gladurbad.medusa.util.PlayerUtil;
import com.gladurbad.medusa.util.type.BoundingBox;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import io.github.retrooper.packetevents.utils.vector.Vector3d;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@CheckInfo(name = "Reach (A)",description = "Check attacking distance",experimental = true)
public class ReachA extends Check {

    public ReachA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isUseEntity()) {
            Entity target = data.getCombatProcessor().getTarget();
            Entity lastTarget = data.getCombatProcessor().getLastTarget();
            if (target != lastTarget) return;
            if (data.getCombatProcessor().getHitTicks() == 0) {
                double distance = (data.getCombatProcessor().getDistance() + data.getCombatProcessor().getLastDistance()) / 2D;
                double offset = (double) data.getTransactionProcessor().getServerTransactionPing() / 1000D;
                offset = Math.abs(offset);
                offset = Math.min(offset, 0.4);
                double maxReach = 3.15 + offset;
                debug("d:" + data.getCombatProcessor().getDistance() + ", ld:" + data.getCombatProcessor().getLastDistance());

                //狗都觉得不可能
                if (distance > 5.5) return;

                if (distance > maxReach) {
                    buffer += 1.0;
                }

                if (buffer > 2.0) {
                    fail("Distance: " + distance + ", limit: " + maxReach);
                    buffer = 0;
                }
            }
        }
    }

}
