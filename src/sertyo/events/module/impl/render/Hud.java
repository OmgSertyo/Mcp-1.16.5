package sertyo.events.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.blaze3d.matrix.MatrixStack;
import sertyo.events.Main;
import sertyo.events.event.render.EventRender2D;
import sertyo.events.module.api.Category;
import sertyo.events.module.api.Module;
import sertyo.events.module.api.ModuleAnnotation;
import sertyo.events.module.api.setting.impl.MultiBooleanSetting;
import sertyo.events.util.NEGRITEXT;
import sertyo.events.util.render.ColorUtil;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.font.Fonts;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static sertyo.events.util.Util.mc;

@ModuleAnnotation(category = Category.RENDER, name = "Hud", description = "Ты че еблан нахуй тебе на худ описание?")
public class Hud extends Module {
    public static final MultiBooleanSetting elements = new MultiBooleanSetting("Elements", Arrays.asList("Watermark", "Server Info", "Coords", "Inventory", "Potions", "Armor", "Target HUD"));

    private NEGRITEXT text = new NEGRITEXT(Arrays.asList("", "Я бью негров", "Емае", "БУ БАБУЛЬКУ"), 2000);
    @EventTarget
    public void onRender2D(EventRender2D event) {
        //Мне категорический лень писать худ поэтому я возьму из 2022) НЕ БЕЙТЕ ЗА ГОВНО КОД ПОЖАЛУЙСТА ЭТО ИЗ 2022 !!!
        if (!mc.gameSettings.showDebugInfo) {
            MatrixStack ms = event.getMatrixStack();
            int scaledHeight = Main.getInstance().getScaleMath().calc(event.getResolution().getScaledHeight());
            int color = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
            Color color3 = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
            int color2 = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
            Color color4 = Main.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
            int middleColor = ColorUtil.interpolateColorC(color, color2, 0.5F).getRGB();
            boolean isDark = true;
            Main.getInstance().getScaleMath().pushScale();
            int itemOffset;
            int itemOffset2;
            int itemOffset3;
            int itemOffset4;
            String coordsText;
            String serverPing;
            String coordsText2;
            String coordsText3;
            if (elements.get(0)) { }
        }
    }
}
