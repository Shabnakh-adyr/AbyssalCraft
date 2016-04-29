package com.shinoow.abyssalcraft.common.network.server;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

public class FireMessage extends AbstractServerMessage<FireMessage> {

	private int x;
	private int y;
	private int z;

	public FireMessage() {}

	public FireMessage(BlockPos pos) {
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

		x = ByteBufUtils.readVarInt(buffer, 5);
		y = ByteBufUtils.readVarInt(buffer, 5);
		z = ByteBufUtils.readVarInt(buffer, 5);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

		ByteBufUtils.writeVarInt(buffer, x, 5);
		ByteBufUtils.writeVarInt(buffer, y, 5);
		ByteBufUtils.writeVarInt(buffer, z, 5);
	}

	@Override
	public void process(EntityPlayer player, Side side) {

		World world = player.worldObj;
		BlockPos pos = new BlockPos(x, y, z);

		world.playSoundEffect(player.posX, player.posY, player.posZ, "random.fizz", 1.0F, 1.0F);
		world.setBlockToAir(pos);
	}
}