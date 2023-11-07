package dev.gamemode.chatchannels.model.channel;

import org.bukkit.entity.Player;

public interface MembershipChannel extends Channel {
  boolean shouldAutojoin(Player player);

  boolean canJoin(Player player);

  void join(Player player);

  boolean isMember(Player player);

  void leave(Player player);

}
