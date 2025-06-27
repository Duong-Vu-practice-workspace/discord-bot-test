package vn.com.duongvct.test.discordbot.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.com.duongvct.test.discordbot.events.LoggingListener;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


@Configuration
public class BotConfig {
    private static final Logger logger = LoggerFactory.getLogger(BotConfig.class);
    @Value("${discord.token}")
    private String discordToken;

    @Bean
    public JDA jda() throws InterruptedException {
        logger.info("Starting bot ...");
        try{
            EnumSet<GatewayIntent> intents = EnumSet.of(
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_INVITES,
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.DIRECT_MESSAGES
            );
            JDA jda = JDABuilder.createDefault(discordToken)
                    .enableIntents(intents)
                    .setActivity(Activity.customStatus("online"))
                    .build();
            jda.awaitReady();
            List<CommandData> commandDataList = new ArrayList<>();
            commandDataList.add(Commands.slash("hello", "Say hello to the bot"));
            commandDataList.add(Commands.slash("chat", "Chat with the AI")
                    .addOption(OptionType.STRING, "prompt", "Ask anything", true));
            commandDataList.add(Commands.slash("user", "Get the user information")
                    .addOption(OptionType.STRING, "username", "Enter username", true));
            commandDataList.add(Commands.slash("help", "Displays this help message"));
            commandDataList.add(Commands.slash("ping", "Checks the bot's latency"));
            commandDataList.add(Commands.slash("info", "Displays information about the bot"));
            commandDataList.add(Commands.slash("define", "Get the definition of word")
                    .addOption(OptionType.STRING, "word", "ask about any word", true));

            jda.updateCommands().addCommands(commandDataList).queue();
            jda.addEventListener(new LoggingListener());
            return jda;
        } catch (InterruptedException e) {
            logger.error("Interrupted while waiting for setup to complete", e);
            Thread.currentThread().interrupt();
        }
        return null;
    }
}
