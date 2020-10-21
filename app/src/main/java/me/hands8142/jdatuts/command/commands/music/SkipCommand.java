package me.hands8142.jdatuts.command.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import me.hands8142.jdatuts.lavaplayer.GuildMusicManager;
import me.hands8142.jdatuts.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class SkipCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("이 작업을 수행하려면 음성 채널에 있어야합니다.").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("이 명령이 작동하려면 음성 채널에 있어야합니다.").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("이 작업을 수행하려면 봇과 동일한 음성 채널에 있어야합니다.").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if (audioPlayer.getPlayingTrack() == null) {
            channel.sendMessage("현재 재생중인 음악이 없습니다.").queue();
            return;
        }

        musicManager.scheduler.nextTrack();

        channel.sendMessage("현재 음악을 스킵 했습니다.").queue();
    }

    @Override
    public String getName() {
        return "스킵";
    }

    @Override
    public String getHelp() {
        return "노래를 건너 뛰어 다음 음악을 재생합니다.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("skip", "건너뛰기");
    }
}
