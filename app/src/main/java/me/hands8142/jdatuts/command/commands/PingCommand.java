package me.hands8142.jdatuts.command.commands;

import me.hands8142.jdatuts.Config;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.JDA;

import java.util.List;

public class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue(
                (ping) -> ctx.getChannel()
                        .sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue()
        );
    }

    @Override
    public String getName() {
        return "핑";
    }

    @Override
    public String getHelp() {
        return "봇의 딜레이를 보여줍니다.\n" +
                "사용방법: `" + Config.get("PREFIX") + getName() + "`";
    }

    @Override
    public List<String> getAliases() {
        return List.of("ping");
    }
}
