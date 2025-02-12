package sertyo.events;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.Getter;
import me.sertyo.j2c.J2c;


import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.lwjgl.glfw.GLFW;
import sertyo.events.command.api.CommandManager;
import sertyo.events.event.input.EventInputKey;
import sertyo.events.event.input.EventMouse;
import sertyo.events.event.render.EventRender2D;
import sertyo.events.manager.friend.FriendManager;
import sertyo.events.manager.macro.MacroManager;
import sertyo.events.module.api.Module;
import sertyo.events.manager.config.ConfigManager;
import sertyo.events.manager.dragging.DragManager;
import sertyo.events.manager.theme.ThemeManager;
import sertyo.events.module.api.ModuleManager;
import sertyo.events.ui.gui.Gui;
import sertyo.events.util.math.ScaleMath;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.font.Fonts;

import java.util.Iterator;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@Getter
@J2c
public class Main {

    /** Все аннотации для нативки если вдруг чит станет не с открытыми исходниками
     **/

    public static String name = "Neiron";
    public static String version = "1.16.5 edition";
    public static String build = "1.0.1";
    long startTime;
    long endtime;
    @Getter
    private static final Main instance = new Main();
    private ScaleMath scaleMath;
    private ModuleManager moduleManager;
    private DragManager dragManager;
    public static boolean first = false;
    private final ConfigManager configManager = new ConfigManager();
    private ThemeManager themeManager;
    private CommandManager commandManager;
    private MacroManager macroManager;
    private FriendManager friendManager;
    private Gui csGui;

    @SneakyThrows
    public void start() {
        EventManager.register(this);
        startTime = System.currentTimeMillis();
        if (System.getProperty("os.name").toLowerCase().contains("mac"))
            this.scaleMath = new ScaleMath(3);
        else
            this.scaleMath = new ScaleMath(2);
        this.themeManager = new ThemeManager();
        this.commandManager = new CommandManager();
        this.moduleManager = new ModuleManager();

        this.dragManager = new DragManager();
        this.dragManager.init();
        this.macroManager = new MacroManager();
        this.macroManager.init();
        this.friendManager = new FriendManager();
        this.friendManager.init();
        this.csGui = new Gui();
        this.configManager.loadConfig("autocfg");
        Minecraft.getInstance().session = new Session("_3lOY_NeOn4IK_", "", "", "mojang");

        endtime = System.currentTimeMillis();
        System.out.println("Start time is " + (endtime - startTime));
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }


    public void shutdown() {
        //Все хотят мой звездный Член
        this.dragManager.save();
        this.configManager.saveConfig("autocfg");
    }
    //For change language
    public void toggleLanguage() {

    }
    @EventTarget
    public void onInputKey(EventInputKey eventInputKey) {
        Iterator var2 = this.moduleManager.getModules().iterator();

        while(var2.hasNext()) {
            Module module = (Module)var2.next();
            if (module.getBind() == eventInputKey.getKey()) {
                module.toggle();
            }
        }
        if (eventInputKey.getKey() == GLFW.GLFW_KEY_RIGHT_SHIFT)
            Minecraft.getInstance().displayGuiScreen(csGui);
        this.macroManager.onKeyPressed(eventInputKey.getKey());
    }

    @EventTarget
    public void onMouse(EventMouse eventMouse) {

        for (sertyo.events.module.api.Module value : this.moduleManager.getModules()) {
            Module module = (Module) value;
            if (module.getMouseBind() == eventMouse.getButton() && eventMouse.getButton() > 2) {
                module.toggle();
            }
        }

        this.macroManager.onMousePressed(eventMouse.getButton());
    }
    @EventTarget
    public void onRender2D(EventRender2D eventMouse) {
        RenderUtility.drawRect(10, 10, 10, 10, -1);
        Fonts.INTER_BOLD.get(20).draw(eventMouse.getMatrixStack(), "Негр", 20, 20, -1);
    }

}
