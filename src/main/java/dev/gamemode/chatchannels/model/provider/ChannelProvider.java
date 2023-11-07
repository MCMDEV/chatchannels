package dev.gamemode.chatchannels.model.provider;

import dev.gamemode.chatchannels.model.channel.Channel;
import java.util.Collection;
import java.util.Optional;

public interface ChannelProvider {

  Optional<Channel> getChannel(String name);

  Collection<Channel> getChannels();
}
