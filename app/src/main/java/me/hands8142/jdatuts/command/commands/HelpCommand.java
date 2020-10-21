package me.hands8142.jdatuts.command.commands;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.hands8142.jdatuts.CommandManager;
import me.hands8142.jdatuts.Config;
import me.hands8142.jdatuts.command.CommandContext;
import me.hands8142.jdatuts.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if (args.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            builder.append("명령어 목록\n");

            manager.getCommands().stream().map(ICommand::getName).forEach(
                    (it) -> builder.append('`').append(Config.get("prefix")).append(it).append("`\n")
            );

            EmbedBuilder embed = EmbedUtils.getDefaultEmbed()
                    .setTitle("명령어 목록")
                    .setDescription(builder.toString());

            channel.sendMessage(embed.build()).queue();
            return;
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessage("찾을수 없습니다." + search).queue();
            return;
        }

        EmbedBuilder embed = EmbedUtils.getDefaultEmbed()
                .setTitle(search + "의 도움말")
                .setDescription(command.getHelp());

        //channel.sendMessage(command.getHelp()).queue();
        channel.sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "도움말";
    }

    @Override
    public String getHelp() {
        return "봇이 할수있는 명령어 목록을 보여줍니다.\n" +
                "사용방법: `" + Config.get("PREFIX") + getName() + " [command]`";
    }

    @Override
    public List<String> getAliases() {
        return List.of("commands", "cmds", "commandlist", "help", "도움");
    }
}
