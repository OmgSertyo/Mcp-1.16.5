package sertyo.events.util;

import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.client.CClickWindowPacket;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static sertyo.events.util.Util.mc;
//FROM https://yougame.biz/threads/339725/
public class InvUtil {
    public static Slot getInventorySlot(Item item) {
        return mc.player.openContainer.inventorySlots.stream().filter(s -> s.getStack().getItem().equals(item) && s.slotNumber >= mc.player.openContainer.inventorySlots.size() - 36).findFirst().orElse(null);
    }

    public static Slot getInventorySlot(List<Item> item) {
        return mc.player.openContainer.inventorySlots.stream().filter(s -> item.contains(s.getStack().getItem()) && s.slotNumber >= mc.player.openContainer.inventorySlots.size() - 36).findFirst().orElse(null);
    }

    public static Slot getFoodMaxSaturationSlot() {
        return mc.player.openContainer.inventorySlots.stream().filter(s -> s.getStack().getItem().getFood() != null && !s.getStack().getItem().getFood().canEatWhenFull()).max(Comparator.comparingDouble(s -> s.getStack().getItem().getFood().getSaturation())).orElse(null);
    }

    public static int getInventoryCount(Item item) {
        return IntStream.range(0, 45).filter(i -> mc.player.inventory.getStackInSlot(i).getItem().equals(item)).map(i -> mc.player.inventory.getStackInSlot(i).getCount()).sum();
    }

    public static void clickSlot(Slot slot, int button, ClickType clickType, boolean packet) {
        if (slot != null) clickSlotId(slot.slotNumber, button, clickType, packet);
    }

    public static void clickSlotId(int slot, int button, ClickType clickType, boolean packet) {
        if (packet) {
            mc.player.connection.sendPacket(new CClickWindowPacket(mc.player.openContainer.windowId, slot, button, clickType, ItemStack.EMPTY, mc.player.openContainer.getNextTransactionID(mc.player.inventory)));
        } else {
            mc.playerController.windowClick(mc.player.openContainer.windowId, slot, button, clickType, mc.player);
        }
    }

    public static int getPrice(ItemStack itemStack) {
        CompoundNBT tag = itemStack.getTag();
        if (tag == null) return -1;
        String price = StringUtils.substringBetween(tag.toString(), "\"text\":\" $", "\"}]");
        if (price == null || price.isEmpty()) return -1;
        price = price.replaceAll(" ", "").replaceAll(",", "");
        return Integer.parseInt(price);
    }
}
