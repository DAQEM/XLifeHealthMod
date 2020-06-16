package me.daqem.xlifehealth.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.daqem.xlifehealth.utils.SecurityCheck;
import me.daqem.xlifehealth.utils.SendMessage;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

import static me.daqem.xlifehealth.utils.XLifeModifiers.applyMaxHealthModifier;
import static me.daqem.xlifehealth.utils.XLifeModifiers.removeMaxHealthModifiers;

public final class SetHealthCommand {

    private SetHealthCommand() {
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(Commands.literal("sethealth")
                .requires(commandSource -> commandSource.hasPermissionLevel(2))
                .then(Commands.argument("target", EntityArgument.players())
                        .executes(context -> setHealthtoPlayer(
                                context.getSource(),
                                EntityArgument.getPlayers(context, "target"),
                                2
                        ))
                        .then(Commands.argument("health", IntegerArgumentType.integer())
                                .executes(context -> setHealthtoPlayer(
                                        context.getSource(),
                                        EntityArgument.getPlayers(context, "target"),
                                        IntegerArgumentType.getInteger(context, "health")))
                        )
                )
        );
        dispatcher.register(Commands.literal("gethealth")
                .requires(commandSource -> commandSource.hasPermissionLevel(2))
                .then(Commands.argument("targets", EntityArgument.players())
                        .executes(context -> checkHealth(context.getSource(), EntityArgument.getPlayers(context, "targets")))
                )
        );
        dispatcher.register(Commands.literal("about")
                .requires(commandSource -> commandSource.hasPermissionLevel(2))
                .then(Commands.argument("xlifehealth", StringArgumentType.string())
                        .executes(context -> about(context.getSource()))
                )
        );
    }

    private static int about(CommandSource source) {
        SendMessage.sendFeedback(source, TextFormatting.YELLOW + "=====================================");
        SendMessage.sendFeedback(source, TextFormatting.YELLOW + " ");
        SendMessage.sendFeedback(source, TextFormatting.GOLD + "About X Life!");
        SendMessage.sendFeedback(source, TextFormatting.GOLD + " ");
        SendMessage.sendFeedback(source, TextFormatting.WHITE + "Author: DaqEm");
        SendMessage.sendFeedback(source, TextFormatting.WHITE + "Minecraft version: 1.14.4");
        SendMessage.sendFeedback(source, TextFormatting.WHITE + "Created on Forge 28.2.0");
        SendMessage.sendFeedback(source, TextFormatting.WHITE + "Report bugs to: daqemyt@gmail.com");
        SendMessage.sendFeedback(source, TextFormatting.YELLOW + " ");
        SendMessage.sendFeedback(source, TextFormatting.YELLOW + "=====================================");

        return 1;
    }

    private static int checkHealth(CommandSource source, Collection<ServerPlayerEntity> targets) {
        for (ServerPlayerEntity player : targets) {
            SecurityCheck.securityCheck(player);
            SendMessage.sendFeedback(source, TextFormatting.YELLOW + "Health: " + TextFormatting.GOLD + player.getHealth());
            SendMessage.sendFeedback(source, TextFormatting.YELLOW + "Max Health: " + TextFormatting.GOLD + player.getMaxHealth());;
        }
        return 1;
    }

    private static int setHealthtoPlayer(CommandSource source, Collection<ServerPlayerEntity> targets, int hearts) {
        int health = (hearts * 2) - 20;
        if (!(hearts >= 11)) {
            for (ServerPlayerEntity player : targets) {
                removeMaxHealthModifiers(player);
                applyMaxHealthModifier(player, health);
                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                }
            }
        } else {
            SendMessage.sendFeedback(source, TextFormatting.RED + " Error: [number of hearts] must be between 1 and 10.");
        }
        return 1;
    }
}
