package dev.gamemode.chatchannels.renderer;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

@RequiredArgsConstructor
public class ConfiguredRenderer implements ChannelRenderer {

  private final String input;

  /*
   This is okay according to the Paper team.
   https://discord.com/channels/289587909051416579/555462289851940864/1171503384700919830
  */
  @Override
  public Component render(Component channelName, Component sourceDisplayName, Component message) {
    return MiniMessage.miniMessage()
        .deserialize(input,
            TagResolver.resolver("source_display_name",
                Tag.selfClosingInserting(sourceDisplayName)),
            TagResolver.resolver("message", Tag.selfClosingInserting(message)),
            TagResolver.resolver("channel_name", Tag.selfClosingInserting(channelName))
        );
  }
}
