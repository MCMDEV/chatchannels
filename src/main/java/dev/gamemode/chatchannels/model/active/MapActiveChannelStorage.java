package dev.gamemode.chatchannels.model.active;

import dev.gamemode.chatchannels.model.channel.Channel;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MapActiveChannelStorage implements ActiveChannelStorage {

  private final Map<UUID, Channel> channels = new ConcurrentHashMap<>();

  @Override
  public Optional<Channel> getActiveChannel(UUID uuid) {
    return Optional.ofNullable(channels.get(uuid));
  }

  @Override
  public void switchChannel(UUID uuid, Channel channel) {
    channels.put(uuid, channel);
  }

  @Override
  public void deactivate(UUID uniqueId) {
    channels.remove(uniqueId);
  }
}
