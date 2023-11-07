package dev.gamemode.chatchannels.command;

import dev.gamemode.chatchannels.model.channel.Channel;
import dev.gamemode.chatchannels.model.channel.MembershipChannel;
import dev.gamemode.chatchannels.model.provider.ChannelProvider;
import java.util.Collections;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TogglechannelCommand extends BukkitCommand {

  private final ChannelProvider channelProvider;

  public TogglechannelCommand(ChannelProvider channelProvider) {
    super("togglechannel");
    this.channelProvider = channelProvider;

    setPermission("chatchannels.togglechannel");
  }

  @Override
  public boolean execute(@NotNull CommandSender sender, @NotNull String label,
                         @NotNull String[] args) {
    if (!(sender instanceof Player player)) {
      return true;
    }
    if (!testPermission(sender)) {
      return true;
    }

    if (args.length == 0) {
      return false;
    }

    String channelName = args[0];

    channelProvider.getChannel(channelName).ifPresentOrElse(channel -> {
      if (!(channel instanceof MembershipChannel membershipChannel)) {
        player.sendMessage(
            Component.text("This channel cannot be joined.", NamedTextColor.GOLD)
        );
        return;
      }

      handleMembershipSwitch(player, membershipChannel);
    }, () -> {
      player.sendMessage(
          Component.text("Channel not found.", NamedTextColor.GOLD)
      );
    });

    return true;
  }

  private void handleMembershipSwitch(Player player, MembershipChannel membershipChannel) {
    if (membershipChannel.isMember(player)) {
      membershipChannel.leave(player);
      player.sendMessage(
          Component.text("You have left the channel.", NamedTextColor.GOLD)
      );
    } else {
      if (!membershipChannel.canJoin(player)) {
        player.sendMessage(
            Component.text("You cannot join this channel.", NamedTextColor.GOLD)
        );
        return;
      }

      membershipChannel.join(player);
      player.sendMessage(
          Component.text("You have joined the channel.", NamedTextColor.GOLD)
      );
    }
  }

  @Override
  public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias,
                                           @NotNull String[] args) throws IllegalArgumentException {
    if (!(sender instanceof Player player)) {
      return Collections.emptyList();
    }
    if (!testPermission(sender)) {
      return Collections.emptyList();
    }

    return channelProvider.getChannels().stream()
        .filter(channel -> channel.canSee(player))
        .map(Channel::getName)
        .toList();
  }
}
