package me.hands8142.jdatuts.command.commands.music;

import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import me.hands8142.jdatuts.lavaplayer.GuildMusicManager;
import me.hands8142.jdatuts.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

@SuppressWarnings("ConstantConditions")
public class StopCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        final AudioManager audioManager = ctx.getGuild().getAudioManager();

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

        musicManager.scheduler.player.stopTrack();
        musicManager.scheduler.queue.clear();
        audioManager.closeAudioConnection();

        channel.sendMessage("플레이어가 중지되고 대기열이 지워졌습니다.").queue();
    }

    @Override
    public String getName() {
        return "멈춰";
    }

    @Override
    public String getHelp() {
        return "현재 노래를 중지하고 대기열을 지 웁니다.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("stop");
    }
}
