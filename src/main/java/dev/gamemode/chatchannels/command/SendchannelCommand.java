package dev.gamemode.chatchannels.command;

import dev.gamemode.chatchannels.model.channel.Channel;
import dev.gamemode.chatchannels.model.channel.MembershipChannel;
import dev.gamemode.chatchannels.model.provider.ChannelProvider;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SendchannelCommand extends BukkitCommand {

  private final ChannelProvider channelProvider;

  public SendchannelCommand(ChannelProvider channelProvider) {
    super("sendchannel");
    this.channelProvider = channelProvider;

    setPermission("chatchannels.sendchannel");
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
    if (args.length < 2) {
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

      String joined = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
      channel.getViewers().forEach(audience -> {
        audience.sendMessage(channel.getRenderer()
            .render(channel.getDisplayName(), player.displayName(), Component.text(joined)));
      });
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
    if (args.length > 1) {
      return Collections.emptyList();
    }

    return channelProvider.getChannels().stream()
        .filter(channel -> channel.canSee(player))
        .map(Channel::getName)
        .toList();
  }
}
