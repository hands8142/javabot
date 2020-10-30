package me.hands8142.jdatuts.command.commands;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import me.hands8142.jdatuts.Config;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class WeatherCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        if (args.isEmpty()) {
            channel.sendMessage("조회하려면 지역을 제공해야합니다.").queue();
            return;
        }

        final String location = String.join(" ", args);

        WebUtils.ins.getJSONObject("https://manyyapi.herokuapp.com/weather/" + location).async((json) -> {
            if (!json.get("success").asBoolean()) {
                channel.sendMessage(json.get("error").asText()).queue();
                return;
            }

            final String area = json.get("지역").asText();
            final String current = json.get("현재온도").asText();
            final String sensible = json.get("체감온도").asText();
            final String info = json.get("정보").asText();
            final String uv = json.get("자외선").asText();
            final String minmax = json.get("최저최고온도").asText();
            final String fine = json.get("미세먼지").asText();
            final String ultrafine = json.get("초미세먼지").asText();
            final String ozone = json.get("오존 지수").asText();

            final EmbedBuilder embed = EmbedUtils.getDefaultEmbed()
                    .setTitle("날씨")
                    .addField("지역", area, true)
                    .addField("현재온도", current, true)
                    .addField("체감온도", sensible, true)
                    .addField("정보", info, true)
                    .addField("자외선", uv, true)
                    .addField("최저온도/최고온도", minmax, true)
                    .addField("미세먼지", fine, true)
                    .addField("초미세먼지", ultrafine, true)
                    .addField("오존 지수", ozone, true);

            channel.sendMessage(embed.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "날씨";
    }

    @Override
    public String getHelp() {
        return "현재 지역의 날씨를 보여줍니다." +
                "사용방법: `" + Config.get("PREFIX") + getName() + "<location>`";
    }

    @Override
    public List<String> getAliases() {
        return List.of("weather");
    }
}
