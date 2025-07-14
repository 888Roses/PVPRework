package net.rose.pvp_rework.common.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.rose.pvp_rework.api.networking.NetworkMessage;
import net.rose.pvp_rework.common.PVPRework;
import net.rose.pvp_rework.common.networking.ResetVelocityNetworkMessageS2C;

import java.util.HashMap;
import java.util.Map;

public class ModNetworking {
    // region BackEnd
    // Should be on the top of the document!

    private static final Map<Identifier, NetworkMessage> NETWORK_MESSAGES = new HashMap<>();

    private static <T extends NetworkMessage> T register(Identifier identifier, T message) {
        NETWORK_MESSAGES.put(identifier, message);
        message.setIdentifier(identifier);
        return message;
    }

    private static void createServerGlobalReceivers() {
        for (var entry : NETWORK_MESSAGES.entrySet()) {
            var id = entry.getKey();
            var message = entry.getValue();

            if (message.getType() == NetworkMessage.ReceiverType.C2S || message.getType() == NetworkMessage.ReceiverType.BOTH) {
                ServerPlayNetworking.registerGlobalReceiver(id, message::receiveOnServer);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static void createClientGlobalReceivers() {
        for (var entry : NETWORK_MESSAGES.entrySet()) {
            var id = entry.getKey();
            var message = entry.getValue();

            if (message.getType() == NetworkMessage.ReceiverType.S2C || message.getType() == NetworkMessage.ReceiverType.BOTH) {
                ClientPlayNetworking.registerGlobalReceiver(id, message::receiveOnClient);
            }
        }
    }

    // endregion

    public static ResetVelocityNetworkMessageS2C RESET_VELOCITY = register(ResetVelocityNetworkMessageS2C.ID, new ResetVelocityNetworkMessageS2C());

    public static void init() {
        createServerGlobalReceivers();
        PVPRework.LOGGER.info("[{}]: Initialized Mod Networking!", PVPRework.MOD_ID);
    }
}
