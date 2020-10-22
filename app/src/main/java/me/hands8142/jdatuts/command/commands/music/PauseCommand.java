package me.hands8142.jdatuts.command.commands.music;

import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import me.hands8142.jdatuts.lavaplayer.GuildMusicManager;
import me.hands8142.jdatuts.lavaplayer.PlayerManager;

import java.util.List;

public class PauseCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final boolean paused = musicManager.audioPlayer.isPaused();

        if (paused) {
            musicManager.audioPlayer.setPaused(false);
            ctx.getChannel().sendMessage("다시 재생했습니다.").queue();
        } else {
            musicManager.audioPlayer.setPaused(true);
            ctx.getChannel().sendMessage("일시정지했습니다.").queue();
        }
    }

    @Override
    public String getName() {
        return "일시정지";
    }

    @Override
    public String getHelp() {
        return "일시정지를 하거나 다시 재생합니다.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("pause");
    }
}
