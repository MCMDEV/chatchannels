package dev.gamemode.chatchannels;

import dev.gamemode.chatchannels.command.ChatchannelsCommand;
import dev.gamemode.chatchannels.command.SendchannelCommand;
import dev.gamemode.chatchannels.command.SwitchchannelCommand;
import dev.gamemode.chatchannels.command.TogglechannelCommand;
import dev.gamemode.chatchannels.config.ConfigReader;
import dev.gamemode.chatchannels.listener.ChatListener;
import dev.gamemode.chatchannels.listener.ConnectionListener;
import dev.gamemode.chatchannels.model.active.ActiveChannelStorage;
import dev.gamemode.chatchannels.model.active.MapActiveChannelStorage;
import dev.gamemode.chatchannels.model.provider.ChannelProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatchannelsPlugin extends JavaPlugin {

  private ConfigReader configReader;
  private ChannelProvider channelProvider;
  private ActiveChannelStorage activeChannelStorage;

  @Override
  public void onEnable() {
    loadConfig();
    createChannelSystems();
    registerCommands();
    registerListeners();
  }

  private void createChannelSystems() {
    this.channelProvider = this.configReader.configureProvider();
    this.activeChannelStorage = new MapActiveChannelStorage();
  }

  private void loadConfig() {
    this.configReader = new ConfigReader();
    this.configReader.load(getDataFolder());
  }

  private void registerCommands() {
    Bukkit.getCommandMap().register("channel", new ChatchannelsCommand(channelProvider));
    Bukkit.getCommandMap().register("channel", new TogglechannelCommand(channelProvider));
    Bukkit.getCommandMap().register("channel", new SendchannelCommand(channelProvider));
    Bukkit.getCommandMap()
        .register("channel", new SwitchchannelCommand(channelProvider, activeChannelStorage));
  }

  private void registerListeners() {
    Bukkit.getPluginManager().registerEvents(new ChatListener(activeChannelStorage), this);
    Bukkit.getPluginManager().registerEvents(new ConnectionListener(channelProvider), this);
  }

  @Override
  public void onDisable() {

  }
}
