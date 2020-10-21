package me.hands8142.jdatuts.command.commands;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import me.hands8142.jdatuts.Config;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class FeedbackCommand implements ICommand {

    private final WebhookClient client;

    public FeedbackCommand() {
        WebhookClientBuilder builder = new WebhookClientBuilder(Config.get("webhook_url"));
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Webhook-Thread");
            thread.setDaemon(true);
            return thread;
        });

        this.client = builder.build();
    }

    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        if (args.isEmpty()) {
            channel.sendMessage("잘못 입력 하셨습니다.").queue();
            return;
        }

        final User user = ctx.getAuthor();

        WebhookMessageBuilder builder = new WebhookMessageBuilder()
                .setUsername(user.getName()) // ctx.getMember().getEffectiveName()
                .setAvatarUrl(user.getEffectiveAvatarUrl().replaceFirst("gif", "png") + "?size=512")
                .setContent(String.join(" ", args));

        client.send(builder.build());
    }

    @Override
    public String getName() {
        return "피드백";
    }

    @Override
    public String getHelp() {
        return "피드백을 보낼수 있습니다.\n" +
                "사용방법: `" + Config.get("PREFIX") + getName() + " [message]`";
    }

    @Override
    public List<String> getAliases() {
        return List.of("feedback");
    }
}
