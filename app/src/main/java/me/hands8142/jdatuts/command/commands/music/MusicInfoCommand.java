package me.hands8142.jdatuts.command.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import me.hands8142.jdatuts.lavaplayer.GuildMusicManager;
import me.hands8142.jdatuts.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class MusicInfoCommand implements ICommand {
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
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            channel.sendMessage("현재 재생중인 음악이 없습니다.").queue();
            return;
        }

        final AudioTrackInfo info = track.getInfo();

        channel.sendMessageFormat("제목: `%s`, 저자: `%s` (링크: <%s>)", info.title, info.author, info.uri).queue();
    }

    @Override
    public String getName() {
        return "음악정보";
    }

    @Override
    public String getHelp() {
        return "현재 재생중인 음악의 정보를 보여줍니다.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("musicinfo");
    }
}
