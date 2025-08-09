package me.perch.elevator.commands;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.perch.elevator.Variables;
import me.perch.elevator.files.MessagesFile;
import org.jspecify.annotations.NullMarked;

@SuppressWarnings({"UnstableApiUsage", "SameReturnValue"})
@NullMarked
public class CommandPerchElevator {

    public LiteralCommandNode<CommandSourceStack> constructCommand() {
        return Commands.literal("perchelevator")
            .then(Commands.literal("reload")
                .requires(source -> source.getSender().hasPermission("perchelevator.reload"))
                .executes(ctx -> {
                    Variables.reload();
                    MessagesFile.reload();
                    ctx.getSource().getSender().sendMessage("§aPerchElevator config reloaded.");
                    return Command.SINGLE_SUCCESS;
                })
            )
            .build();
    }
}