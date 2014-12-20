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
                        /*
                        &7[&a%s&7] &6%s&7: %s
                        Format as you would in an in game message but use %s as a variable ("%s:%s", "Mine", "Hub") would make Mine:Hub
                        Make sure the number of supplied params match the number of %s
                         */
                        pl.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', String.format("&a%s &9&l> &6%s&7: %s",
                                s.getServer().getInfo().getName(),
                                s.getName(),
                                e.getMessage()
                        ))));
            }
        }
    }
}
