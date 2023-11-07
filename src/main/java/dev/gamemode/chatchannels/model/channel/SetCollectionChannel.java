package dev.gamemode.chatchannels.model.channel;

import dev.gamemode.chatchannels.renderer.ChannelRenderer;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class SetCollectionChannel implements MembershipChannel {

  private final String name;
  private final Component displayName;
  private final Set<Player> viewers;
  private final ChannelRenderer channelRenderer;

  @Override
  public boolean shouldAutojoin(Player player) {
    return player.hasPermission("chatchannels.autojoin." + name);
  }

  @Override
  public boolean canSee(Audience audience) {
    if (audience instanceof Player player) {
      return canJoin(player);
    }
    return true;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Component getDisplayName() {
    return displayName;
  }

  @Override
  public Collection<Audience> getViewers() {
    return viewers.stream()
        .map(player -> (Audience) player)
        .collect(Collectors.toSet());
  }

  @Override
  public ChannelRenderer getRenderer() {
    return channelRenderer;
  }

  @Override
  public boolean canJoin(Player player) {
    return player.hasPermission("chatchannels.join." + name);
  }

  @Override
  public void join(Player player) {
    viewers.add(player);
  }

  @Override
  public boolean isMember(Player player) {
    return viewers.contains(player);
  }

  @Override
  public void leave(Player player) {
    viewers.remove(player);
  }
}
