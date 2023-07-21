package com.gladurbad.medusa.command.impl;

import com.gladurbad.medusa.Medusa;
import com.gladurbad.medusa.command.CommandInfo;
import com.gladurbad.medusa.command.MedusaCommand;
import com.gladurbad.medusa.config.Config;
import com.gladurbad.medusa.data.PlayerData;
import com.gladurbad.medusa.manager.CheckManager;
import com.gladurbad.medusa.manager.ThemeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "reload",purpose = "Reload")
public class Reload extends MedusaCommand {
    @Override
    protected boolean handle(CommandSender sender, Command command, String label, String[] args) {
        Medusa.INSTANCE.getPlugin().saveDefaultConfig();
        Config.updateConfig();
        Medusa.INSTANCE.stop(Medusa.INSTANCE.getPlugin());
        Medusa.INSTANCE.start(Medusa.INSTANCE.getPlugin());
        Medusa.INSTANCE.getPlayerDataManager().getAllData().forEach(
                playerData -> {
                    playerData.getChecks().clear();
                    playerData.setChecks(CheckManager.loadChecks(playerData));
                }
        );
        sendMessage(sender, Config.ACCENT_ONE + "Reloaded Medusa" + Config.ACCENT_ONE + ".");
        return true;
    }
}
