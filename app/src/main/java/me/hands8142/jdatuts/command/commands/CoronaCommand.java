package me.hands8142.jdatuts.command.commands;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class CoronaCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        WebUtils.ins.getJSONObject("https://manyyapi.herokuapp.com/corona").async((json) -> {
            if (!json.get("success").asBoolean()) {
                channel.sendMessage(json.get("error").asText()).queue();
                return;
            }

            final String time = json.get("time").asText();
            final String confirm = json.get("확진환자").asText();
            final String cure = json.get("완치환자").asText();
            final String ut = json.get("치료중").asText();
            final String death = json.get("사망").asText();
            final String cumulative = json.get("누적확진률").asText();
            final String fr = json.get("치사율").asText();

            final EmbedBuilder embed = EmbedUtils.getDefaultEmbed()
                    .setTitle("Covid-19 Virus Korea Status")
                    .addField("Data source : Ministry of Health and Welfare of Korea", "http://ncov.mohw.go.kr/index.jsp", false)
                    .addField("최신 데이터 시간", time, false)
                    .addField("확진환자(누적)", confirm, true)
                    .addField("완치환자(격리해제)", cure, true)
                    .addField("치료중(격리 중)", ut, true)
                    .addField("사망", death, true)
                    .addField("누적확진률", cumulative, true)
                    .addField("치사율", fr, true)
                    .setThumbnail("https://wikis.krsocsci.org/images/7/79/%EB%8C%80%ED%95%9C%EC%99%95%EA%B5%AD_%ED%83%9C%EA%B7%B9%EA%B8%B0.jpg");

            channel.sendMessage(embed.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "코로나";
    }

    @Override
    public String getHelp() {
        return "코로나 상황을 보여줍니다.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("corona");
    }
}
