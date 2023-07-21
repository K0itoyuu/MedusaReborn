package com.gladurbad.medusa.check.impl.movement.noslow;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;

@CheckInfo(name = "NoSlow (A)",description = "Checks if the player no slow blocking/eating.")
public class NoSlowA extends Check {

    public NoSlowA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {

    }
}
