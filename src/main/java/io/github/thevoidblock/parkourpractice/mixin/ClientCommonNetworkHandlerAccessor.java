package io.github.thevoidblock.parkourpractice.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.session.telemetry.WorldSession;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ClientCommonNetworkHandler.class)
public interface ClientCommonNetworkHandlerAccessor {
    @Accessor
    WorldSession getWorldSession();

    @Accessor
    Screen getPostDisconnectScreen();

    @Accessor
    Map<Identifier, byte[]> getServerCookies();

    @Accessor
    Map<String, String> getCustomReportDetails();

    @Accessor
    boolean getSeenInsecureChatWarning();
}
