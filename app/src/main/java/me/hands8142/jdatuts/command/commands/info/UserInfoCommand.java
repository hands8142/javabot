package me.hands8142.jdatuts.command.commands.info;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.hands8142.jdatuts.Config;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserInfoCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final Message message = ctx.getMessage();
        final List<String> args = ctx.getArgs();

        if(args.size() == 1) {
            final Member target = message.getMentionedMembers().get(0);
            final EmbedBuilder embed = userinfo(target);
            ctx.getChannel().sendMessage(embed.build()).queue();
        } else {
            final Member target = ctx.getMember();
            final EmbedBuilder embed = userinfo(target);
            ctx.getChannel().sendMessage(embed.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "유저정보";
    }

    @Override
    public String getHelp() {
        return "유저 정보를 보여줍니다" +
                "Usage: `" + Config.get("PREFIX") + getName() + " <@user>`";
    }

    private EmbedBuilder userinfo(Member target) {
        final List<Role> roles = target.getRoles();

        StringBuilder builder = new StringBuilder();

        for (Role role : roles) {
            builder.append(role.getAsMention()).append(", ");
        }

        return EmbedUtils.getDefaultEmbed()
                .setTitle(target.getUser().getName() + nickname(target.getNickname()))
                .setAuthor(target.getUser().getName(), target.getUser().getAvatarUrl(), target.getUser().getEffectiveAvatarUrl())
                .setDescription("**이름**: " + target.getAsMention() + "\n**태그**: " + target.getUser().getAsTag() + "\n**아이디**: " + target.getId() + "\n**아바타 링크**: [link](" + target.getUser().getEffectiveAvatarUrl() + ")")
                .setThumbnail(target.getUser().getEffectiveAvatarUrl())
                .addField("서버 참여 시간", target.getTimeJoined().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), true)
                .addField("계정 생성 일", target.getTimeCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), true)
                .addField("역할", builder.toString(), true);
    }

    private String nickname(String name) {
        if (name == null) {
            return "";
        } else {
            return "(" + name + ")";
        }
    }

    @Override
    public List<String> getAliases() {
        return List.of("userinfo");
    }
}
