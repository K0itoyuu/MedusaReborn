package com.gladurbad.medusa.check.impl.player.client;

import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.check.Check;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.packet.Packet;
import com.gladurbad.medusa.util.anticheat.AlertUtil;

@CheckInfo(name = "Client (E)",description = "IP Risk")
public class ClientE extends Check {

    public ClientE(PlayerData data) {
        super(data);
    }

    private boolean alerted = false;

    @Override
    public void handle(Packet packet) {
        if (alerted) return;

        AlertUtil.handleAlert(this,data,"Host:" + data.getPlayer().getAddress().getHostName());
        alerted = true;
    }
}
