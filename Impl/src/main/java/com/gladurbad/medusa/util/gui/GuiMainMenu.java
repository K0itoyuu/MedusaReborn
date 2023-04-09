package com.gladurbad.medusa.util.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiMainMenu extends ChestGui implements Listener {
    private final Player player;
    private Inventory gui;
    public GuiMainMenu(Player player) {
        this.player = player;
    }

    @Override
    public void loadGui() {
        this.gui = Bukkit.createInventory(null,27, a("&aMedusa Gui | MainMenu"));

        ItemStack check = new ItemStack(Material.BOOK,1);
        ItemMeta checkItemMeta = check.getItemMeta();
        checkItemMeta.setDisplayName(a("&aChecks"));
        check.setItemMeta(checkItemMeta);
        gui.setItem(13,check);

        player.openInventory(gui);
    }

    @EventHandler
    public void onWindowClick(InventoryClickEvent event) {
        if (event.getWhoClicked() == player && event.getClickedInventory() == gui) {
            event.setCancelled(true);

            ItemStack object = event.getCurrentItem();

            if (object.getItemMeta().getDisplayName().equalsIgnoreCase(a("&aChecks"))) {
                player.closeInventory();
                new GuiChecks(player).loadGui();
            }
        }
    }
}
