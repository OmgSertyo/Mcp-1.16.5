package sertyo.events.ui.gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;
import sertyo.events.Main;
import sertyo.events.manager.config.Config;
import sertyo.events.manager.config.ConfigManager;
import sertyo.events.manager.theme.Theme;
import sertyo.events.manager.theme.Themes;
import sertyo.events.module.api.Category;
import sertyo.events.ui.gui.component.Component;
import sertyo.events.ui.gui.component.impl.ConfigComponent;
import sertyo.events.ui.gui.component.impl.ModuleComponent;
import sertyo.events.ui.gui.component.impl.ThemeComponent;
import sertyo.events.ui.gui.window.ColorPickerWindow;
import sertyo.events.util.Mouse;
import sertyo.events.util.Util;
import sertyo.events.util.animation.Animation;
import sertyo.events.util.animation.AnimationMath;
import sertyo.events.util.animation.Direction;
import sertyo.events.util.animation.impl.DecelerateAnimation;
import sertyo.events.util.animation.impl.EaseInOutQuad;
import sertyo.events.util.math.MathUtility;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.StencilUtility;
import sertyo.events.util.render.font.Fonts;

import static org.lwjgl.glfw.GLFW.*;

public class Gui extends Screen  {
    public int x;
    public int y;
    public static Category selected;
    public static ColorPickerWindow colorPicker;
    public static List<ModuleComponent> modules;
    public static List<ModuleComponent> modules2;
    public static List<ThemeComponent> guiThemes;
    public static List<ThemeComponent> themes;
    public static List<ConfigComponent> configs;
    public static List<ConfigComponent> configs2;
    private Animation openAnimation;
    private static final Animation moduleAnimation;
    public static boolean escapeInUse;
    public boolean isClosed;
    public float scrollY = 32.5F;
    private float text1Y = 0.0F;
    private float text2Y = 0.0F;

    public Gui() {
        super(new StringTextComponent("SOSOSISSISISISI"));
        this.x = Minecraft.getInstance().getMainWindow().getScaledWidth() / 2 - this.width / 2 + 90;
        this.y = Minecraft.getInstance().getMainWindow().getScaledHeight() / 2 - this.height / 2;
        Main.getInstance().getModuleManager().getModules().forEach((module) -> {
            if (Main.getInstance().getModuleManager().getModules().indexOf(module) % 2 == 0) {
                modules.add(new ModuleComponent(module, 120.0F, 30.0F));
            }

            if (Main.getInstance().getModuleManager().getModules().indexOf(module) % 2 != 0) {
                modules2.add(new ModuleComponent(module, 120.0F, 30.0F));
            }

        });
        Themes[] var2 = Themes.values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Themes theme = var2[var4];
            if (theme.getTheme().getType().equals(Theme.ThemeType.GUI)) {
                guiThemes.add(new ThemeComponent(theme.getTheme(), 55.0F, 30.0F));
            } else {
                themes.add(new ThemeComponent(theme.getTheme(), 55.0F, 30.0F));
            }
        }

    }

    public void init() {
        super.init();
        this.openAnimation = new EaseInOutQuad(250, 1.0F, Direction.FORWARDS);
        if (colorPicker != null) {
            colorPicker.init();
        }

        updateConfigComponents();
    }

    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
        super.render(ms, mouseX, mouseY, partialTicks);

        Color[] guiColors = Main.getInstance().getThemeManager().getCurrentGuiTheme().getColors();
        boolean isDark = Main.getInstance().getThemeManager().getCurrentGuiTheme().equals(Themes.DARK.getTheme());


        if (this.isClosed && this.openAnimation.isDone()) {
            Minecraft.getInstance().displayGuiScreen(null);

            this.isClosed = false;
        }
float scale = this.openAnimation.getOutput();
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)(this.x + 175), (float)((this.y + 300) / 2), 0.0F);
        GlStateManager.scalef(scale, scale, 0.0F);
        GlStateManager.translatef((float)(-(this.x + 175)), (float)(-((this.y + 300) / 2)), 0.0F);
        RenderUtility.drawRoundedRect((float)this.x, (float)this.y, 350.0F, 250.0F, 8.0F, 185.0F, 50.0F, guiColors[0].getRGB(), guiColors[1].getRGB());
        Fonts.INTER_BOLD.get(28).draw(ms, Main.name, (float)(this.x + 145), (float)(this.y - 20), (new Color(230, 230, 230)).getRGB());

        Fonts.INTER_BOLD.get(14).draw(ms, "MAIN", (float)(this.x + 10), (float)(this.y + 10), guiColors[3].getRGB());
        Category[] var9 = Category.values();

        for (Category category : var9) {
            category.getAnimation().setDirection(selected.equals(category) ? Direction.FORWARDS : Direction.BACKWARDS);
            boolean hovered = MathUtility.isHovered((float) mouseX, (float) mouseY, (float) (this.x + 10), (float) (this.y + 20 + 20 * category.ordinal() + 1 + (category.isBottom() ? 15 : 0)), 75.0F, 17.5F);

            if (selected.equals(category)) {
                RenderUtility.drawRoundedRect((float) (this.x + 10), (float) (this.y + 20 + 20 * category.ordinal() + 1 + (category.isBottom() ? 15 : 0)), 75.0F * category.getAnimation().getOutput(), 17.5F, 4.0F, guiColors[4].getRGB());
            } else if (hovered) {
                RenderUtility.drawRoundedRect((float) (this.x + 10), (float) (this.y + 20 + 20 * category.ordinal() + 1 + (category.isBottom() ? 15 : 0)), 75.0F, 17.5F, 4.0F, guiColors[5].hashCode());
            } else {
                RenderUtility.drawRoundedRect((float) (this.x + 10), (float) (this.y + 20 + 20 * category.ordinal() + 1 + (category.isBottom() ? 15 : 0)), 75.0F * category.getAnimation().getOutput(), 17.5F, 4.0F, 3);
            }

            Fonts.INTER_BOLD.get(16).draw(ms, category.getName(), (float) this.x + 32.5F, (float) (this.y + 20 + 20 * category.ordinal()) + 7.5F + (float) (category.isBottom() ? 15 : 0), isDark ? Color.WHITE.getRGB() : (new Color(65, 65, 65)).getRGB());
        }


        Fonts.INTER_BOLD.get(16).draw(ms, "Sertyo", (float)(this.x + 36), (float)(this.y + 225), isDark ? -1 : (new Color(65, 65, 65)).getRGB());
        Fonts.INTER_BOLD.get(14).draw(ms, "UID: 1", (float)(this.x + 36), (float)(this.y + 235), (new Color(120, 120, 120)).getRGB());
        StencilUtility.init();
        RenderUtility.drawRect((float)this.x + 92.5F, (float)this.y + 26.5F, 257.5F, 223.5F, -1);
        StencilUtility.read(1);
        if (selected.equals(Category.THEMES)) {
            this.drawThemes(ms, mouseX, mouseY);

        } else {
            this.drawComponents(ms, mouseX, mouseY);
        }

        RenderUtility.drawRect((float)(this.x + 99), (float)this.y + 26.5F, 245.0F, 250.0F, (new Color((float)guiColors[1].getRed() / 255.0F, (float)guiColors[1].getGreen() / 255.0F, (float)guiColors[1].getBlue() / 255.0F, moduleAnimation.getOutput())).getRGB());
        StencilUtility.cleanup();
        if (colorPicker != null) {
            colorPicker.render(mouseX, mouseY);
        }

        GlStateManager.popMatrix();
    }

    private void drawThemes(MatrixStack ms, int mouseX, int mouseY) {
        boolean isDark = Main.getInstance().getThemeManager().getCurrentGuiTheme().equals(Themes.DARK.getTheme());
        int xOffset = 0;
        GLFW.glfwSetScrollCallback(GLFW.glfwGetCurrentContext(), new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                // Обновление scrollY на основе значения прокрутки
                scrollY += (float) yoffset * 5.0F; // Делите или умножайте по необходимости
            }
        });
        float offset = this.scrollY;
        float sizeOffset1 = 0.0F;
        float sizeOffset2 = 0.0F;
        this.text1Y = AnimationMath.fast(this.text1Y, (float)this.y + offset, 15.0F);
        Fonts.INTER_BOLD.get(16).draw(ms, "Interface", (float)(this.x + 100), this.text1Y, isDark ? Color.WHITE.getRGB() : (new Color(65, 65, 65)).getRGB());

        Iterator var8;
        ThemeComponent themeComponent;
        int index;
        for(var8 = guiThemes.iterator(); var8.hasNext(); sizeOffset1 += themeComponent.height + 7.0F) {
            themeComponent = (ThemeComponent)var8.next();
            index = guiThemes.indexOf(themeComponent);
            themeComponent.setX((float)(this.x + 100) + (float)xOffset * (themeComponent.width + 8.0F));
            themeComponent.setY(AnimationMath.fast(themeComponent.y, (float)(this.y + 12) + offset, 15.0F));
            themeComponent.render(ms, mouseX, mouseY);
            ++xOffset;
            if ((index + 1) % 4 == 0) {
                offset += themeComponent.height + 7.0F;
                xOffset = 0;
            }
        }

        this.text2Y = AnimationMath.fast(this.text2Y, (float)(this.y + 49) + offset, 15.0F);
        Fonts.INTER_BOLD.get(16).draw(ms, "Styles", (float)(this.x + 100), this.text2Y, isDark ? Color.WHITE.getRGB() : (new Color(65, 65, 65)).getRGB());
        xOffset = 0;
        offset = this.scrollY;

        for(var8 = themes.iterator(); var8.hasNext(); sizeOffset2 += themeComponent.height + 7.0F) {
            themeComponent = (ThemeComponent)var8.next();
            index = themes.indexOf(themeComponent);
            themeComponent.setX((float)(this.x + 100) + (float)xOffset * (themeComponent.width + 8.0F));
            themeComponent.setY(AnimationMath.fast(themeComponent.y, (float)(this.y + 61) + offset, 15.0F));
            themeComponent.render(ms, mouseX, mouseY);
            ++xOffset;
            if ((index + 1) % 4 == 0) {
                offset += themeComponent.height + 7.0F;
                xOffset = 0;
            }
        }

        float scrollMax = Math.max(sizeOffset1, sizeOffset2);
        if (scrollMax > 250.0F) {
            this.scrollY = MathHelper.clamp(this.scrollY, -scrollMax, 32.5F);
        } else {
            this.scrollY = 32.5F;
        }

    }
    private void drawComponents(MatrixStack ms,int mouseX, int mouseY) {
        List<ModuleComponent> categoryModules1 = (List)modules.stream().filter((module) -> {
            return module.getModule().getCategory().equals(selected);
        }).collect(Collectors.toList());
        List<ModuleComponent> categoryModules2 = (List)modules2.stream().filter((module) -> {
            return module.getModule().getCategory().equals(selected);
        }).collect(Collectors.toList());
        GLFW.glfwSetScrollCallback(GLFW.glfwGetCurrentContext(), new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollY += (float) yoffset * 5.0F;
            }
        });
        float offset = this.scrollY;
        float offset1 = 32.5F;
        float offset2 = 32.5F;
        Iterator var8 = categoryModules1.iterator();

        while(true) {
            ModuleComponent moduleElement;
            Iterator var10;
            Component element;
            do {
                if (!var8.hasNext()) {
                    offset = this.scrollY;
                    var8 = categoryModules2.iterator();

                    while(true) {
                        do {
                            if (!var8.hasNext()) {
                                float scrollMax = Math.max(offset1, offset2);
                                if (scrollMax > 250.0F) {
                                    this.scrollY = MathHelper.clamp(this.scrollY, -scrollMax + 277.0F, 32.5F);
                                } else {
                                    this.scrollY = 32.5F;
                                }

                                return;
                            }

                            moduleElement = (ModuleComponent)var8.next();
                            moduleElement.setX((float)(this.x + 224));
                            moduleElement.setY(AnimationMath.fast(moduleElement.y, (float)this.y + offset, 15.0F));
                        } while(!moduleElement.getModule().isSearched());

                        var10 = moduleElement.elements.iterator();

                        while(var10.hasNext()) {
                            element = (Component)var10.next();
                            if (element.isVisible()) {
                                offset += element.height;
                                offset2 += element.height;
                            }
                        }

                        moduleElement.render(ms, mouseX, mouseY);
                        offset += moduleElement.height + 5.0F;
                        offset2 += moduleElement.height + 5.0F;
                    }
                }

                moduleElement = (ModuleComponent)var8.next();
                moduleElement.setX((float)(this.x + 100));
                moduleElement.setY(AnimationMath.fast(moduleElement.y, (float)this.y + offset, 15.0F));
            } while(!moduleElement.getModule().isSearched());

            var10 = moduleElement.elements.iterator();

            while(var10.hasNext()) {
                element = (Component)var10.next();
                if (element.isVisible()) {
                    offset += element.height;
                    offset1 += element.height;
                }
            }

            moduleElement.render(ms, mouseX, mouseY);
            offset += moduleElement.height + 5.0F;
            offset1 += moduleElement.height + 5.0F;
        }
    }
    public static void updateConfigComponents() {
        configs.clear();
        configs2.clear();
        List<Config> allConfigs = ConfigManager.getLoadedConfigs();
        Iterator var1 = allConfigs.iterator();

        while(var1.hasNext()) {
            Config config = (Config)var1.next();
            if (allConfigs.indexOf(config) % 2 == 0) {
                configs.add(new ConfigComponent(config.getName(), 120.0F, 30.0F));
            }

            if (allConfigs.indexOf(config) % 2 != 0) {
                configs2.add(new ConfigComponent(config.getName(), 120.0F, 30.0F));
            }
        }

    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) {
            Category[] var4 = Category.values();
            int var5 = var4.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Category category = var4[var6];
                boolean hovered = MathUtility.isHovered((float) mouseX, (float) mouseY, (float) (this.x + 10), (float) (this.y + 20 + 20 * category.ordinal() + 1 + (category.isBottom() ? 15 : 0)), 75.0F, 17.5F);
                if (hovered && !selected.equals(category)) {
                    selected = category;
                    this.scrollY = 32.5F;
                    moduleAnimation.reset();
                }
            }
        }

        if (colorPicker != null) {
            colorPicker.mouseClicked((double) mouseX, (double) mouseY, mouseButton);
        } else {
            Iterator var9 = modules.iterator();

            ModuleComponent moduleComponent;
            while (var9.hasNext()) {
                moduleComponent = (ModuleComponent) var9.next();
                if (moduleComponent.getModule().getCategory().equals(selected) && moduleComponent.getModule().isSearched()) {
                    moduleComponent.mouseClicked((double) mouseX, (double) mouseY, mouseButton);
                }
            }

            var9 = modules2.iterator();

            while (var9.hasNext()) {
                moduleComponent = (ModuleComponent) var9.next();
                if (moduleComponent.getModule().getCategory().equals(selected) && moduleComponent.getModule().isSearched()) {
                    moduleComponent.mouseClicked((double) mouseX, (double) mouseY, mouseButton);
                }
            }

            if (selected.equals(Category.THEMES)) {
                var9 = guiThemes.iterator();

                ThemeComponent themeComponent;
                while (var9.hasNext()) {
                    themeComponent = (ThemeComponent) var9.next();
                    themeComponent.mouseClicked((double) mouseX, (double) mouseY, mouseButton);
                }

                var9 = themes.iterator();

                while (var9.hasNext()) {
                    themeComponent = (ThemeComponent) var9.next();
                    themeComponent.mouseClicked((double) mouseX, (double) mouseY, mouseButton);
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);

    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (colorPicker != null) {
            colorPicker.mouseReleased((double)mouseX, (double)mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        Iterator var3 = modules.iterator();

        ModuleComponent moduleComponent;
        while(var3.hasNext()) {
            moduleComponent = (ModuleComponent)var3.next();
            if (moduleComponent.getModule().getCategory().equals(selected) && moduleComponent.getModule().isSearched()) {
                moduleComponent.keyTyped(keyCode);
            }
        }

        var3 = modules2.iterator();

        while(var3.hasNext()) {
            moduleComponent = (ModuleComponent)var3.next();
            if (moduleComponent.getModule().getCategory().equals(selected) && moduleComponent.getModule().isSearched()) {
                moduleComponent.keyTyped(keyCode);
            }
        }

        if (keyCode == 1 && !escapeInUse) {
            this.openAnimation.setDirection(Direction.BACKWARDS);
            this.openAnimation.setDuration(225);
            this.isClosed = true;
        } else {
            escapeInUse = false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean isPauseScreen() {
        return false;
    }

    public static float getAnimationAlpha() {
        return moduleAnimation.getOutput();
    }

    static {
        selected = Category.COMBAT;
        modules = new ArrayList();
        modules2 = new ArrayList();
        guiThemes = new ArrayList();
        themes = new ArrayList();
        configs = new ArrayList();
        configs2 = new ArrayList();
        moduleAnimation = new DecelerateAnimation(500, 1.0F, Direction.BACKWARDS);
    }
}
