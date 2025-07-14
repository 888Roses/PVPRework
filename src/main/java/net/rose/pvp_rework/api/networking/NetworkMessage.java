package net.rose.pvp_rework.api.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public abstract class NetworkMessage {
    protected Identifier identifier;

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /**
     * Sends a {@link PacketByteBuf} {@code data} to a given {@link ServerPlayerEntity} {@code serverPlayerEntity} for
     * it to be interpreted on the {@code Client Side} of that {@link ServerPlayerEntity}. This data is then received
     * and interpreted by
     * {@link NetworkMessage#receiveOnClient(MinecraftClient, ClientPlayNetworkHandler, PacketByteBuf, PacketSender)}.
     *
     * @param serverPlayerEntity The {@link ServerPlayerEntity} that will receive the data packet.
     * @param data               The data to send to the client of that {@link ServerPlayerEntity}.
     */
    public void sendToClient(ServerPlayerEntity serverPlayerEntity, PacketByteBuf data) {
        ServerPlayNetworking.send(serverPlayerEntity, getIdentifier(), data);
    }

    /**
     * Sends a {@link PacketByteBuf} {@code data} to the connected {@link MinecraftServer}. This data is then received
     * and interpreted by
     * {@link NetworkMessage#receiveOnServer(MinecraftServer, ServerPlayerEntity, ServerPlayNetworkHandler, PacketByteBuf,
     * PacketSender)}.
     *
     * @param data The data to send to the server.
     */
    public void sendToServer(PacketByteBuf data) {
        ClientPlayNetworking.send(getIdentifier(), data);
    }

    public abstract ReceiverType getType();

    public void receiveOnClient(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler,
                                PacketByteBuf packetByteBuf, PacketSender packetSender) {}

    public void receiveOnServer(MinecraftServer minecraftServer, ServerPlayerEntity serverPlayerEntity,
                                ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf packetByteBuf,
                                PacketSender packetSender) {}

    public enum ReceiverType {
        S2C, C2S, BOTH
    }
}
