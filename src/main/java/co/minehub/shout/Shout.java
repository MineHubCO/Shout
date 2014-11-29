package co.minehub.shout;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Shout extends Command implements Listener {
    public ShoutMain pasta;
    public List<String> input = new ArrayList<String>();

    public Shout(ShoutMain nethad) {
        super("shout", "shout.use");
        this.pasta = nethad;
        this.pasta.getProxy().getPluginManager().registerCommand(this.pasta, this);
        this.pasta.getProxy().getPluginManager().registerListener(this.pasta, this);
    }

    public void execute(CommandSender s, String[] args) {
        if (!this.input.contains(s.getName())) {
            this.input.add(s.getName());
            s.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "You raise your voice."));
            return;
        }

        this.input.remove(s.getName());
        s.sendMessage(new TextComponent(ChatColor.DARK_RED + "You use your inside voice."));
    }

    @EventHandler
    public void onPlayerChat(ChatEvent e) {
        if ((e.getSender() instanceof ProxiedPlayer)) {
            ProxiedPlayer s = (ProxiedPlayer) e.getSender();
            if ((this.input.contains(s.getName())) && (!e.isCommand()) && (s.hasPermission("shout.use"))) {
                e.setCancelled(true);
                for (ProxiedPlayer pl : this.pasta.getProxy().getPlayers())
                    if (pl.hasPermission("shout.use"))
                        pl.sendMessage(new TextComponent(ChatColor.GRAY + "[" + ChatColor.GREEN + s.getServer().getInfo().getName() + ChatColor.GRAY + "] " + ChatColor.GOLD + s.getName() + ChatColor.GRAY + ": " + e.getMessage()));
            }
        }
    }
}
