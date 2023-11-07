package dev.gamemode.chatchannels.renderer;

import net.kyori.adventure.text.Component;

public interface ChannelRenderer {

  Component render(Component channelName, Component sourceDisplayName, Component message);

}
