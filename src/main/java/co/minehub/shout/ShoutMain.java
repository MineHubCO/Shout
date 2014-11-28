package co.minehub.shout;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class ShoutMain extends Plugin
        implements Listener {

    public void onEnable() {
        getProxy().getLogger().log(Level.INFO, "Shout may or may not enable!");
        new Shout(this);
        getProxy().getLogger().log(Level.INFO, "Shout is enabled!");
    }

    public void onDisable() {
        getProxy().getLogger().log(Level.INFO, "Shout is disabling!");
    }
}
