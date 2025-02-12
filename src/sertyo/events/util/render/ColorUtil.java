package sertyo.events.util.render;


import lombok.experimental.UtilityClass;
import net.minecraft.util.math.MathHelper;
import sertyo.events.util.math.Interpolator;
import sertyo.events.util.math.Mathf;

import java.awt.*;

@UtilityClass
public class ColorUtil {

    public static Color interpolateColorC(int color1, int color2, float amount) {
        amount = Math.min(1.0F, Math.max(0.0F, amount));
        return new Color(interpolateInt(getRed(color1), getRed(color2), (double)amount), interpolateInt(getGreen(color1), getGreen(color2), (double)amount), interpolateInt(getBlue(color1), getBlue(color2), (double)amount), interpolateInt(getAlpha(color1), getAlpha(color2), (double)amount));
    }
    public static int setAlpha(int color, int alpha) {
        Color c = new Color(color);
        return (new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha)).getRGB();
    }
    public static class IntColor {

        public static float[] rgb(final int color) {
            return new float[]{
                    (color >> 16 & 0xFF) / 255f,
                    (color >> 8 & 0xFF) / 255f,
                    (color & 0xFF) / 255f,
                    (color >> 24 & 0xFF) / 255f
            };
        }

        public static int rgba(final int r,
                               final int g,
                               final int b,
                               final int a) {
            return a << 24 | r << 16 | g << 8 | b;
        }

        public static int getRed(final int hex) {
            return hex >> 16 & 255;
        }

        public static int getGreen(final int hex) {
            return hex >> 8 & 255;
        }

        public static int getBlue(final int hex) {
            return hex & 255;
        }

        public static int getAlpha(final int hex) {
            return hex >> 24 & 255;
        }
    }
    public static int rgba(int r, int g, int b, int a) {
        return a << 24 | r << 16 | g << 8 | b;
    }
    public int red(int c) {
        return c >> 16 & 0xFF;
    }
    public static int gradient(int speed, int index, int... colors) {
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        angle = (angle > 180 ? 360 - angle : angle) + 180;
        int colorIndex = (int) (angle / 360f * colors.length);
        if (colorIndex == colors.length) {
            colorIndex--;
        }
        int color1 = colors[colorIndex];
        int color2 = colors[colorIndex == colors.length - 1 ? 0 : colorIndex + 1];
        return interpolateColor(color1, color2, angle / 360f * colors.length - colorIndex);
    }
    public static int getRed(final int hex) {
        return hex >> 16 & 255;
    }

    public static int getGreen(final int hex) {
        return hex >> 8 & 255;
    }

    public static int getBlue(final int hex) {
        return hex & 255;
    }

    public static int getAlpha(final int hex) {
        return hex >> 24 & 255;
    }
    public static int interpolateColor(int color1, int color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));

        int red1 = getRed(color1);
        int green1 = getGreen(color1);
        int blue1 = getBlue(color1);
        int alpha1 = getAlpha(color1);

        int red2 = getRed(color2);
        int green2 = getGreen(color2);
        int blue2 = getBlue(color2);
        int alpha2 = getAlpha(color2);

        int interpolatedRed = interpolateInt(red1, red2, amount);
        int interpolatedGreen = interpolateInt(green1, green2, amount);
        int interpolatedBlue = interpolateInt(blue1, blue2, amount);
        int interpolatedAlpha = interpolateInt(alpha1, alpha2, amount);

        return (interpolatedAlpha << 24) | (interpolatedRed << 16) | (interpolatedGreen << 8) | interpolatedBlue;
    }
    public static Double interpolate(double oldValue, double newValue, double interpolationValue) {
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue) {
        return interpolate(oldValue, newValue, (float) interpolationValue);
    }
    public int green(int c) {
        return c >> 8 & 0xFF;
    }

    public int blue(int c) {
        return c & 0xFF;
    }

    public int alpha(int c) {
        return c >> 24 & 0xFF;
    }

    public float redf(int c) {
        return (float) red(c) / 255.F;
    }

    public float greenf(int c) {
        return (float) green(c) / 255.F;
    }

    public float bluef(int c) {
        return (float) blue(c) / 255.F;
    }

    public float alphaf(int c) {
        return (float) alpha(c) / 255.F;
    }

    public int[] getRGBA(int c) {
        return new int[]{red(c), green(c), blue(c), alpha(c)};
    }

    public float[] getRGBAf(int c) {
        return new float[]{(float) red(c) / 255.F, (float) green(c) / 255.F, (float) blue(c) / 255.F, (float) alpha(c) / 255.F};
    }

    public int getColor(float r, float g, float b, float a) {
        return new Color((int) r, (int) g, (int) b, (int) a).getRGB();
    }

    public int getColor(int r, int g, int b, int a) {
        return new Color(r, g, b, a).getRGB();
    }

    public int getColor(int r, int g, int b) {
        return new Color(r, g, b, 255).getRGB();
    }

    public int getColor(int br, int a) {
        return new Color(br, br, br, a).getRGB();
    }

    public int getColor(int br) {
        return new Color(br, br, br, 255).getRGB();
    }

    public int replAlpha(int c, int a) {
        return getColor(red(c), green(c), blue(c), a);
    }

    public int multAlpha(int c, float apc) {
        return getColor(red(c), green(c), blue(c), (float) alpha(c) * apc);
    }

    public int multDark(int c, float brpc) {
        return getColor((float) red(c) * brpc, (float) green(c) * brpc, (float) blue(c) * brpc, (float) alpha(c));
    }

    public int overCol(int c1, int c2, float pc01) {
        return getColor((float) red(c1) * (1 - pc01) + (float) red(c2) * pc01, (float) green(c1) * (1 - pc01) + (float) green(c2) * pc01, (float) blue(c1) * (1 - pc01) + (float) blue(c2) * pc01, (float) alpha(c1) * (1 - pc01) + (float) alpha(c2) * pc01);
    }

    public int overCol(int c1, int c2) {
        return overCol(c1, c2, 0.5f);
    }

    public int RED = getColor(255, 0, 0);
    public int GREEN = getColor(0, 255, 0);
    public int BLUE = getColor(0, 0, 255);

    public Color[] genGradientForText(Color color1, Color color2, int length) {
        Color[] gradient = new Color[length];
        for (int i = 0; i < length; i++) {
            double pc = (double) i / (length - 1);
            gradient[i] = interpolate(color1, color2, pc);
        }
        return gradient;
    }

    public int[] genGradientForText(int color1, int color2, int length) {
        int[] gradient = new int[length];
        for (int i = 0; i < length; i++) {
            double pc = (double) i / (length - 1);
            gradient[i] = interpolate(color1, color2, pc);
        }
        return gradient;
    }

    public Color fade(int speed, int index, Color color) {
        final float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        angle = (angle > 180 ? 360 - angle : angle) + 180;

        Color colorHSB = new Color(Color.HSBtoRGB(hsb[0], hsb[1], (angle / 360F)));

        return new Color(
                colorHSB.getRed(),
                colorHSB.getGreen(),
                colorHSB.getBlue(),
                (int) Mathf.clamp(0, 255, color.getAlpha())
        );
    }

    public Color interpolate(Color color1, Color color2, double amount) {
        amount = 1F - amount;
        amount = (float) Mathf.clamp(0, 1, amount);
        return new Color(
                Interpolator.lerp(color1.getRed(), color2.getRed(), amount),
                Interpolator.lerp(color1.getGreen(), color2.getGreen(), amount),
                Interpolator.lerp(color1.getBlue(), color2.getBlue(), amount),
                Interpolator.lerp(color1.getAlpha(), color2.getAlpha(), amount)
        );
    }

    public int interpolate(int color1, int color2, double amount) {
        amount = (float) Mathf.clamp(0, 1, amount);
        return getColor(
                Interpolator.lerp(red(color1), red(color2), amount),
                Interpolator.lerp(green(color1), green(color2), amount),
                Interpolator.lerp(blue(color1), blue(color2), amount),
                Interpolator.lerp(alpha(color1), alpha(color2), amount)
        );
    }

    public Color lerp(int speed, int index, Color start, Color end) {
        int angle = (int) (((System.currentTimeMillis()) / speed + index) % 360);
        angle = (angle >= 180 ? 360 - angle : angle) * 2;
        return interpolate(start, end, angle / 360f);
    }

    public Color random() {
        return new Color(Color.HSBtoRGB((float) Math.random(), (float) (0.75F + (Math.random() / 4F)), (float) (0.75F + (Math.random() / 4F))));
    }

    public Color rainbowC(int speed, int index, float saturation, float brightness, float opacity) {
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        float hue = angle / 360f;
        Color color = new Color(Color.HSBtoRGB(hue, saturation, brightness));
        return new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                Math.max(0, Math.min(255, (int) (opacity * 255)))
        );
    }

    public int rainbow(int speed, int index, float saturation, float brightness, float opacity) {
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        float hue = angle / 360f;
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return getColor(
                red(color),
                green(color),
                blue(color),
                Math.max(0, Math.min(255, (int) (opacity * 255)))
        );
    }

    public Color skyRainbowC(int speed, int index) {
        double angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        return Color.getHSBColor(
                ((angle %= 360) / 360.0) < 0.5 ? -((float) (angle / 360.0)) : (float) (angle / 360.0),
                0.5F,
                1.0F
        );
    }

    public int skyRainbow(int speed, int index) {
        double angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        return Color.getHSBColor(
                ((angle %= 360) / 360.0) < 0.5 ? -((float) (angle / 360.0)) : (float) (angle / 360.0),
                0.5F,
                1.0F
        ).hashCode();
    }

    public Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) Mathf.clamp(0, 255, alpha));
    }

    public static int getOverallColorFrom(int color1, int color2, float percentTo2) {
        final int finalRed = (int) Mathf.lerp(color1 >> 16 & 0xFF, color2 >> 16 & 0xFF, percentTo2),
                finalGreen = (int) Mathf.lerp(color1 >> 8 & 0xFF, color2 >> 8 & 0xFF, percentTo2),
                finalBlue = (int) Mathf.lerp(color1 & 0xFF, color2 & 0xFF, percentTo2),
                finalAlpha = (int) Mathf.lerp(color1 >> 24 & 0xFF, color2 >> 24 & 0xFF, percentTo2);
        return getColor(finalRed, finalGreen, finalBlue, finalAlpha);
    }

    public static int reAlphaInt(final int color,
                                 final int alpha) {
        return (MathHelper.clamp(alpha, 0, 255) << 24) | (color & 16777215);
    }
    public static int HUEtoRGB(int value) {
        float hue = (float)value / 360.0F;
        return Color.HSBtoRGB(hue, 1.0F, 1.0F);
    }
}