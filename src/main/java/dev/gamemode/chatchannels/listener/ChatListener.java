package dev.gamemode.chatchannels.listener;

import dev.gamemode.chatchannels.model.active.ActiveChannelStorage;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class ChatListener implements Listener {

  private final ActiveChannelStorage activeChannelStorage;

  @EventHandler
  private void onChat(AsyncChatEvent event) {
    activeChannelStorage.getActiveChannel(event.getPlayer().getUniqueId()).ifPresent(channel -> {
      event.viewers().clear();
      event.viewers().addAll(channel.getViewers());

      event.renderer(ChatRenderer.viewerUnaware(
          (source, sourceDisplayName, message) -> channel.getRenderer()
              .render(channel.getDisplayName(), event.getPlayer().displayName(), event.message())));
    });
  }

}
