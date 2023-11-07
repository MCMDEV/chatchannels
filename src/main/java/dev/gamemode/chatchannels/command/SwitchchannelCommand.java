package dev.gamemode.chatchannels.command;

import dev.gamemode.chatchannels.model.active.ActiveChannelStorage;
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

public class SwitchchannelCommand extends BukkitCommand {

  private final ChannelProvider channelProvider;
  private final ActiveChannelStorage activeChannelStorage;

  public SwitchchannelCommand(ChannelProvider channelProvider,
                              ActiveChannelStorage activeChannelStorage) {
    super("switchchannel");
    this.channelProvider = channelProvider;
    this.activeChannelStorage = activeChannelStorage;

    setPermission("chatchannels.switchchannel");
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
      activeChannelStorage.getActiveChannel(player.getUniqueId()).ifPresentOrElse(channel -> {
        activeChannelStorage.deactivate(player.getUniqueId());

        player.sendMessage(
            Component.text("You have left the channel.", NamedTextColor.GOLD)
        );
      }, () -> player.sendMessage(
          Component.text("You are not in a channel.", NamedTextColor.GOLD)
      ));
      return true;
    }

    String channelName = args[0];

    channelProvider.getChannel(channelName).ifPresentOrElse(channel -> {
      if (!(channel instanceof MembershipChannel membershipChannel)) {
        player.sendMessage(
            Component.text("This channel cannot be switched to.", NamedTextColor.GOLD)
        );
        return;
      }
      if (!membershipChannel.canJoin(player)) {
        player.sendMessage(
            Component.text("You cannot switch to this channel.", NamedTextColor.GOLD)
        );
        return;
      }

      activeChannelStorage.switchChannel(player.getUniqueId(), membershipChannel);
      player.sendMessage(
          Component.text("You have switched to the channel.", NamedTextColor.GOLD)
      );
    }, () -> {
      player.sendMessage(
          Component.text("Channel not found.", NamedTextColor.GOLD)
      );
    });

    return true;
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
