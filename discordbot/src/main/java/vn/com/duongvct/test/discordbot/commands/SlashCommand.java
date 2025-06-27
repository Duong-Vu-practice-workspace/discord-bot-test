package vn.com.duongvct.test.discordbot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommand {
    String getName();
    void handle(SlashCommandInteractionEvent event);
}
