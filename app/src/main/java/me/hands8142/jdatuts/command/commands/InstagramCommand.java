package me.hands8142.jdatuts.command.commands;

import com.fasterxml.jackson.databind.JsonNode;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import me.hands8142.jdatuts.Config;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class InstagramCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        if (args.isEmpty()) {
            channel.sendMessage("조회하려면 사용자 이름을 제공해야합니다.").queue();
            return;
        }

        final String usn = args.get(0);

        WebUtils.ins.getJSONObject("https://apis.duncte123.me/insta/" + usn).async((json) -> {
            if (!json.get("success").asBoolean()) {
                channel.sendMessage(json.get("error").get("message").asText()).queue();
                return;
            }

            final JsonNode user = json.get("user");
            final String username = user.get("username").asText();
            final String pfp = user.get("profile_pic_url").asText();
            final String biography = user.get("biography").asText();
            final boolean isPrivate = user.get("is_private").asBoolean();
            final int following = user.get("following").get("count").asInt();
            final int followers = user.get("followers").get("count").asInt();
            final int uploads = user.get("uploads").get("count").asInt();

            final EmbedBuilder embed = EmbedUtils.getDefaultEmbed()
                    .setTitle(username + "의 Instagram 정보", "https://www.instagram.com/" + username)
                    .setThumbnail(pfp)
                    .setDescription(String.format(
                            "**잠금:** %s\n**소개:** %s\n**팔로우:** %s\n**팔로워:** %s\n**업로드:** %s",
                            isPrivate,
                            biography,
                            following,
                            followers,
                            uploads
                    ))
                    .setImage(getLatestImage(json.get("images")));

            channel.sendMessage(embed.build()).queue();
        });
    }

    @Override
    public String getName() {
        return "인스타그램";
    }

    @Override
    public List<String> getAliases() {
        return List.of("instagram", "insta", "인스타");
    }

    @Override
    public String getHelp() {
        return "최신 이미지로 사용자의 Instagram 통계를 표시합니다.\n" +
                "사용방법: `" + Config.get("PREFIX") + getName() + " <username>`";
    }

    private String getLatestImage(JsonNode json) {
        if (!json.isArray()) {
            return null;
        }

        if (json.size() == 0) {
            return null;
        }

        return json.get(0).get("url").asText();
    }
}
