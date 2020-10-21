package me.hands8142.jdatuts.command.commands.music;

import me.hands8142.jdatuts.Config;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class JoinCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("이미 음성 채널에 있습니다").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("이 명령이 작동하려면 음성 채널에 있어야합니다.").queue();
            return;
        }

        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        final VoiceChannel memberChannel = memberVoiceState.getChannel();

        audioManager.openAudioConnection(memberChannel);
        channel.sendMessageFormat("`\uD83D\uDD0A %s`에 들어갑니다", memberChannel.getName()).queue();
    }

    @Override
    public String getName() {
        return "입장";
    }

    @Override
    public String getHelp() {
        return "봇이 음성 채널에 참여하도록합니다.\n" +
                "사용방법: `" + Config.get("PREFIX") + getName() + "`";
    }

    @Override
    public List<String> getAliases() {
        return List.of("join", "들어와");
    }
}
