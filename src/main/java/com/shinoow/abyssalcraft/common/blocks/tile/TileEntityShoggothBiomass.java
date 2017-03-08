/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;

public class TileEntityShoggothBiomass extends TileEntity implements ITickable {

	private int cooldown;
	private int spawnedShoggoths;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		cooldown = nbttagcompound.getInteger("Cooldown");
		spawnedShoggoths = nbttagcompound.getInteger("SpawnedShoggoths");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Cooldown", cooldown);
		nbttagcompound.setInteger("SpawnedShoggoths", spawnedShoggoths);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(pos, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void update() {
		if(worldObj.getDifficulty() != EnumDifficulty.PEACEFUL){
			cooldown++;
			if (cooldown >= 400) {
				cooldown = worldObj.rand.nextInt(10);
				resetNearbyBiomass(true);
				if(!worldObj.isRemote)
					if(worldObj.getEntitiesWithinAABB(EntityLesserShoggoth.class, new AxisAlignedBB(pos).expand(16, 16, 16)).size() <= 6){
						EntityLesserShoggoth mob = new EntityLesserShoggoth(worldObj);
						setPosition(mob, pos.getX(), pos.getY(), pos.getZ());
						mob.onInitialSpawn(worldObj.getDifficultyForLocation(pos), (IEntityLivingData)null);
						worldObj.spawnEntityInWorld(mob);
						spawnedShoggoths++;
						if(spawnedShoggoths >= 5)
							worldObj.setBlockState(pos, ACBlocks.monolith_stone.getDefaultState(), 2);
					}
			}
		}
	}

	public void resetNearbyBiomass(boolean again){
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0, 0, 0);
		TileEntity tile1 = worldObj.getTileEntity(new BlockPos(x - 1, y, z));
		TileEntity tile2 = worldObj.getTileEntity(new BlockPos(x, y, z - 1));
		TileEntity tile3 = worldObj.getTileEntity(new BlockPos(x - 1, y, z - 1));
		TileEntity tile4 = worldObj.getTileEntity(new BlockPos(x + 1, y, z));
		TileEntity tile5 = worldObj.getTileEntity(new BlockPos(x, y, z + 1));
		TileEntity tile6 = worldObj.getTileEntity(new BlockPos(x + 1, y, z + 1));
		TileEntity tile7 = worldObj.getTileEntity(new BlockPos(x - 1, y, z + 1));
		TileEntity tile8 = worldObj.getTileEntity(new BlockPos(x + 1, y, z - 1));
		if(again){
			if(tile1 != null && tile1 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile1).setCooldown(worldObj.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile1).resetNearbyBiomass(false);
			} if(tile2 != null && tile2 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile2).setCooldown(worldObj.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile2).resetNearbyBiomass(false);
			} if(tile3 != null && tile3 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile3).setCooldown(worldObj.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile3).resetNearbyBiomass(false);
			} if(tile4 != null && tile4 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile4).setCooldown(worldObj.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile4).resetNearbyBiomass(false);
			} if(tile5 != null && tile5 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile5).setCooldown(worldObj.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile5).resetNearbyBiomass(false);
			} if(tile6 != null && tile6 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile6).setCooldown(worldObj.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile6).resetNearbyBiomass(false);
			} if(tile7 != null && tile7 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile7).setCooldown(worldObj.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile7).resetNearbyBiomass(false);
			} if(tile8 != null && tile8 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile8).setCooldown(worldObj.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile8).resetNearbyBiomass(false);
			}
		}
		else {
			if(tile1 != null && tile1 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile1).setCooldown(worldObj.rand.nextInt(10));
			if(tile2 != null && tile2 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile2).setCooldown(worldObj.rand.nextInt(10));
			if(tile3 != null && tile3 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile3).setCooldown(worldObj.rand.nextInt(10));
			if(tile4 != null && tile4 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile4).setCooldown(worldObj.rand.nextInt(10));
			if(tile5 != null && tile5 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile5).setCooldown(worldObj.rand.nextInt(10));
			if(tile6 != null && tile6 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile6).setCooldown(worldObj.rand.nextInt(10));
			if(tile7 != null && tile7 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile7).setCooldown(worldObj.rand.nextInt(10));
			if(tile8 != null && tile8 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile8).setCooldown(worldObj.rand.nextInt(10));

		}
	}

	private void setPosition(EntityLiving entity, int x, int y, int z){
		if(worldObj.getBlockState(new BlockPos(x, y + 1, z)).getMaterial().isSolid()){
			if(worldObj.getBlockState(new BlockPos(x, y + 2, z)).getMaterial().isSolid()){
				if(worldObj.getBlockState(new BlockPos(x + 1, y + 1, z)).getMaterial().isSolid()){
					if(worldObj.getBlockState(new BlockPos(x, y + 1, z + 1)).getMaterial().isSolid()){
						if(worldObj.getBlockState(new BlockPos(x + 1, y + 1, z + 1)).getMaterial().isSolid()){
							if(worldObj.getBlockState(new BlockPos(x - 1, y + 1, z)).getMaterial().isSolid()){
								if(worldObj.getBlockState(new BlockPos(x, y + 1, z - 1)).getMaterial().isSolid()){
									if(worldObj.getBlockState(new BlockPos(x - 1, y + 1, z - 1)).getMaterial().isSolid()){
										if(worldObj.getBlockState(new BlockPos(x + 4, y + 1, z)).getMaterial().isSolid()){
											if(worldObj.getBlockState(new BlockPos(x, y + 1, z + 4)).getMaterial().isSolid()){
												if(worldObj.getBlockState(new BlockPos(x + 4, y + 1, z + 4)).getMaterial().isSolid()){
													if(worldObj.getBlockState(new BlockPos(x - 4, y + 1, z)).getMaterial().isSolid()){
														if(worldObj.getBlockState(new BlockPos(x, y + 1, z - 4)).getMaterial().isSolid()){
															if(worldObj.getBlockState(new BlockPos(x - 4, y + 1, z - 4)).getMaterial().isSolid()){
																if(worldObj.getBlockState(new BlockPos(x, y + 15, z)).getMaterial().isSolid()){
																	entity.addPotionEffect(new PotionEffect(MobEffects.resistance, 10, 100));
																	entity.setLocationAndAngles(x, y + 20, z, entity.rotationYaw, entity.rotationPitch);
																} else {
																	entity.addPotionEffect(new PotionEffect(MobEffects.resistance, 10, 100));
																	entity.setLocationAndAngles(x, y + 15, z, entity.rotationYaw, entity.rotationPitch);
																}
															} else entity.setLocationAndAngles(x - 4, y + 1, z - 4, entity.rotationYaw, entity.rotationPitch);
														} else entity.setLocationAndAngles(x, y + 1, z - 4, entity.rotationYaw, entity.rotationPitch);
													} else entity.setLocationAndAngles(x - 4, y + 1, z, entity.rotationYaw, entity.rotationPitch);
												} else entity.setLocationAndAngles(x + 4, y + 1, z + 4, entity.rotationYaw, entity.rotationPitch);
											} else entity.setLocationAndAngles(x, y + 1, z + 4, entity.rotationYaw, entity.rotationPitch);
										} else entity.setLocationAndAngles(x + 4, y + 1, z, entity.rotationYaw, entity.rotationPitch);
									} else entity.setLocationAndAngles(x - 1, y + 1, z - 1, entity.rotationYaw, entity.rotationPitch);
								} else entity.setLocationAndAngles(x, y + 1, z - 1, entity.rotationYaw, entity.rotationPitch);
							} else entity.setLocationAndAngles(x - 1, y + 1, z, entity.rotationYaw, entity.rotationPitch);
						} else entity.setLocationAndAngles(x + 1, y + 1, z + 1, entity.rotationYaw, entity.rotationPitch);
					} else entity.setLocationAndAngles(x, y + 1, z + 1, entity.rotationYaw, entity.rotationPitch);
				} else entity.setLocationAndAngles(x + 1, y + 1, z, entity.rotationYaw, entity.rotationPitch);
			} else entity.setLocationAndAngles(x, y + 2, z, entity.rotationYaw, entity.rotationPitch);
		} else entity.setLocationAndAngles(x, y + 1, z, entity.rotationYaw, entity.rotationPitch);
	}

	public int getCooldown(){
		return cooldown;
	}

	public void setCooldown(int cd){
		cooldown = cd;
	}
}
