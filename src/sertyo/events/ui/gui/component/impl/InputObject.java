package sertyo.events.ui.gui.component.impl;

import com.mojang.blaze3d.matrix.MatrixStack;


import org.lwjgl.glfw.GLFW;
import sertyo.events.module.api.setting.impl.TextSetting;
import sertyo.events.ui.gui.component.Component;
import sertyo.events.util.math.MathUtility;
import sertyo.events.util.render.ColorUtil;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.font.Fonts;

public class InputObject extends Component {

    public TextSetting set;
    public ModuleComponent object;



    public InputObject(ModuleComponent moduleComponent, TextSetting setting) {
        super(0.0F, 0.0F, 0.0F, 14.0F);
        this.object = moduleComponent;
        this.set = setting;
    }
    public InputObject(TextSetting setting) {
        super(0.0F, 0.0F, 0.0F, 14.0F);
        this.set = setting;
    }


    public boolean focused;

    public void render(MatrixStack ms, int mouseX, int mouseY) {
        super.render(ms, mouseX, mouseY);
        Fonts.INTER_BOLD.get(14).draw(ms, set.getName(), x + 5, y + height / 2f - Fonts.INTER_BOLD.get(13).getHeight() / 2f + 2, ColorUtil.rgba(161, 166, 179, 255));

        RenderUtility.drawRoundedRect(x + 5, y + 12, width - 10, 10, 4, ColorUtil.rgba(20, 21, 24, 255));

        Fonts.INTER_BOLD.get(13).draw(new MatrixStack(), set.text, x + 7 + (focused ? 1 : 0), y + 10 + height / 2f - Fonts.INTER_BOLD.get(13).getHeight() / 2f + 2, ColorUtil.rgba(255, 255, 255, 255));
        this.height = 27;
    }


    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        {
            if (MathUtility.isHovered(mouseX, mouseY, x + 5, y + 12, width - 10, 10)) {
                focused = true;
            } else {
                focused = false;
            }
        }
    }



    public void keyTyped(int keyCode) {
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            focused = false;
        }
        if (keyCode == GLFW.GLFW_KEY_DELETE || keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            if (set.text.isEmpty()) return;
            StringBuilder b = new StringBuilder();
            int i = 0;
            for (char c : set.text.toCharArray()) {
                if (i < set.text.length() - 1)
                    b.append(c);
                i++;
            }
            set.text = b.toString();
        }
    }

    public void charTyped(char codePoint, int modifiers) {
        if (Fonts.INTER_BOLD.get(13).getWidth(set.text != null ? set.text : "") > width - 20) return;
        StringBuilder b = new StringBuilder();
        int i = 0;
        for (char c : set.text.toCharArray()) {
            if (i < set.text.length() - 1)
                b.append(c);
            else
                b.append(c + "" + codePoint);
            i++;
        }
        set.text = set.text.isEmpty() ? String.valueOf(codePoint) : b.toString();
    }
}
