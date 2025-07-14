package net.rose.pvp_rework.common.networking;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.rose.pvp_rework.api.networking.NetworkMessage;
import net.rose.pvp_rework.api.util.SoundUtil;
import net.rose.pvp_rework.common.PVPRework;
import net.rose.pvp_rework.common.init.ModNetworking;
import net.rose.pvp_rework.common.init.ModSounds;

public class ChargoldScytheHitSoundNetworkMessageS2C extends NetworkMessage {
    public static final Identifier ID = PVPRework.id("chargold_scythe_hit_sound");

    @Override
    public void receiveOnClient(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler,
                                PacketByteBuf packetByteBuf, PacketSender packetSender) {
        if (minecraftClient.player == null) {
            return;
        }

        var random = Random.create();
        SoundUtil.playSoundWithDistance(
                minecraftClient.player.getWorld(), new Vec3d(packetByteBuf.readVector3f()),
                ModSounds.CHARGOLD_SCYTHE_HIT, SoundCategory.PLAYERS,
                MathHelper.nextFloat(random, 0.9F, 1.1F),
                MathHelper.nextFloat(random, 0.9F, 1.1F),
                50F
        );
    }

    @Override
    public ReceiverType getType() {
        return ReceiverType.S2C;
    }

    public static void send(ServerPlayerEntity serverPlayer, Vec3d pos) {
        final var packet =  PacketByteBufs.create();
        packet.writeVector3f(pos.toVector3f());
        ModNetworking.SCYTHE_HIT_SOUND.sendToClient(serverPlayer, packet);
    }
}
