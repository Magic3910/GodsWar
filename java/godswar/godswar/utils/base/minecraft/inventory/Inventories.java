package godswar.godswar.utils.base.minecraft.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Inventories {

	public static final Inventory common = Bukkit.createInventory(null, 9);

	private Inventories() {
	}

	public static void setItem(final PlayerInventory inventory, final EquipmentSlot slot, final ItemStack item) {
		switch (slot) {
			case HAND:
				inventory.setItemInMainHand(item);
				break;
			case OFF_HAND:
				inventory.setItemInOffHand(item);
				break;
			case FEET:
				inventory.setBoots(item);
				break;
			case LEGS:
				inventory.setLeggings(item);
				break;
			case CHEST:
				inventory.setChestplate(item);
				break;
			case HEAD:
				inventory.setHelmet(item);
				break;
			default:
				break;
		}
	}

	public static ItemStack getItem(final PlayerInventory inventory, final EquipmentSlot slot) {
		switch(slot) {
			case HAND:
				return inventory.getItemInMainHand();
			case OFF_HAND:
				return inventory.getItemInOffHand();
			case FEET:
				return inventory.getBoots();
			case LEGS:
				return inventory.getLeggings();
			case CHEST:
				return inventory.getChestplate();
			case HEAD:
				return inventory.getHelmet();
			default:
				break;
		}
		return null;
	}

	public static void setItem(final EntityEquipment inventory, final EquipmentSlot slot, final ItemStack item) {
		switch(slot) {
			case HAND:
				inventory.setItemInMainHand(item);
				break;
			case OFF_HAND:
				inventory.setItemInOffHand(item);
				break;
			case FEET:
				inventory.setBoots(item);
				break;
			case LEGS:
				inventory.setLeggings(item);
				break;
			case CHEST:
				inventory.setChestplate(item);
				break;
			case HEAD:
				inventory.setHelmet(item);
				break;
			default:
				break;
		}
	}

	public static ItemStack getItem(final EntityEquipment inventory, final EquipmentSlot slot) {
		switch(slot) {
			case HAND:
				return inventory.getItemInMainHand();
			case OFF_HAND:
				return inventory.getItemInOffHand();
			case FEET:
				return inventory.getBoots();
			case LEGS:
				return inventory.getLeggings();
			case CHEST:
				return inventory.getChestplate();
			case HEAD:
				return inventory.getHelmet();
			default:
				break;
		}
		return null;
	}

}
