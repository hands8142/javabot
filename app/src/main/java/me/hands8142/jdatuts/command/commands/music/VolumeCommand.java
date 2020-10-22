package me.hands8142.jdatuts.command.commands.music;

import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import me.hands8142.jdatuts.lavaplayer.GuildMusicManager;
import me.hands8142.jdatuts.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class VolumeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        String volume = String.join(" ", args);

        if (args.size() == 1) {
            musicManager.audioPlayer.setVolume(Integer.parseInt(volume));
            channel.sendMessage("볼륨을 `" + volume + "`으로 변경하였습니다.").queue();
        } else {
            channel.sendMessage("현재 볼륨은 `" + musicManager.audioPlayer.getVolume() + "`입니다").queue();
        }
    }

    @Override
    public String getName() {
        return "볼륨";
    }

    @Override
    public String getHelp() {
        return "현재 볼륨을 보여주거나 볼륨을 조정합니다.\n" +
                "볼륨값이 지정되어 있지 않으면 현재 볼륨값을 보여줍니다.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("volume");
    }
}
