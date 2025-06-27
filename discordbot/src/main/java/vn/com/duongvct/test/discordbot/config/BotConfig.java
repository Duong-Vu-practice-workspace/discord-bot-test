package vn.com.duongvct.test.discordbot.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BotConfig {
    private static final Logger logger = LoggerFactory.getLogger(BotConfig.class);
    @Value("${discord.token}")
    private String discordToken;

    @Bean
    public JDA jda() throws InterruptedException {
        logger.info("Starting bot ...");
        try{
            JDA jda = JDABuilder.createDefault(discordToken)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                    .setActivity(Activity.watching("Football Manager"))
                    .build();
            jda.awaitReady();
//            jda.addEventListener(new PingPongListener());
            return jda;
        } catch (InterruptedException e) {
            logger.error("Interrupted while waiting for setup to complete", e);
            Thread.currentThread().interrupt();
        }
        return null;
    }
}
