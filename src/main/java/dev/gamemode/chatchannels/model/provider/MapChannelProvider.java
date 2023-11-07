package dev.gamemode.chatchannels.model.provider;

import dev.gamemode.chatchannels.model.channel.Channel;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MapChannelProvider implements ChannelProvider {

  private final Map<String, Channel> channels;

  @Override
  public Optional<Channel> getChannel(String name) {
    return Optional.ofNullable(channels.get(name));
  }

  @Override
  public Collection<Channel> getChannels() {
    return channels.values();
  }
}
