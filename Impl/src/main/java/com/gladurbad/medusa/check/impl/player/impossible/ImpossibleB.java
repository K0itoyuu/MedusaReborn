package com.gladurbad.medusa.check.impl.player.impossible;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.play.in.blockplace.WrappedPacketInBlockPlace;
import io.github.retrooper.packetevents.utils.player.ClientVersion;
import io.github.retrooper.packetevents.utils.vector.Vector3f;
import org.bukkit.inventory.ItemStack;

@CheckInfo(name = "Impossible (B)",description = "HeldItem Spoof")
public class ImpossibleB extends Check {

    public ImpossibleB(PlayerData data) {
        super(data);
    }
    @Override
    public void handle(Packet packet) {
        if (packet.isBlockPlace()) {
            WrappedPacketInBlockPlace wrapper = new WrappedPacketInBlockPlace(packet.getRawPacket());

            Vector3f blockPos = wrapper.getCursorPosition();

            if (isSword(wrapper.getItemStack()) && data.getClientVersion().isOlderThanOrEquals(ClientVersion.v_1_8)) {
                buffer++;
            }

            if (blockPos.x == 0.0 && blockPos.y == 0.0 && blockPos.z == 0.0 && isSword(wrapper.getItemStack())) {
                buffer = 0;
            }

            if (buffer>=3 && isSword(wrapper.getItemStack())) {
                fail();
            }

            debug("ItemStack: " + wrapper.getItemStack().getType().name());
            debug("blockPos: " + blockPos.x + " " + blockPos.y + " " + blockPos.z);
        }
    }

    private boolean isSword(ItemStack itemStack) {
        return itemStack.getType().name().contains("SWORD");
    }
}
