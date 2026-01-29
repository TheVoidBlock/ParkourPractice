package io.github.thevoidblock.parkourpractice.fakes;

import com.mojang.authlib.GameProfile;
import io.github.thevoidblock.parkourpractice.mixin.ClientCommonNetworkHandlerAccessor;
import io.github.thevoidblock.parkourpractice.mixin.ClientPlayNetworkHandlerAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;

import java.util.UUID;

import static io.github.thevoidblock.parkourpractice.ParkourPractice.MOD_ID;

public class FakeNetworkHandler extends ClientPlayNetworkHandler {
    public FakeNetworkHandler(MinecraftClient client, ClientPlayNetworkHandler handler) {
        super(
                client,
                handler.getConnection(),
                new ClientConnectionState(
                        ((ClientPlayNetworkHandlerAccessor) handler).getChunkLoadProgress(),
                        new GameProfile(UUID.randomUUID(), MOD_ID, handler.getProfile().properties()),
                        ((ClientCommonNetworkHandlerAccessor) handler).getWorldSession(),
                        handler.getRegistryManager(),
                        handler.getEnabledFeatures(),
                        handler.getBrand(),
                        handler.getServerInfo(),
                        ((ClientCommonNetworkHandlerAccessor) handler).getPostDisconnectScreen(),
                        ((ClientCommonNetworkHandlerAccessor) handler).getServerCookies(),
                        client.inGameHud.getChatHud().toChatState(),
                        ((ClientCommonNetworkHandlerAccessor) handler).getCustomReportDetails(),
                        handler.getServerLinks(),
                        handler.getSeenPlayers(),
                        ((ClientCommonNetworkHandlerAccessor) handler).getSeenInsecureChatWarning()
                )
        );
    }

    @Override
    public void sendPacket(Packet<?> packet) {}
}
