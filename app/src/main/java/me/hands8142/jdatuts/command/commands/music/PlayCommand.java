package me.hands8142.jdatuts.command.commands.music;

import me.hands8142.jdatuts.Config;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import me.hands8142.jdatuts.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class PlayCommand implements ICommand {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        if (ctx.getArgs().isEmpty()) {
            channel.sendMessage("올바른 사용법은 `=플레이 <youtube link>` 입니다.").queue();
            return;
        }

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

        String link = String.join(" ", ctx.getArgs());

        if (!isUrl(link)) {
            link = "ytsearch: " + link;
        }

        PlayerManager.getInstance().loadAndPlay(channel, link);
    }

    @Override
    public String getName() {
        return "플레이";
    }

    @Override
    public String getHelp() {
        return "노래를 재생합니다\n" +
                "사용방법: `" + Config.get("PREFIX") + getName() + " <youtube link>`";
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    @Override
    public List<String> getAliases() {
        return List.of("play");
    }
}
