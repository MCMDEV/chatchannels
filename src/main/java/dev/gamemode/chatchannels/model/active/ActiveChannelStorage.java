package dev.gamemode.chatchannels.model.active;

import dev.gamemode.chatchannels.model.channel.Channel;
import java.util.Optional;
import java.util.UUID;

public interface ActiveChannelStorage {

  Optional<Channel> getActiveChannel(UUID uuid);

  void switchChannel(UUID uuid, Channel channel);

  void deactivate(UUID uniqueId);
}
