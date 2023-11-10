package dev.gamemode.chatchannels.command;

import dev.gamemode.chatchannels.model.channel.Channel;
import dev.gamemode.chatchannels.model.channel.MembershipChannel;
import dev.gamemode.chatchannels.model.provider.ChannelProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatchannelsCommand extends BukkitCommand {

  private final ChannelProvider channelProvider;

  public ChatchannelsCommand(ChannelProvider channelProvider) {
    super("chatchannels");
    this.channelProvider = channelProvider;

    setPermission("chatchannels");
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

    channelProvider.getChannels().stream()
        .filter(channel -> channel.canSee(player))
        .forEach(channel -> {
          if (channel instanceof MembershipChannel membershipChannel) {
            player.sendMessage(getMembershipChannelComponent(membershipChannel));
          } else {
            player.sendMessage(getChannelComponent(channel));
          }
        });

    return true;
  }

  private Component getChannelComponent(Channel channel) {
    return Component.text()
        .append(channel.getDisplayName())
        .append(Component.text(" - ", NamedTextColor.GRAY))
        .append(buildActive(channel))
        .build();
  }

  private Component getMembershipChannelComponent(MembershipChannel channel) {
    return Component.text()
        .append(channel.getDisplayName())
        .append(Component.text(" - ", NamedTextColor.GRAY))
        .append(buildToggle(channel))
        .append(Component.space())
        .append(buildActive(channel))
        .build();
  }

  @NotNull
  private TextComponent buildToggle(MembershipChannel channel) {
    return Component.text("[Toggle]", NamedTextColor.GOLD)
        .hoverEvent(HoverEvent.showText(Component.text("Click to toggle channel.")))
        .clickEvent(ClickEvent.runCommand("/togglechannel " + channel.getName()));
  }

  @NotNull
  private TextComponent buildActive(Channel channel) {
    return Component.text("[Switch]", NamedTextColor.GOLD)
        .hoverEvent(HoverEvent.showText(Component.text("Click to switch channel.")))
        .clickEvent(ClickEvent.runCommand("/switchchannel " + channel.getName()));
  }
}
