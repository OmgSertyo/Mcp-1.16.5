package sertyo.events.util.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import jhlabs.image.GaussianFilter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;
import sertyo.events.util.Util;
import sertyo.events.util.render.font.Font;
import sertyo.events.util.render.font.Fonts;
import sertyo.events.util.render.shader.Shader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;

import static com.mojang.blaze3d.platform.GlStateManager.*;
import static com.mojang.blaze3d.platform.GlStateManager.GL_QUADS;
import static com.mojang.blaze3d.systems.RenderSystem.enableBlend;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR_TEX;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_TEX;
import static org.lwjgl.opengl.GL11.*;

public class RenderUtility implements Util {
    private static final Shader ROUNDED_GRADIENT;
    private static final Shader ROUNDED;
    private static final Shader GRADIENT_MASK;
    private static final Shader ROUNDED_TEXTURE;
    private static final Shader head;
    private static final Shader text2;
    public static void drawShadow(float x, float y, float width, float height, int radius, int startColor, int endColor) {


        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(GL_GREATER, 0.01f);

        x -= radius;
        y -= radius;
        width = width + radius * 2;
        height = height + radius * 2;
        x -= 0.25f;
        y += 0.25f;

        int identifier = Objects.hash(width, height, radius);
        int textureId;

        if (shadowCache.containsKey(identifier)) {
            textureId = shadowCache.get(identifier);
            GlStateManager.bindTexture(textureId);
        } else {
            if (width <= 0) {
                width = 1;
            }

            if (height <= 0) {
                height = 1;
            }

            BufferedImage originalImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D graphics = originalImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(radius, radius, (int) (width - radius * 2), (int) (height - radius * 2));
            graphics.dispose();

            GaussianFilter filter = new GaussianFilter(radius);
            BufferedImage blurredImage = filter.filter(originalImage, null);
            DynamicTexture texture = new DynamicTexture(TextureUtils.toNativeImage(blurredImage));
            texture.setBlurMipmap(true, true);
            try {
                textureId = texture.getGlTextureId();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            shadowCache.put(identifier, textureId);
        }

        float[] startColorComponents = ColorUtil.IntColor.rgb(startColor);
        float[] endColorComponents = ColorUtil.IntColor.rgb(endColor);

        BUFFER.begin(GL_QUADS, POSITION_COLOR_TEX);
        BUFFER.pos(x, y, 0.0f)
                .color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3])
                .tex(0.0f, 0.0f)
                .endVertex();

        BUFFER.pos(x, y + (float) ((int) height), 0.0f)
                .color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3])
                .tex(0.0f, 1.0f)
                .endVertex();

        BUFFER.pos(x + (float) ((int) width), y + (float) ((int) height), 0.0f)
                .color(endColorComponents[0], endColorComponents[1], endColorComponents[2], endColorComponents[3])
                .tex(1.0f, 1.0f)
                .endVertex();

        BUFFER.pos(x + (float) ((int) width), y, 0.0f)
                .color(endColorComponents[0], endColorComponents[1], endColorComponents[2], endColorComponents[3])
                .tex(1.0f, 0.0f)
                .endVertex();

        TESSELLATOR.draw();

        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static void renderString(Font font, String text, float x, float y, float maxWidth, int color) {
        text2.useProgram();
        text2.setupUniform4f("inColor", (float) (color >> 16 & 0xFF) / 255.0f, (float) (color >> 8 & 0xFF) / 255.0f, (float) (color & 0xFF) / 255.0f, (float) (color >> 24 & 0xFF) / 255.0f);
        text2.setupUniform1f("width", maxWidth);
        text2.setupUniform1f("maxWidth", (x + maxWidth) * 2);
        font.draw(new MatrixStack(),text, x, y, color);
        text2.unloadProgram();
    }

    private static final HashMap<Integer, Integer> shadowCache = new HashMap<>();
    public static void drawShadow(float x, float y, float width, float height, int radius, int bottomLeft, int topLeft, int bottomRight, int topRight) {


        GlStateManager.blendFuncSeparate(SourceFactor.SRC_ALPHA.param,
                DestFactor.ONE_MINUS_SRC_ALPHA.param, SourceFactor.ONE.param,
                DestFactor.ZERO.param);
        GlStateManager.shadeModel(7425);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(GL_GREATER, 0.01f);

        x -= radius;
        y -= radius;
        width = width + radius * 2;
        height = height + radius * 2;
        x -= 0.25f;
        y += 0.25f;

        int identifier = Objects.hash(width, height, radius);
        int textureId;

        if (shadowCache.containsKey(identifier)) {
            textureId = shadowCache.get(identifier);
            GlStateManager.bindTexture(textureId);
        } else {
            if (width <= 0) {
                width = 1;
            }

            if (height <= 0) {
                height = 1;
            }

            BufferedImage originalImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D graphics = originalImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(radius, radius, (int) (width - radius * 2), (int) (height - radius * 2));
            graphics.dispose();

            GaussianFilter filter = new GaussianFilter(radius);
            BufferedImage blurredImage = filter.filter(originalImage, null);
            DynamicTexture texture = new DynamicTexture(TextureUtils.toNativeImage(blurredImage));
            texture.setBlurMipmap(true, true);
            try {
                textureId = texture.getGlTextureId();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            shadowCache.put(identifier, textureId);
        }

        float[] bottomLefts = ColorUtil.IntColor.rgb(bottomLeft);
        float[] topLefts = ColorUtil.IntColor.rgb(topLeft);
        float[] bottomRights = ColorUtil.IntColor.rgb(bottomRight);
        float[] topRights = ColorUtil.IntColor.rgb(topRight);

        BUFFER.begin(GL_QUADS, POSITION_COLOR_TEX);
        BUFFER.pos(x, y, 0.0f)
                .color(bottomLefts[0], bottomLefts[1], bottomLefts[2], bottomLefts[3])
                .tex(0.0f, 0.0f)
                .endVertex();

        BUFFER.pos(x, y + (float) ((int) height), 0.0f)
                .color(topLefts[0], topLefts[1], topLefts[2], topLefts[3])
                .tex(0.0f, 1.0f)
                .endVertex();

        BUFFER.pos(x + (float) ((int) width), y + (float) ((int) height), 0.0f)
                .color(topRights[0], topRights[1], topRights[2], topRights[3])
                .tex(1.0f, 1.0f)
                .endVertex();

        BUFFER.pos(x + (float) ((int) width), y, 0.0f)
                .color(bottomRights[0], bottomRights[1], bottomRights[2], bottomRights[3])
                .tex(1.0f, 0.0f)
                .endVertex();

        TESSELLATOR.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static void quadsBegin(float x, float y, float width, float height, int glQuads) {
        BUFFER.begin(glQuads, POSITION_TEX);
        {
            BUFFER.pos(x, y, 0).tex(0, 0).endVertex();
            BUFFER.pos(x, y + height, 0).tex(0, 1).endVertex();
            BUFFER.pos(x + width, y + height, 0).tex(1, 1).endVertex();
            BUFFER.pos(x + width, y, 0).tex(1, 0).endVertex();
        }
        TESSELLATOR.draw();
    }
    public static void drawHead(ResourceLocation skin,float x, float y, float width, float height, float radius, float alpha, float hurtPercent){
        mc.getTextureManager().bindTexture(skin); // биндим текстуру головы игрока
        pushMatrix();
        enableBlend();
        head.useProgram(); // атачим шейдер для рендера
        head.setupUniform2f("size", width,height); //ставим размер головы
        head.setupUniform1f("radius", radius); // ставим закругление головы
        head.setupUniform1f("hurt_time", hurtPercent); //дамаг энтити (красное затемнение)
        head.setupUniform1f("alpha", alpha); // прозрачность

        head.setupUniform1f("startX", 4); // не менять
        head.setupUniform1f("startY", 4); // тоже не менять

        head.setupUniform1f("endX", 8); // опять не менять
        head.setupUniform1f("endY", 8); //не менять

        head.setupUniform1f("texXSize", 32); //размер текстурки (НЕ МЕНЯТЬ)
        head.setupUniform1f("texYSize", 32); //не менять тоже размер текстурки
        head.drawQuads(x+2, y+2, width, height); //рисуем голову на координатах
        head.unloadProgram(); // детачим шейдер чтобы gl11 нас не бом бомнул
        disableBlend();
        popMatrix();
    }

    public static void drawRoundedGradientRect(float x, float y, float x2, float y2, float round, float value, int color1, int color2, int color3, int color4) {
        float[] c1 = ColorUtil.getRGBAf(color1);
        float[] c2 = ColorUtil.getRGBAf(color2);
        float[] c3 = ColorUtil.getRGBAf(color3);
        float[] c4 = ColorUtil.getRGBAf(color4);
        GlStateManager.color((int) 0.0F, (int) 0.0F, (int) 0.0F, (int) 0.0F);
        GlStateManager.enableBlend();
        ROUNDED_GRADIENT.useProgram();
        ROUNDED_GRADIENT.setupUniform2f("size", x2 * 2.0F, y2 * 2.0F);
        ROUNDED_GRADIENT.setupUniform4f("round", round, round, round, round);
        ROUNDED_GRADIENT.setupUniform2f("smoothness", 0.0F, 1.5F);
        ROUNDED_GRADIENT.setupUniform1f("value", value);
        ROUNDED_GRADIENT.setupUniform4f("color1", c1[0], c1[1], c1[2], c1[3]);
        ROUNDED_GRADIENT.setupUniform4f("color2", c2[0], c2[1], c2[2], c2[3]);
        ROUNDED_GRADIENT.setupUniform4f("color3", c3[0], c3[1], c3[2], c3[3]);
        ROUNDED_GRADIENT.setupUniform4f("color4", c4[0], c4[1], c4[2], c4[3]);
        allocTextureRectangle(x, y, x2, y2);
        ROUNDED_GRADIENT.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void drawRoundedGradientRect(float x, float y, float x2, float y2, float round, int color1, int color2, int color3, int color4) {
        float[] c1 = ColorUtil.getRGBAf(color1);
        float[] c2 = ColorUtil.getRGBAf(color2);
        float[] c3 = ColorUtil.getRGBAf(color3);
        float[] c4 = ColorUtil.getRGBAf(color4);
        GlStateManager.color((int) 0.0F, (int) 0.0F, (int) 0.0F, (int) 0.0F);
        GlStateManager.enableBlend();
        ROUNDED_GRADIENT.useProgram();
        ROUNDED_GRADIENT.setupUniform2f("size", x2 * 2.0F, y2 * 2.0F);
        ROUNDED_GRADIENT.setupUniform4f("round", round, round, round, round);
        ROUNDED_GRADIENT.setupUniform2f("smoothness", 0.0F, 1.5F);
        ROUNDED_GRADIENT.setupUniform1f("value", 1.0F);
        ROUNDED_GRADIENT.setupUniform4f("color1", c1[0], c1[1], c1[2], c1[3]);
        ROUNDED_GRADIENT.setupUniform4f("color2", c2[0], c2[1], c2[2], c2[3]);
        ROUNDED_GRADIENT.setupUniform4f("color3", c3[0], c3[1], c3[2], c3[3]);
        ROUNDED_GRADIENT.setupUniform4f("color4", c4[0], c4[1], c4[2], c4[3]);
        allocTextureRectangle(x, y, x2, y2);
        ROUNDED_GRADIENT.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void drawRoundedGradientRect(float x, float y, float x2, float y2, float round1, float round2, float round3, float round4, float value, int color1, int color2, int color3, int color4) {
        float[] c1 = ColorUtil.getRGBAf(color1);
        float[] c2 = ColorUtil.getRGBAf(color2);
        float[] c3 = ColorUtil.getRGBAf(color3);
        float[] c4 = ColorUtil.getRGBAf(color4);
        GlStateManager.color((int) 0.0F, (int) 0.0F, (int) 0.0F, (int) 0.0F);
        GlStateManager.enableBlend();
        ROUNDED_GRADIENT.useProgram();
        ROUNDED_GRADIENT.setupUniform2f("size", x2 * 2.0F, y2 * 2.0F);
        ROUNDED_GRADIENT.setupUniform4f("round", round1, round2, round3, round4);
        ROUNDED_GRADIENT.setupUniform2f("smoothness", 0.0F, 1.5F);
        ROUNDED_GRADIENT.setupUniform1f("value", value);
        ROUNDED_GRADIENT.setupUniform4f("color1", c1[0], c1[1], c1[2], c1[3]);
        ROUNDED_GRADIENT.setupUniform4f("color2", c2[0], c2[1], c2[2], c2[3]);
        ROUNDED_GRADIENT.setupUniform4f("color3", c3[0], c3[1], c3[2], c3[3]);
        ROUNDED_GRADIENT.setupUniform4f("color4", c4[0], c4[1], c4[2], c4[3]);
        allocTextureRectangle(x, y, x2, y2);
        ROUNDED_GRADIENT.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void scaleStart(float x, float y, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        GL11.glScalef(scale, scale, 1.0F);
        GL11.glTranslatef(-x, -y, 0.0F);
    }
    public static void scaleEnd() {
        GL11.glPopMatrix();
    }
    public static void drawImage(ResourceLocation tex, float x, float y, float x2, float y2) {
        mc.getTextureManager().bindTexture(tex);
        allocTextureRectangle(x, y, x2, y2);
        GlStateManager.bindTexture(0);
    }
    public static void applyRound(float width, float height, float round, float alpha, Runnable runnable) {
        GlStateManager.color((int) 0.0F, (int) 0.0F, (int) 0.0F, (int) 0.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        ROUNDED_TEXTURE.useProgram();
        ROUNDED_TEXTURE.setupUniform2f("size", (width - round) * 2.0F, (height - round) * 2.0F);
        ROUNDED_TEXTURE.setupUniform1f("round", round);
        ROUNDED_TEXTURE.setupUniform1f("alpha", alpha);
        runnable.run();
        ROUNDED_TEXTURE.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void allocTextureRectangle(float x, float y, float width, float height) {
        BUFFER.begin(7, DefaultVertexFormats.POSITION_TEX);
        BUFFER.pos((double)x, (double)y, 0.0D).tex(0.0F, 0.0F).endVertex();
        BUFFER.pos((double)x, (double)(y + height), 0.0D).tex(0.0F, 1.0F).endVertex();
        BUFFER.pos((double)(x + width), (double)(y + height), 0.0D).tex(1.0F, 1.0F).endVertex();
        BUFFER.pos((double)(x + width), (double)y, 0.0D).tex(1.0F, 0.0F).endVertex();
        TESSELLATOR.draw();
    }
    public static void drawRoundedRect(float x, float y, float x2, float y2, float round, float swapX, float swapY, int firstColor, int secondColor) {
        float[] c = ColorUtil.getRGBAf(firstColor);
        float[] c1 = ColorUtil.getRGBAf(secondColor);
        GlStateManager.color((int) 0.0F, (int) 0.0F, (int) 0.0F, (int) 0.0F);
        GlStateManager.enableBlend();
        ROUNDED.useProgram();
        ROUNDED.setupUniform2f("size", (x2 - round) * 2.0F, (y2 - round) * 2.0F);
        ROUNDED.setupUniform1f("round", round);
        ROUNDED.setupUniform2f("smoothness", 0.0F, 1.5F);
        ROUNDED.setupUniform2f("swap", swapX, swapY);
        ROUNDED.setupUniform4f("firstColor", c[0], c[1], c[2], c[3]);
        ROUNDED.setupUniform4f("secondColor", c1[0], c1[1], c1[2], c[3]);
        allocTextureRectangle(x, y, x2, y2);
        ROUNDED.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void drawRoundedRect(float x, float y, float x2, float y2, float round, int color) {
        drawRoundedGradientRect(x, y, x2, y2, round, 1.0F, color, color, color, color);
    }
    public static void drawCRoundedRect(float x, float y, float x2, float y2, float round1, float round2, float round3, float round4, int color) {
        drawRoundedGradientRect(x, y, x2, y2, round1, round2, round3, round4, 1.0F, color, color, color, color);
    }

    public static void drawRoundCircle(float x, float y, float size, float radius, int color) {
        drawRoundedRect(x - size / 2.0F, y - size / 2.0F, size, size, radius, color);
    }

    public static void applyGradientMask(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight, Runnable content) {
        GlStateManager.color((int) 0.0F, (int) 0.0F, (int) 0.0F, (int) 0.0F);
        GlStateManager.enableBlend();
        GRADIENT_MASK.useProgram();
        GRADIENT_MASK.setupUniform2f("location", x * 2.0F, (float) Minecraft.getInstance().getMainWindow().getHeight() - height * 2.0F - y * 2.0F);
        GRADIENT_MASK.setupUniform2f("rectSize", width * 2.0F, height * 2.0F);
        GRADIENT_MASK.setupUniform1f("alpha", alpha);
        GRADIENT_MASK.setupUniform3f("color1", (float)bottomLeft.getRed() / 255.0F, (float)bottomLeft.getGreen() / 255.0F, (float)bottomLeft.getBlue() / 255.0F);
        GRADIENT_MASK.setupUniform3f("color2", (float)topLeft.getRed() / 255.0F, (float)topLeft.getGreen() / 255.0F, (float)topLeft.getBlue() / 255.0F);
        GRADIENT_MASK.setupUniform3f("color3", (float)bottomRight.getRed() / 255.0F, (float)bottomRight.getGreen() / 255.0F, (float)bottomRight.getBlue() / 255.0F);
        GRADIENT_MASK.setupUniform3f("color4", (float)topRight.getRed() / 255.0F, (float)topRight.getGreen() / 255.0F, (float)topRight.getBlue() / 255.0F);
        content.run();
        GRADIENT_MASK.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void drawGradientRect(float x, float y, float width, float height, int color1, int color2, int color3, int color4) {
        float[] c1 = ColorUtil.getRGBAf(color1);
        float[] c2 = ColorUtil.getRGBAf(color2);
        float[] c3 = ColorUtil.getRGBAf(color3);
        float[] c4 = ColorUtil.getRGBAf(color4);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.shadeModel(7425);
        BUFFER.begin(7, DefaultVertexFormats.POSITION_COLOR);
        BUFFER.pos((double)x, (double)(height + y), 0.0D).color(c1[0], c1[1], c1[2], c1[3]).endVertex();
        BUFFER.pos((double)(width + x), (double)(height + y), 0.0D).color(c2[0], c2[1], c2[2], c2[3]).endVertex();
        BUFFER.pos((double)(width + x), (double)y, 0.0D).color(c3[0], c3[1], c3[2], c3[3]).endVertex();
        BUFFER.pos((double)x, (double)y, 0.0D).color(c4[0], c4[1], c4[2], c4[3]).endVertex();
        TESSELLATOR.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }
    public static void drawRectWithOutline(float x, float y, float width, float height, int color, int outlineColor) {
        drawRect(x - 0.5F, y - 0.5F, width + 1.0F, height + 1.0F, outlineColor);
        drawRect(x, y, width, height, color);
    }
    public static void drawRect(float x, float y, float x2, float y2, int color) {
        drawGradientRect(x, y, x2, y2, color, color, color, color);
    }
    public static void drawRectNoWH(double left, double top, double right, double bottom, int color) {
        double j;
        if (left < right) {
            j = left;
            left = right;
            right = j;
        }

        if (top < bottom) {
            j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.color((int) f, (int) f1, (int) f2, (int) f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, top, 0.0D).endVertex();
        bufferbuilder.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

    static {
        ROUNDED_GRADIENT = new Shader("shaders/rounded_gradient.fsh", true);
        ROUNDED = new Shader("shaders/rounded.fsh", true);
        GRADIENT_MASK = new Shader("shaders/gradient_mask.fsh", true);
        ROUNDED_TEXTURE = new Shader("shaders/rounded_texture.fsh", true);
        head = new Shader("shaders/head.fsh", true);
        text2 = new Shader("shaders/text.fsh", true);
    }


}
