package godswar.godswar.utils.library;
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Crypto Morin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.Colorable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("deprecation")
public final class BlockX {

	public static final int CAKE_SLICES = 6;

	public static boolean isLit(Block block) {
			if (!(block.getBlockData() instanceof Lightable)) return false;
			Lightable lightable = (Lightable) block.getBlockData();
			return lightable.isLit();
	}

	/**
	 * Checks if the block is a container.
	 * Containers are chests, hoppers, enderchests and everything that
	 * has an inventory.
	 *
	 * @param block the block to check.
	 * @return true if the block is a container, otherwise false.
	 */
	public static boolean isContainer(Block block) {
		return block.getState() instanceof InventoryHolder;
	}

	/**
	 * Can be furnaces or redstone lamps.
	 */
	public static void setLit(Block block, boolean lit) {
			if (!(block.getBlockData() instanceof Lightable)) return;
			Lightable lightable = (Lightable) block.getBlockData();
			lightable.setLit(lit);
			return;
	}

	/**
	 * Wool and Dye. But Dye is not a block itself.
	 */
	public static DyeColor getColor(Block block) {
			if (!(block.getBlockData() instanceof Colorable)) return null;
			Colorable colorable = (Colorable) block.getBlockData();
			return colorable.getColor();
	}

	public static boolean isCake(Material material) {
		return material.name().equals("CAKE_BLOCK");
	}

	public static boolean isWheat(Material material) {
		return material.name().equals("CROPS");
	}

	public static boolean isSugarCane(Material material) {
		return material.name().equals("SUGAR_CANE_BLOCK");
	}

	public static boolean isBeetroot(Material material) {
		return material.name().equals("BEETROOT_BLOCK");
	}

	public static boolean isNetherWart(Material material) {
		return material.name().equals("NETHER_WARTS");
	}

	public static boolean isCarrot(Material material) {
		return material == Material.CARROT;
	}

	public static boolean isPotato(Material material) {
		return material == Material.POTATO;
	}

	public static BlockFace getDirection(Block block) {
			if (!(block.getBlockData() instanceof Directional)) return BlockFace.SELF;
			Directional direction = (Directional) block.getBlockData();
			return direction.getFacing();
	}

	public static boolean setDirection(Block block, BlockFace facing) {
			if (!(block.getBlockData() instanceof Directional)) return false;
			Directional direction = (Directional) block.getBlockData();
			direction.setFacing(facing);
			return true;
	}

	public static int getAge(Block block) {
			if (!(block.getBlockData() instanceof Ageable)) return 0;
			Ageable ageable = (Ageable) block.getBlockData();
			return ageable.getAge();
	}

	public static void setAge(Block block, int age) {
			if (!(block.getBlockData() instanceof Ageable)) return;
			Ageable ageable = (Ageable) block.getBlockData();
			ageable.setAge(age);
	}

	/**
	 * Sets the type of any block that can be colored.
	 *
	 * @param block the block to color.
	 * @param color the color to use.
	 * @return true if the block can be colored, otherwise false.
	 */
	public static boolean setColor(Block block, DyeColor color) {
			String type = block.getType().name();
			if (type.endsWith("WOOL")) block.setType(Material.getMaterial(color.name() + "_WOOL"));
			else if (type.endsWith("BED")) block.setType(Material.getMaterial(color.name() + "_BED"));
			else if (type.endsWith("STAINED_GLASS"))
				block.setType(Material.getMaterial(color.name() + "_STAINED_GLASS"));
			else if (type.endsWith("STAINED_GLASS_PANE"))
				block.setType(Material.getMaterial(color.name() + "_STAINED_GLASS_PANE"));
			else if (type.endsWith("TERRACOTTA")) block.setType(Material.getMaterial(color.name() + "_TERRACOTTA"));
			else if (type.endsWith("GLAZED_TERRACOTTA"))
				block.setType(Material.getMaterial(color.name() + "_GLAZED_TERRACOTTA"));
			else if (type.endsWith("BANNER")) block.setType(Material.getMaterial(color.name() + "_BANNER"));
			else if (type.endsWith("WALL_BANNER")) block.setType(Material.getMaterial(color.name() + "_WALL_BANNER"));
			else if (type.endsWith("CARPET")) block.setType(Material.getMaterial(color.name() + "_CARPET"));
			else if (type.endsWith("SHULKER_BOX")) block.setType(Material.getMaterial(color.name() + "_SHULKERBOX"));
			else if (type.endsWith("CONCRETE")) block.setType(Material.getMaterial(color.name() + "_CONCRETE"));
			else if (type.endsWith("CONCRETE_POWDER"))
				block.setType(Material.getMaterial(color.name() + "_CONCRETE_POWDER"));
			else return false;
			return true;
	}

	/**
	 * Can be used on cauldron.
	 */
	public static boolean setFluidLevel(Block block, int level) {
			if (!(block.getBlockData() instanceof Levelled)) return false;
			Levelled levelled = (Levelled) block.getBlockData();
			levelled.setLevel(level);
			return true;
	}

	public static int getFluidLevel(Block block) {
			if (!(block.getBlockData() instanceof Levelled)) return -1;
			Levelled levelled = (Levelled) block.getBlockData();
			return levelled.getLevel();
	}

	public static boolean isWaterStationary(Block block) {
		return block.getType().name().equals("STATIONARY_WATER");
	}

	public static boolean isWater(Material material) {
		final String name = material.name();
		return name.equals("WATER") || name.equals("STATIONARY_WATER");
	}

	public static void setCakeSlices(Block block, int amount) {
		if (!isCake(block.getType())) throw new IllegalArgumentException("Block is not a cake: " + block.getType());
			BlockData bd = block.getBlockData();
			if (bd instanceof org.bukkit.block.data.type.Cake) {
				org.bukkit.block.data.type.Cake cake = (org.bukkit.block.data.type.Cake) bd;

				if (amount <= cake.getMaximumBites()) {
					cake.setBites(cake.getBites() + 1);
				} else {
					block.breakNaturally();
					return;
				}

				block.setBlockData(bd);
			}
			return;
	}

	public static int addCakeSlices(Block block, int slices) {
		if (!isCake(block.getType())) throw new IllegalArgumentException("Block is not a cake: " + block.getType());
			BlockData bd = block.getBlockData();
			org.bukkit.block.data.type.Cake cake = (org.bukkit.block.data.type.Cake) bd;

			if (cake.getBites() + slices <= cake.getMaximumBites()) {
				cake.setBites(cake.getBites() + slices);
			} else {
				block.breakNaturally();
				return cake.getMaximumBites() - cake.getBites();
			}

			block.setBlockData(bd);
			return cake.getMaximumBites() - cake.getBites();
	}

	public static boolean setWooden(Block block, MaterialX species) {
		block.setType(species.getMaterial());
		return true;
	}

	/**
	 * <b>Universal Method</b>
	 * <p>
	 * Check if the block type matches the specified MaterialX
	 *
	 * @param block    the block to check.
	 * @param material the MaterialX similar to this block type.
	 * @return true if the block type is similar to the material.
	 */
	public static boolean isType(Block block, MaterialX material) {
		Material blockType = block.getType();

		switch (material) {
			case CAKE:
				return isCake(blockType);
			case NETHER_WART:
				return isNetherWart(blockType);
			case CARROT:
			case CARROTS:
				return isCarrot(blockType);
			case POTATO:
			case POTATOES:
				return isPotato(blockType);
			case WHEAT:
			case WHEAT_SEEDS:
				return isWheat(blockType);
			case BEETROOT:
			case BEETROOT_SEEDS:
			case BEETROOTS:
				return isBeetroot(blockType);
			case SUGAR_CANE:
				return isSugarCane(blockType);
			case WATER:
				return isWater(blockType);
			case AIR:
				return isAir(blockType);
			default:
				return material.getMaterial() == blockType && block.getData() == material.getData();
		}
	}

	public static boolean isAir(Material material) {
		// Only air material names end with "IR"
		return material.name().endsWith("IR");
	}

	public static boolean isPowered(Block block) {
			if (!(block.getBlockData() instanceof Powerable)) return false;
			Powerable powerable = (Powerable) block.getBlockData();
			return powerable.isPowered();
	}

	public static void setPowered(Block block, boolean powered) {
			if (!(block.getBlockData() instanceof Powerable)) return;
			Powerable powerable = (Powerable) block.getBlockData();
			powerable.setPowered(powered);
			return;
	}

	public static boolean isOpen(Block block) {
			if (!(block.getBlockData() instanceof org.bukkit.block.data.Openable)) return false;
			org.bukkit.block.data.Openable openable = (org.bukkit.block.data.Openable) block.getBlockData();
			return openable.isOpen();
	}

	public static void setOpened(Block block, boolean opened) {
			if (!(block.getBlockData() instanceof org.bukkit.block.data.Openable)) return;
			org.bukkit.block.data.Openable openable = (org.bukkit.block.data.Openable) block.getBlockData();
			openable.setOpen(opened);
			return;
	}

	public static BlockFace getRotation(Block block) {
			if (!(block.getBlockData() instanceof Rotatable)) return null;
			Rotatable rotatable = (Rotatable) block.getBlockData();
			return rotatable.getRotation();
	}

	public static void setRotation(Block block, BlockFace facing) {
			if (!(block.getBlockData() instanceof Rotatable)) return;
			Rotatable rotatable = (Rotatable) block.getBlockData();
			rotatable.setRotation(facing);
	}

	private static boolean isMaterial(Block block, String... materials) {
		String type = block.getType().name();
		for (String material : materials)
			if (type.equals(material)) return true;
		return false;
	}

	private static final Set<Material> indestructible = ImmutableSet.of(
			MaterialX.BARRIER.getMaterial(), MaterialX.BEDROCK.getMaterial(), MaterialX.COMMAND_BLOCK.getMaterial(),
			MaterialX.CHAIN_COMMAND_BLOCK.getMaterial(), MaterialX.REPEATING_COMMAND_BLOCK.getMaterial(), MaterialX.END_PORTAL_FRAME.getMaterial(),
			MaterialX.STRUCTURE_BLOCK.getMaterial()
	);

	public static boolean isIndestructible(Material type) {
		return indestructible.contains(type);
	}

	private static final Map<String, String> BLOCK_MATERIALS = ImmutableMap.<String, String>builder()
			.put("BED", "BED_BLOCK").put("BANNER", "STANDING_BANNER")
			.put("CAULDRON_ITEM", "CAULDRON")
			.put("REDSTONE_COMPARATOR", "REDSTONE_COMPARATOR_OFF")
			.put("SKULL_ITEM", "SKULL").put("DIODE", "DIODE_BLOCK_OFF").build();

	private static Material checkMaterial(Material material) {
		if (material != null && BLOCK_MATERIALS.containsKey(material.name())) {
			return Material.getMaterial(BLOCK_MATERIALS.get(material.name()));
		}
		return material;
	}

	private static Method SET_DATA = null;

	static {
			try {
				SET_DATA = Block.class.getDeclaredMethod("setData", byte.class);
			} catch (NoSuchMethodException ignored) {
			}
	}

	public static void setType(Block block, MaterialX materialX) {
		Material material = checkMaterial(materialX.getMaterial());
		if (material != null) {
			block.setType(material);
				try {
					SET_DATA.invoke(block, materialX.getData());
				} catch (IllegalAccessException | InvocationTargetException ignored) {
				}
		}
	}

	public static void sendBlockChange(Player player, Location location, MaterialX materialX) {
		Material material = checkMaterial(materialX.getMaterial());
		if (material != null) {
				player.sendBlockChange(location, material.createBlockData());
		}
	}

}