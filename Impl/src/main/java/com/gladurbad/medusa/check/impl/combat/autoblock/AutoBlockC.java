package com.gladurbad.medusa.check.impl.combat.autoblock;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import org.bukkit.ChatColor;

@CheckInfo(name = "AutoBlock (C)",description = "Interact AutoBlock",experimental = true)
public class AutoBlockC extends Check {

    private boolean isInteracted = false;

    public AutoBlockC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isUseEntity()) {
            WrappedPacketInUseEntity wrapper = new WrappedPacketInUseEntity(packet.getRawPacket());
            if (wrapper.getAction().equals(WrappedPacketInUseEntity.EntityUseAction.INTERACT)) {
                isInteracted = true;
            }

            if (data.getPlayer().isBlocking() && data.getCombatProcessor().getHitTicks() == 0) {
                if (wrapper.getAction().equals(WrappedPacketInUseEntity.EntityUseAction.ATTACK)) {
                    if (!isInteracted) {
                        fail("b:" + data.getPlayer().isBlocking() + ", i:" + isInteracted);
                    }
                    isInteracted = false;
                }
            }
        }
    }
}
