package sertyo.events.module.impl.combat;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import sertyo.events.event.packet.EventPacket;
import sertyo.events.event.packet.EventReceivePacket;
import sertyo.events.module.api.Category;
import sertyo.events.module.api.Module;
import sertyo.events.module.api.ModuleAnnotation;
import sertyo.events.module.api.setting.impl.NumberSetting;

import java.util.Random;

import static sertyo.events.util.Util.mc;

@ModuleAnnotation(name = "SuperBow", category = Category.COMBAT, description = "Выстреливает в противника с огромной силой")
public class SuperBow extends Module {

    private final NumberSetting power = new NumberSetting("Сила", 30, 1, 100, 1);
    private final Random random = new Random();

    @EventTarget
    public void onpacket(EventReceivePacket e) {
        if (e.getPacket() instanceof CPlayerDiggingPacket p) {
            if (p.getAction() == CPlayerDiggingPacket.Action.RELEASE_USE_ITEM) {
                mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_SPRINTING));
                System.out.println(power.get());
                for (int i = 0; i < power.get(); i++) {
                    mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY() - 0.000000001, mc.player.getPosZ(), true));
                    mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(mc.player.getPosX(), mc.player.getPosY() + 0.000000001, mc.player.getPosZ(), false));
                }
            }
        }
    }
}
