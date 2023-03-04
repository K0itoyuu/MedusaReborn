package com.gladurbad.medusa;

import com.gladurbad.medusa.listener.BukkitEventListener;
import com.gladurbad.medusa.listener.ClientBrandListener;
import com.gladurbad.medusa.listener.NetworkListener;
import com.gladurbad.medusa.listener.JoinQuitListener;
import com.gladurbad.medusa.manager.*;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import lombok.Getter;
import com.gladurbad.medusa.command.CommandManager;
import com.gladurbad.medusa.config.Config;
import com.gladurbad.medusa.packet.processor.ReceivingPacketProcessor;
import com.gladurbad.medusa.packet.processor.SendingPacketProcessor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.messaging.Messenger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public enum Medusa {

    INSTANCE;

    private MedusaPlugin plugin;

    private long startTime;

    private boolean firstStart = false;

    private final TickManager tickManager = new TickManager();
    private final ReceivingPacketProcessor receivingPacketProcessor = new ReceivingPacketProcessor();
    private final SendingPacketProcessor sendingPacketProcessor = new SendingPacketProcessor();
    private final PlayerDataManager playerDataManager = new PlayerDataManager();
    private final CommandManager commandManager = new CommandManager(this.getPlugin());
    private final ExecutorService packetExecutor = Executors.newSingleThreadExecutor();

    public void start(final MedusaPlugin plugin) {
        this.plugin = plugin;
        assert plugin != null : "Error while starting Medusa.";

        this.getPlugin().saveDefaultConfig();
        Config.updateConfig();

        CheckManager.setup();
        ThemeManager.setup();
        this.getPlayerDataManager().getAllData().clear();
        Bukkit.getOnlinePlayers().forEach(player -> this.getPlayerDataManager().add(player));

        getPlugin().saveDefaultConfig();
        getPlugin().getCommand("medusa").setExecutor(commandManager);

        tickManager.start();

        final Messenger messenger = Bukkit.getMessenger();
        if (ListenerCenter.clientBrandListener == null)
            ListenerCenter.clientBrandListener = new ClientBrandListener();
        if (firstStart) {
            messenger.registerIncomingPluginChannel(plugin, "MC|Brand", ListenerCenter.clientBrandListener);
            firstStart = true;
        }

        startTime = System.currentTimeMillis();

        if (ListenerCenter.bukkitEventListener == null)
            ListenerCenter.bukkitEventListener = new BukkitEventListener();

        if (ListenerCenter.joinQuitListener == null)
            ListenerCenter.joinQuitListener = new JoinQuitListener();

        if (ListenerCenter.networkListener == null)
            ListenerCenter.networkListener = new NetworkListener();

        Bukkit.getServer().getPluginManager().registerEvents(ListenerCenter.bukkitEventListener, plugin);
        Bukkit.getServer().getPluginManager().registerEvents(ListenerCenter.clientBrandListener, plugin);
        Bukkit.getServer().getPluginManager().registerEvents(ListenerCenter.joinQuitListener, plugin);

        PacketEvents.get().registerListener(ListenerCenter.networkListener);
    }

    public void stop(final MedusaPlugin plugin) {
        this.plugin = plugin;
        assert plugin != null : "Error while shutting down Medusa.";

        tickManager.stop();
    }
}
