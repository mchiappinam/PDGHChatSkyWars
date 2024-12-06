package me.mchiappinam.pdghchatskywars;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class Main extends JavaPlugin implements Listener {
	protected static Economy econ = null;
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		if(!setupEconomy()) {
			getLogger().warning("ERRO: Vault (Economia) nao encontrado!");
			getLogger().warning("Desativando o plugin...");
			getServer().getPluginManager().disablePlugin(this);
        }
		getServer().getConsoleSender().sendMessage("§3[PDGHChatSkyWars] §2ativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHChatSkyWars] §2Acesse: http://pdgh.com.br/");
	}

	public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHChatSkyWars] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHChatSkyWars] §2Acesse: http://pdgh.com.br/");
	  }

	@EventHandler(priority=EventPriority.HIGHEST)
	private void onChat(ChatMessageEvent e) {
		if (e.getTags().contains("dinheiro")) {
			if(econ.getBalance(e.getSender().getName())==0)
				e.setTagValue("dinheiro", "§e(§f"+econ.getBalance(e.getSender().getName())+"§e)");
			else if(econ.getBalance(e.getSender().getName())>=1)
				e.setTagValue("dinheiro", "§e(§a+"+econ.getBalance(e.getSender().getName())+"§e)");
			else if(econ.getBalance(e.getSender().getName())<=-1)
				e.setTagValue("dinheiro", "§e(§c"+econ.getBalance(e.getSender().getName())+"§e)");
		}
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}