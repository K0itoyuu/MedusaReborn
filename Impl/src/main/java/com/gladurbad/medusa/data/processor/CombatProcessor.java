package com.gladurbad.medusa.data.processor;

import com.gladurbad.medusa.util.MathUtil;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import lombok.Getter;
import com.gladurbad.medusa.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Entity;


@Getter
public final class CombatProcessor {

    private final PlayerData data;

    private int hitTicks, swings, hits, currentTargets;

    private double hitMissRatio, distance,lastDistance;

    private Entity target, lastTarget;

    public CombatProcessor(final PlayerData data) {
        this.data = data;
    }

    public void handleUseEntity(final WrappedPacketInUseEntity wrapper) {
        if (wrapper.getAction() != WrappedPacketInUseEntity.EntityUseAction.ATTACK || wrapper.getEntity() == null) {
            return;
        }
        data.getRotationProcessor().setAttackPitch(data.getRotationProcessor().getPitch());
        data.getRotationProcessor().setAttackYaw(data.getRotationProcessor().getYaw());

        lastTarget = target == null ? wrapper.getEntity() : target;
        target = wrapper.getEntity();

        lastDistance = distance;
        distance = Math.abs(MathUtil.getDistance(data.getPlayer(), target) - 0.15);
        //distance = data.getPlayer().getLocation().toVector().setY(0).distance(target.getLocation().toVector().setY(0)) - .42;
        ++hits;

        hitTicks = 0;

        if (target != lastTarget) {
            ++currentTargets;
        }
    }

    public void handleArmAnimation() {
        ++swings;
    }

    public void handleFlying() {
        ++hitTicks;
        currentTargets = 0;

        if (swings > 1) {
            hitMissRatio = ((double) hits / (double) swings) * 100;
        }
        if (hits > 100 || swings > 100) {
            hits = swings = 0;
        }
    }
}
