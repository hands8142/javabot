package me.hands8142.jdatuts.command.commands.info;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class GuildInfoCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final Guild guild = ctx.getGuild();
        final String createTime = guild.getTimeCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        final int memberCount = guild.getMemberCount();
        final int boostCount = guild.getBoostCount();
        final String name = guild.getName();
        final String id = guild.getId();
        final Region region = guild.getRegion();
        final List<Role> roles = guild.getRoles();
        final String verification = guild.getVerificationLevel().toString();
        final String ownerId = guild.getOwnerId();

        StringBuilder builder = new StringBuilder();

        for (Role role : roles) {
            builder.append(role.getAsMention()).append(", ");
        }

        EmbedBuilder embed = EmbedUtils.getDefaultEmbed()
                .setTitle(name + "의 정보")
                .addField("이름", name, true)
                .addField("아이디", id, true)
                .addField("지역", region.toString(), true)
                .addField("보안 수준", verification, true)
                .addField("생성인", "<@" + ownerId + ">", true)
                .addField("생성인 아이디", ownerId, true)
                .addField("부스트수", "" + boostCount, true)
                .addField("유저수" , "" + memberCount, true)
                .addField("생성 날짜", createTime, true)
                .addField("역할", builder.toString(), false);

        ctx.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "서버정보";
    }

    @Override
    public String getHelp() {
        return "서버 정보를 보여 줍니다.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("serverinfo", "guildinfo");
    }
}
