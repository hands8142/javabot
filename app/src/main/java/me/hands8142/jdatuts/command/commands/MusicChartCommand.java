package me.hands8142.jdatuts.command.commands;

import me.duncte123.botcommons.web.WebUtils;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.List;

public class MusicChartCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        WebUtils.ins.getJSONObject("https://manyyapi.herokuapp.com/music").async((json) -> {

            if (!json.get("success").asBoolean()) {
                channel.sendMessage(json.get("error").asText()).queue();
                return;
            }

            final MessageAction messageAction = channel.sendMessage("**현재 음악 차트**\n");

            for (int i = 1; i < 31; i++) {
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
        return "음악차트";
    }

    @Override
    public String getHelp() {
        return "멜론 차트 Top30를 보여 줍니다.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("musicshart", "MusicChart", "멜론");
    }
}
