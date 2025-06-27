package vn.com.duongvct.test.discordbot.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.time.Instant;
import java.util.List;

public class LoggingListener extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingListener.class);
    private static final String LOG_CHANNEL = "my-discord-bot-logs";
    private static final String MESSAGE_DELETED_TITLE = "Message Deleted";
    private static final String MESSAGE_DELETED_DESCRIPTION = "A message was deleted in #%s.";

    private void sendLog(TextChannel channel, String title, String description, Color color) {
        if (channel == null || !channel.canTalk()) {
            logger.error("Cannot send message to {}", (channel == null ? "null channel" : channel.getName()));
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setDescription(description);
        eb.setColor(color);
        eb.setTimestamp(Instant.now());
        channel.sendMessageEmbeds(eb.build()).queue();
    }

    private TextChannel getLogChannel(@NotNull Guild guild) {
        List<TextChannel> logChannels = guild.getTextChannelsByName(LOG_CHANNEL, true);
        if (!logChannels.isEmpty()) {
            return logChannels.get(0);
        }
        return guild.getTextChannels().isEmpty() ? null : guild.getTextChannels().get(0);
    }
    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        TextChannel logChannel = getLogChannel(event.getGuild());
        String description = String.format(MESSAGE_DELETED_DESCRIPTION, event.getChannel().getName());
        sendLog(logChannel, MESSAGE_DELETED_TITLE, description, Color.GRAY);
    }
}
