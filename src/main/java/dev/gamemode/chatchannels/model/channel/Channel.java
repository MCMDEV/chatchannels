package dev.gamemode.chatchannels.model.channel;

import dev.gamemode.chatchannels.renderer.ChannelRenderer;
import java.util.Collection;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public interface Channel {

  boolean canSee(Audience audience);

  String getName();

  Component getDisplayName();

  Collection<Audience> getViewers();

  ChannelRenderer getRenderer();

}
