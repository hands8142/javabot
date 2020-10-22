package me.hands8142.jdatuts.command.commands.info;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.Guild;

import java.util.List;

public class BotInfoCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final String name = ctx.getSelfUser().getName();
        final String java_version = System.getProperty("java.version");
        final String url = ctx.getSelfUser().getAvatarUrl();
        final String jda_version = JDAInfo.VERSION;
        final List<Guild> guilds = ctx.getSelfUser().getMutualGuilds();
        final int size = guilds.size();
        int member = 0;

        for (Guild guild : guilds) {
            member += guild.getMemberCount();
        }

        final EmbedBuilder embed = EmbedUtils.getDefaultEmbed()
                .setTitle(name + "의 정보")
                .setThumbnail(url)
                .addField("이름", name, true)
                .addField("서버수", "" + size, true)
                .addField("유저수", "" + member, true)
                .addField("java 버전", java_version, true)
                .addField("JDA 버전", jda_version, true)
                .addField("제작자", "한동준#7865", true);

        ctx.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "봇정보";
    }

    @Override
    public String getHelp() {
        return "봇정보를 보여줍니다.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("botinfo");
    }
}
