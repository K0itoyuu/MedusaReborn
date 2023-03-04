package com.gladurbad.medusa.manager;

import com.gladurbad.medusa.listener.BukkitEventListener;
import com.gladurbad.medusa.listener.ClientBrandListener;
import com.gladurbad.medusa.listener.JoinQuitListener;
import com.gladurbad.medusa.listener.NetworkListener;

public class ListenerCenter {
    public static ClientBrandListener clientBrandListener;
    public static BukkitEventListener bukkitEventListener;
    public static JoinQuitListener joinQuitListener;
    public static NetworkListener networkListener;
}
