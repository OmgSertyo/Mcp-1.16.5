package sertyo.events.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.item.BlockItem;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import sertyo.events.event.player.EventUpdate;
import sertyo.events.module.api.Category;
import sertyo.events.module.api.Module;
import sertyo.events.module.api.ModuleAnnotation;
import sertyo.events.util.player.ChatUtility;

import java.util.ArrayList;
import java.util.Arrays;

import static sertyo.events.util.Util.mc;
// К моему большому сOжалению данный код не захочет ZOVоркать
@ModuleAnnotation(name = "ZOVZZOVOVZOV", description = "Z СЛАВА БОГУ ZOV БИЛДЕР ZOV", category = Category.UTIL)
public class ZZZZZOVOVOZVOZVOZOVZOVZOVZOVOZV extends Module {
    ArrayList<IPacket> ZOVVOZZOV = new ArrayList<>();

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!ZOVVOZZOV.isEmpty()) {
            mc.getConnection().sendPacket(ZOVVOZZOV.get(0));
            ZOVVOZZOV.remove(0);
            ChatUtility.addChatMessage("ZAставил букVу Z успешно ZOV!");
            toggle();
        }
    }
    @Override
    public void onEnable() {
        int slot = -1;
        for(int negri = 0 ;negri >= 9; negri++){
            if(mc.player.inventory.getStackInSlot(negri).getItem() instanceof BlockItem){
                slot = negri;
                break;
            }
            if (slot == -1) {
                ChatUtility.addChatMessage("ВОЗЬМИ БЛОКИ ZOVZOVZOVZOVOZVOZV");
                toggle();
            }
            if(mc.player.inventory.currentItem!=slot){
                mc.getConnection().sendPacket(new CHeldItemChangePacket(slot));
                mc.player.inventory.currentItem = slot;
            }
            Vector3d ZOV = mc.player.getPositionVec().add(0,0,0);
            buildZOVZOVOZVOOV(ZOV);
            mc.player.swingArm(Hand.MAIN_HAND);
            if (ZOVVOZZOV.isEmpty())
                toggle();
        }

    }
    public void buildZOVZOVOZVOOV(Vector3d ZOV) {
        ZOVVOZZOV.addAll(Arrays.asList(
                // Верхняя горизонтальная линия
                new CPlayerTryUseItemOnBlockPacket(Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(ZOV.x - 1, ZOV.y, ZOV.z), Direction.UP, new BlockPos((int) ZOV.x - 1, (int) ZOV.y, (int) ZOV.z), true)),
                new CPlayerTryUseItemOnBlockPacket(Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(ZOV.x, ZOV.y, ZOV.z), Direction.UP, new BlockPos((int) ZOV.x, (int) ZOV.y, (int) ZOV.z), true)),
                new CPlayerTryUseItemOnBlockPacket(Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(ZOV.x + 1, ZOV.y, ZOV.z), Direction.UP, new BlockPos((int) ZOV.x + 1, (int) ZOV.y, (int) ZOV.z), true)),

                // Диагональная часть
                new CPlayerTryUseItemOnBlockPacket(Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(ZOV.x, ZOV.y + 1, ZOV.z), Direction.UP, new BlockPos((int) ZOV.x, (int) ZOV.y + 1, (int) ZOV.z), true)),

                // Нижняя горизонтальная линия
                new CPlayerTryUseItemOnBlockPacket(Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(ZOV.x - 1, ZOV.y + 2, ZOV.z), Direction.UP, new BlockPos((int) ZOV.x - 1, (int) ZOV.y + 2, (int) ZOV.z), true)),
                new CPlayerTryUseItemOnBlockPacket(Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(ZOV.x, ZOV.y + 2, ZOV.z), Direction.UP, new BlockPos((int) ZOV.x, (int) ZOV.y + 2, (int) ZOV.z), true)),
                new CPlayerTryUseItemOnBlockPacket(Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(ZOV.x + 1, ZOV.y + 2, ZOV.z), Direction.UP, new BlockPos((int) ZOV.x + 1, (int) ZOV.y + 2, (int) ZOV.z), true))
        ));
    }
}
