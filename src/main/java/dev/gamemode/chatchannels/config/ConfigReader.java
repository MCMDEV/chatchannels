package dev.gamemode.chatchannels.config;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.file.FileNotFoundAction;
import dev.gamemode.chatchannels.model.channel.Channel;
import dev.gamemode.chatchannels.model.channel.SetCollectionChannel;
import dev.gamemode.chatchannels.model.provider.ChannelProvider;
import dev.gamemode.chatchannels.model.provider.MapChannelProvider;
import dev.gamemode.chatchannels.renderer.ChannelRenderer;
import dev.gamemode.chatchannels.renderer.ConfiguredRenderer;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ConfigReader {

  private FileConfig fileConfig;

  public void load(File dataFolder) {
    if (!dataFolder.exists()) {
      dataFolder.mkdirs();
    }

    File filePath = new File(dataFolder, "config.yml");
    this.fileConfig = FileConfig.builder(filePath)
        .onFileNotFound(
            FileNotFoundAction.copyData(ConfigReader.class.getResourceAsStream("/config.yml")))
        .charset(StandardCharsets.UTF_8)
        .build();
    this.fileConfig.load();
  }

  public ChannelProvider configureProvider() {
    String defaultRenderer = fileConfig.get("channel-renderer");
    Config channelsConfig = fileConfig.get("channels");

    return new MapChannelProvider(
        channelsConfig.entrySet().stream().map(entry -> {
          Config channelConfig = channelsConfig.get(entry.getKey());
          return configureChannel(entry.getKey(), channelConfig, defaultRenderer);
        }).collect(Collectors.toMap(Channel::getName, o -> o)
        ));
  }

  public Channel configureChannel(String key, Config config, String defaultRenderer) {
    Component displayName = MiniMessage.miniMessage().deserialize(config.get("display-name"));
    ChannelRenderer channelRenderer =
        new ConfiguredRenderer(config.getOrElse("channel-renderer", defaultRenderer));

    return new SetCollectionChannel(key, displayName, new HashSet<>(), channelRenderer);
  }

}
