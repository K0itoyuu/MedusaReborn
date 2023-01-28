package com.gladurbad.medusa.check.impl.player.inventory;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;

@CheckInfo(name = "Inventory (C)",description = "Checks for attacking in inventory")
public class InventoryC extends Check {

    public InventoryC(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isUseEntity()) {
            WrappedPacketInUseEntity wrapped = new WrappedPacketInUseEntity(packet.getRawPacket());
            if (data.getActionProcessor().isInventory()) {
                wrapped.setTarget(null);
                fail();
            }
        }
    }
}
