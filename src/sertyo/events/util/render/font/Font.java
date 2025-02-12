package sertyo.events.util.render.font;


import com.google.common.base.Preconditions;

import lombok.Cleanup;
import me.sertyo.j2c.J2c;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.TextFormatting;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import sertyo.events.util.render.ColorUtil;

import java.io.InputStream;
import java.util.Locale;

import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings("unused")
public class Font {
    private float posX, posY;
    private final int[] colorCode = new int[32];
    private final GlyphPage regularGlyphPage;

    public Font(GlyphPage regularGlyphPage) {
        this.regularGlyphPage = regularGlyphPage;

        for (int i = 0; i < 32; ++i) {
            int red = (i >> 3 & 1) * 85;
            int green = (i >> 2 & 1) * 170 + red;
            int blue = (i >> 1 & 1) * 170 + red;
            int color = (i & 1) * 170 + red;

            if (i == 6) {
                green += 85;
            }

            if (i >= 16) {
                green /= 4;
                blue /= 4;
                color /= 4;
            }

            this.colorCode[i] = (green & 255) << 16 | (blue & 255) << 8 | color & 255;
        }
    }

    @J2c
    public static Font create(String file, float size) {

        java.awt.Font font = null;

        try {
            @Cleanup InputStream in = Preconditions.checkNotNull(Font.class.getResourceAsStream("/assets/neiron/font/" + file), "Font resource is null");
            font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, in)
                    .deriveFont(java.awt.Font.PLAIN, size);
        } catch (Exception ignored) {
        }

        GlyphPage regularPage = new GlyphPage(font, true, true);
        regularPage.generateGlyphPage();
        regularPage.setupTexture();
        return new Font(regularPage);
    }

    public void drawGradient(MatrixStack matrix, String text, double x, double y, int firstColor, int secondColor) {
        int[] colors = ColorUtil.genGradientForText(firstColor, secondColor, text.length());
        float xOffset = 0;
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            int charColor = colors[i];
            String charString = String.valueOf(character);
            draw(matrix, charString, x + xOffset, y, charColor);
            xOffset += getWidth(charString);
        }
    }

    public void draw(MatrixStack matrix, String text, float x, float y, int color) {
        draw(matrix, text, x, y, color, false);
    }

    public void draw(MatrixStack matrix, String text, double x, double y, int color) {
        draw(matrix, text, (float) x, (float) y, color, false);
    }

    public void drawRight(MatrixStack matrix, String text, float x, float y, int color) {
        draw(matrix, text, x - (getWidth(text)), y, color, false);
    }

    public void drawRight(MatrixStack matrix, String text, double x, double y, int color) {
        draw(matrix, text, (float) (x - (getWidth(text))), (float) y, color, false);
    }

    public void drawShadow(MatrixStack matrix, String text, float x, float y, int color) {
        draw(matrix, text, x, y, color, true);
    }
    public static final String STYLE_CODES = "0123456789abcdefklmnor";
    public static final int[] COLOR_CODES = new int[32];
    static float renderCustomStringGradient(MatrixStack matrices, Font font, String text, double x, double y,int factor,int speed,int... colors) {
        y -= 3;
        GL11.glColor4f(1, 1, 1, 1);

        float startPos = (float) x * 2.0f;
        float posX = startPos;
        float posY = (float) y * 2.0f;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        matrices.push();
        matrices.scale(0.5f, 0.5f, 1f);

        Matrix4f matrix = matrices.getLast().getMatrix();
        int length = text.length();
        String lowerCaseText = text.toLowerCase();

        for (int i = 0; i < length; i++) {
            char c0 = text.charAt(i);

            if (c0 == 167 && i + 1 < length && STYLE_CODES.indexOf(lowerCaseText.charAt(i + 1)) != -1) {
                int i1 = STYLE_CODES.indexOf(lowerCaseText.charAt(i + 1));

                if (i1 < 16) {

                    int j1 = COLOR_CODES[i1];
                }
                i++;
            } else {
                posX += font.renderCustomGradienGlyph(matrix, c0, posX, posY, false, false,factor,speed,colors);
            }
        }

        matrices.pop();
        GlStateManager.disableBlend();

        return (posX - startPos) / 2.0f;
    }
    public float renderCustomGradienGlyph(Matrix4f matrix, char c, float x, float y, boolean bold, boolean italic,int factor,int speed,int... colors) {
        return -1;
    }
public static float drawCustomGradientString(MatrixStack matrices, Font font, String text, double x, double y,int factor,int speed,int... colors) {
    return renderCustomStringGradient(matrices, font, text, x, y,factor,speed,colors);
}
    public void drawgradientt(MatrixStack matrixStack, String text, double x, double y,int factor,int speed,int... colors) {
        drawCustomGradientString(matrixStack, this, text, x, y,factor,speed,colors);
    }
    public void drawShadow(MatrixStack matrix, String text, double x, double y, int color) {
        draw(matrix, text, (float) x, (float) y, color, true);
    }

    public void drawRightShadow(MatrixStack matrix, String text, float x, float y, int color) {
        draw(matrix, text, x - (getWidth(text)), y, color, true);
    }

    public void drawRightShadow(MatrixStack matrix, String text, double x, double y, int color) {
        draw(matrix, text, (float) (x - (getWidth(text))), (float) y, color, true);
    }

    public void drawCenter(MatrixStack matrix, String text, float x, float y, int color) {
        draw(matrix, text, x - getWidth(text) / 2f, y, color, false);
    }

    public void drawCenter(MatrixStack matrix, String text, double x, double y, int color) {
        draw(matrix, text, (float) x - getWidth(text) / 2f, (float) y, color, false);
    }

    public void drawCenterShadow(MatrixStack matrix, String text, float x, float y, int color) {
        draw(matrix, text, x - getWidth(text) / 2f, y, color, true);
    }

    public void drawCenterShadow(MatrixStack matrix, String text, double x, double y, int color) {
        draw(matrix, text, (float) x - getWidth(text) / 2f, (float) y, color, true);
    }

    public void drawOutline(MatrixStack matrix, String text, double x, double y, int color) {
        drawOutline(matrix, text, x, y, color, 0.25F);
    }

    public void drawOutline(MatrixStack matrix, String text, double x, double y, int color, float multDark) {
        float outline = 0.5F;
        int alpha = ColorUtil.alpha(color);
        int outlineColor = ColorUtil.replAlpha(ColorUtil.multDark(color, multDark), alpha);
        draw(matrix, TextFormatting.getTextWithoutFormattingCodes(text), x, y + outline, outlineColor);
        draw(matrix, TextFormatting.getTextWithoutFormattingCodes(text), x, y - outline, outlineColor);
        draw(matrix, TextFormatting.getTextWithoutFormattingCodes(text), x + outline, y, outlineColor);
        draw(matrix, TextFormatting.getTextWithoutFormattingCodes(text), x - outline, y, outlineColor);
        draw(matrix, text, x, y, color);
    }

    public void drawOutline(MatrixStack matrix, String text, float x, float y, int color) {
        drawOutline(matrix, text, x, y, color, 0.25F);

    }

    public void drawOutline(MatrixStack matrix, String text, float x, float y, int color, float multDark) {
        float outline = 0.5F;
        int alpha = ColorUtil.alpha(color);
        int outlineColor = ColorUtil.replAlpha(ColorUtil.multDark(color, multDark), alpha);
        draw(matrix, TextFormatting.getTextWithoutFormattingCodes(text), x, y + outline, outlineColor);
        draw(matrix, TextFormatting.getTextWithoutFormattingCodes(text), x, y - outline, outlineColor);
        draw(matrix, TextFormatting.getTextWithoutFormattingCodes(text), x + outline, y, outlineColor);
        draw(matrix, TextFormatting.getTextWithoutFormattingCodes(text), x - outline, y, outlineColor);
        draw(matrix, text, x, y, color);
    }

    public void drawRightOutline(MatrixStack matrix, String text, float x, float y, int color) {
        drawOutline(matrix, text, x - (getWidth(text)), y, color);
    }

    public void drawRightOutline(MatrixStack matrix, String text, double x, double y, int color) {
        drawOutline(matrix, text, (float) (x - (getWidth(text))), (float) y, color);
    }


    public void drawCenterOutline(MatrixStack matrix, String text, float x, float y, int color) {
        drawOutline(matrix, text, x - getWidth(text) / 2f, y, color);
    }

    public void drawCenterOutline(MatrixStack matrix, String text, double x, double y, int color) {
        drawOutline(matrix, text, (float) x - getWidth(text) / 2f, (float) y, color);
    }

    public void draw(MatrixStack matrix, String text, float x, float y, int color, boolean dropShadow) {
        if (dropShadow) {
            this.renderString(matrix, text, x + 0.5F, y + 0.5F, color, true);
        }
        this.renderString(matrix, text, x, y, color, false);
    }

    private void renderString(MatrixStack matrix, String text, float x, float y, int color, boolean dropShadow) {
        if (text != null) {
            if ((color & -67108864) == 0) {
                color |= -16777216;
            }

            if (dropShadow) {
                color = ColorUtil.multDark(color, 0.2F);
            }
            this.posX = ((x - 1) * 2);
            this.posY = (y * 2);
            this.renderStringAtPos(matrix, text, dropShadow, color);
        }
    }

    private void renderStringAtPos(MatrixStack matrixStack, String text, boolean hasShadow, int color) {


        GlyphPage currentGlyphPage = getCurrentGlyphPage();
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        matrixStack.push();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableTexture();
        GlStateManager.clearCurrentColor();
        currentGlyphPage.bindTexture();
        matrixStack.scale(0.5F, 0.5F, 0.5F);
        for (int i = 0; i < text.length(); ++i) {
            char character = text.charAt(i);
            if (character == 167 && i + 1 < text.length()) {
                int colorIndex = "0123456789abcdefr".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
                if (colorIndex < 16) {
                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }
                    if (hasShadow) {
                        colorIndex += 16;
                    }
                    int colorCode = this.colorCode[colorIndex];
                    red = (float) (colorCode >> 16 & 255) / 255.0F;
                    green = (float) (colorCode >> 8 & 255) / 255.0F;
                    blue = (float) (colorCode & 255) / 255.0F;
                } else {
                    red = (float) (color >> 16 & 255) / 255.0F;
                    green = (float) (color >> 8 & 255) / 255.0F;
                    blue = (float) (color & 255) / 255.0F;
                }
                ++i;
            } else {
                currentGlyphPage = getCurrentGlyphPage();
                currentGlyphPage.bindTexture();
                double charWidth = currentGlyphPage.drawChar(matrixStack, character, posX, posY, red, blue, green, alpha);
                RenderSystem.texParameter(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                this.posX += (float) charWidth;
            }
        }
        currentGlyphPage.unbindTexture();
        GlStateManager.disableBlend();
        matrixStack.pop();
    }

    private GlyphPage getCurrentGlyphPage() {
        return regularGlyphPage;
    }

    public float getHeight() {
        return regularGlyphPage.getMaxFontHeight() / 2F;
    }


    public float getWidth(String str) {
        String text = TextFormatting.getTextWithoutFormattingCodes(str);
        if (text == null) {
            return 0;
        }
        float width = 0;
        GlyphPage currentPage;
        int size = text.length();
        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);
            currentPage = getCurrentGlyphPage();
            width += currentPage.getWidth(character) - 8;
        }
        return width / 2F;
    }


    public String trimStringToWidth(String text, int width) {
        return this.trimStringToWidth(text, width, false);
    }

    public String trimStringToWidth(String str, int maxWidth, boolean reverse) {
        StringBuilder stringBuilder = new StringBuilder();
        String text = TextFormatting.getTextWithoutFormattingCodes(str);
        int startIndex = reverse ? text.length() - 1 : 0;
        int increment = reverse ? -1 : 1;
        int currentWidth = 0;
        GlyphPage currentPage;
        for (int i = startIndex; i >= 0 && i < text.length() && i < maxWidth; i += increment) {
            char character = text.charAt(i);
            currentPage = getCurrentGlyphPage();
            currentWidth += (int) ((currentPage.getWidth(character) - 8) / 2);
            if (i > currentWidth) {
                break;
            }
            if (reverse) {
                stringBuilder.insert(0, character);
            } else {
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }


}