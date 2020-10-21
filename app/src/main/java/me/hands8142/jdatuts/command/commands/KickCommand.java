package me.hands8142.jdatuts.command.commands;

import me.hands8142.jdatuts.Config;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class KickCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();

        if (args.size() < 2 || message.getMentionedMembers().isEmpty()) {
            channel.sendMessage("잘못 입력 하셨습니다.").queue();
            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if (!member.canInteract(target) || !member.hasPermission(Permission.KICK_MEMBERS)) {
            channel.sendMessage("추방 할 권한이 없습니다.").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.KICK_MEMBERS)) {
            channel.sendMessage("추방 할 권한이 없습니다.").queue();
            return;
        }

        final String reason = String.join(" ", args.subList(1, args.size()));

        ctx.getGuild()
                .kick(target, reason)
                .reason(reason)
                .queue(
                        (__) -> channel.sendMessage("킥 성공").queue(),
                        (error) -> channel.sendMessageFormat("실패하였습니다 %s", error.getMessage()).queue()
                );
    }

    @Override
    public String getName() {
        return "킥";
    }

    @Override
    public String getHelp() {
        return "유저를 서버에서 추방시킵니다.\n" +
                "사용방법: `" + Config.get("PREFIX") + getName() + " <@user> <reason>";
    }

    @Override
    public List<String> getAliases() {
        return List.of("kick", "추방");
    }
}
