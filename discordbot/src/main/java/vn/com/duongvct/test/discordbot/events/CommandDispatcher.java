package vn.com.duongvct.test.discordbot.events;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import vn.com.duongvct.test.discordbot.commands.SlashCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandDispatcher extends ListenerAdapter {
    private final Map<String, SlashCommand> commandMap;

    public CommandDispatcher(List<SlashCommand> commands) {
        commandMap = new HashMap<>();
        for (SlashCommand command : commands) {
            commandMap.put(command.getName(), command);
        }
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        SlashCommand command = commandMap.get(event.getName());

        if (command != null) {
            command.handle(event);
        } else {
            event.reply("Unknown command!").setEphemeral(true).queue();
        }
    }

}
