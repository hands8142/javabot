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
public class LeaveCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        final VoiceChannel memberChannel = memberVoiceState.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("봇이 음성채널에 들어가 있지 않습니다.").queue();
            return;
        }

        audioManager.closeAudioConnection();
        channel.sendMessageFormat("`\uD83D\uDD0A %s`에서 나옵니다", memberChannel.getName()).queue();
    }

    @Override
    public String getName() {
        return "나가";
    }

    @Override
    public String getHelp() {
        return "봇이 음성 채널에 나가게합니다.\n" +
                "사용방법: `" + Config.get("PREFIX") + getName() + "`";
    }

    @Override
    public List<String> getAliases() {
        return List.of("leave");
    }
}
