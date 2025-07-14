package net.rose.pvp_rework.common.networking;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.rose.pvp_rework.api.networking.NetworkMessage;
import net.rose.pvp_rework.common.PVPRework;
import net.rose.pvp_rework.common.init.ModNetworking;

public class ResetVelocityNetworkMessageS2C extends NetworkMessage {
    public static final Identifier ID = PVPRework.id("reset_velocity_s2c");

    @Override
    public void receiveOnClient(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler,
                                PacketByteBuf packetByteBuf, PacketSender packetSender) {
        final var player = minecraftClient.player;

        if (player == null) {
            return;
        }

        player.setVelocity(new Vec3d(packetByteBuf.readVector3f()));
    }

    public static PacketByteBuf create(Vec3d velocity) {
        final var packet = PacketByteBufs.create();
        packet.writeVector3f(velocity.toVector3f());
        return packet;
    }

    public static void send(ServerPlayerEntity serverPlayerEntity, Vec3d velocity) {
        ModNetworking.RESET_VELOCITY.sendToClient(serverPlayerEntity, create(velocity));
    }

    @Override
    public ReceiverType getType() {
        return ReceiverType.S2C;
    }
}
