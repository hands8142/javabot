package me.hands8142.jdatuts.command.commands;

import me.duncte123.botcommons.web.WebUtils;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.List;

public class NaverChartCommand implements ICommand {
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        WebUtils.ins.getJSONObject("https://manyyapi.herokuapp.com/naver").async((json) -> {

            if (!json.get("success").asBoolean()) {
                channel.sendMessage(json.get("error").asText()).queue();
                return;
            }

            final MessageAction messageAction = channel.sendMessage("**현재 네이버 실시간 검색어 차트**\n");

            for (int i = 1; i < 21; i++) {
                String text = json.get(i + "").asText();
                messageAction.append(Integer.toString(i))
                        .append("위: `")
                        .append(text)
                        .append("`\n");
            }

            messageAction.queue();
        });
    }

    @Override
    public String getName() {
        return "실시간검색어";
    }

    @Override
    public String getHelp() {
        return "현재 네이버 실시간 검색어를 보여줍니다.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("naverchart", "NaverChart", "네이버차트");
    }
}
