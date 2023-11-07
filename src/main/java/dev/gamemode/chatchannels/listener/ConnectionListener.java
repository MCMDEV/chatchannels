package dev.gamemode.chatchannels.listener;

import dev.gamemode.chatchannels.model.channel.MembershipChannel;
import dev.gamemode.chatchannels.model.provider.ChannelProvider;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class ConnectionListener implements Listener {

  private final ChannelProvider channelProvider;

  @EventHandler
  private void onJoin(PlayerJoinEvent event) {
    channelProvider.getChannels()
        .stream()
        .filter(channel -> channel.canSee(event.getPlayer()))
        .filter(channel -> channel instanceof MembershipChannel)
        .map(channel -> (MembershipChannel) channel)
        .filter(membershipChannel -> membershipChannel.shouldAutojoin(event.getPlayer()))
        .forEach(channel -> channel.join(event.getPlayer()));
  }

  @EventHandler
  private void onQuit(PlayerQuitEvent event) {
    channelProvider.getChannels()
        .stream()
        .filter(channel -> channel instanceof MembershipChannel)
        .map(channel -> (MembershipChannel) channel)
        .filter(channel -> channel.isMember(event.getPlayer()))
        .forEach(channel -> channel.leave(event.getPlayer()));
  }

}
