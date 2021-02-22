package com.mcaim.core.chat;

import com.mcaim.core.util.C;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatInput {
    private static final Map<UUID, ChatInput> inputUsers = new HashMap<>();

    private final Player player;
    private final String inputMessage;
    private final ChatAction chatAction;

    public ChatInput(Player player, String inputMessage, ChatAction chatAction) {
        this.player = player;
        this.inputMessage = inputMessage;
        this.chatAction = chatAction;
        player.closeInventory();
        informUser();
        inputUsers.put(player.getUniqueId(), this);
    }

    public void informUser() {
        C.msg(player, inputMessage);
    }

    public ChatAction getChatAction() { return chatAction; }

    public static ChatInput getChatInputUser(UUID uuid) {
        return inputUsers.get(uuid);
    }

    public static void removeChatInputUser(UUID uuid) {
        inputUsers.remove(uuid);
    }
}
