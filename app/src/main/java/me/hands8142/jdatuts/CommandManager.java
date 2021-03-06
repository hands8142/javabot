package me.hands8142.jdatuts;

import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import me.hands8142.jdatuts.command.commands.*;
import me.hands8142.jdatuts.command.commands.info.BotInfoCommand;
import me.hands8142.jdatuts.command.commands.info.GuildInfoCommand;
import me.hands8142.jdatuts.command.commands.info.UserInfoCommand;
import me.hands8142.jdatuts.command.commands.music.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        addCommand(new KickCommand());
        addCommand(new BanCommand());
        addCommand(new FeedbackCommand());
        addCommand(new InstagramCommand());
        addCommand(new WeatherCommand());
        addCommand(new CoronaCommand());
        addCommand(new MusicChartCommand());
        addCommand(new NaverChartCommand());

        //info
        addCommand(new BotInfoCommand());
        addCommand(new UserInfoCommand());
        addCommand(new GuildInfoCommand());

        //music
        addCommand(new JoinCommand());
        addCommand(new LeaveCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new SkipCommand());
        addCommand(new MusicInfoCommand());
        addCommand(new VolumeCommand());
        addCommand(new PauseCommand());
        addCommand(new QueueCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    void handle(GuildMessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Config.get("prefix")), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }

}
