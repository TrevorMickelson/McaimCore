package com.mcaim.core.chat;

import com.mcaim.core.scheduler.Sync;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler (priority = EventPriority.LOWEST)
    public void onChatInput(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ChatInput chatInput = ChatInput.getChatInputUser(player.getUniqueId());

        if (chatInput == null)
            return;

        event.setCancelled(true);
        Sync.get().run(() -> {
            chatInput.getChatAction().onChat(event.getMessage());
            ChatInput.removeChatInputUser(player.getUniqueId());
        });
    }
}
