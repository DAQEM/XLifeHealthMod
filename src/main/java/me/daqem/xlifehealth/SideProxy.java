package me.daqem.xlifehealth;

import me.daqem.xlifehealth.commands.SetHealthCommand;
import me.daqem.xlifehealth.events.HealthEvents;
import me.daqem.xlifehealth.utils.SendMessage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

class SideProxy {
    SideProxy() {
        // Life-cycle events
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::commonSetup);

        // Other events
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new HealthEvents());

    }

    /**
     * Called after registry events, so we know blocks, items, etc. are registered
     *
     * @param event The event
     */
    private static void commonSetup(FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void serverStarting(FMLServerStartingEvent event) {
        SendMessage.sendLogger("Server started.");
        SetHealthCommand.register(event.getCommandDispatcher());
    }

    /**
     * In addition to everything handled by SideProxy, this will handle client-side resources. This
     * is where you would register things like models and color handlers.
     */
    static class Client extends SideProxy {
        Client() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Client::clientSetup);
        }

        private static void clientSetup(FMLClientSetupEvent event) {
        }
    }

    /**
     * Only created on dedicated servers.
     */
    static class Server extends SideProxy {
        Server() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Server::serverSetup);
        }

        private static void serverSetup(FMLDedicatedServerSetupEvent event) {
        }
    }
}