package sertyo.events.ui.alt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.opengl.GL11;
import sertyo.events.util.Util;
import sertyo.events.util.animation.AnimationMath;
import sertyo.events.util.math.MathUtility;
import sertyo.events.util.render.ColorUtil;
import sertyo.events.util.render.RenderUtility;
import sertyo.events.util.render.font.Fonts;


import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 Alt manager SPIZHEN from hachclient
 **/
public class Account implements Util {

    public String accountName = "";
    public String accountPassword = "";

    public long dateAdded;

    public ResourceLocation skin;
    public float x,y,width,height,animSelect;
    public void onClick(double mouseX,double mouseY,int button){
        if(MathUtility.isHovered(mouseX,mouseY,x+width-14,y+3,8,8)){ // delete
            AltManager.accountsToDelete.add(this);
            return;
        }
        if(MathUtility.isHovered(mouseX,mouseY,x+width-27,y+3,8,8)){ // delete
            mc.keyboardListener.setClipboardString(this.accountName);
            return;
        }
        if(MathUtility.isHovered(mouseX,mouseY,x,y,width,height)){
            mc.session = new Session(accountName,"","","mojang");
        }

    }

    public Account(String accountName) {
        this.accountName = accountName;
        this.dateAdded = System.currentTimeMillis();
        UUID uuid = null;
        try {
            uuid = resolveUUID(accountName);
        } catch (IOException e) {
            uuid = UUID.randomUUID();
        }
        this.skin = DefaultPlayerSkin.getDefaultSkin(uuid);
        Minecraft.getInstance().getSkinManager().loadProfileTextures(new GameProfile(uuid, accountName), (type, loc, tex) -> {
            if (type == MinecraftProfileTexture.Type.SKIN) {
                skin = loc;
            }
        }, true);
    }


    public Account(String accountName, long dateAdded) {
        this.accountName = accountName;
        this.dateAdded = dateAdded;
        UUID uuid = null;
        try {
            uuid = resolveUUID(accountName);
        } catch (IOException e) {
            uuid = UUID.randomUUID();
        }
        this.skin = DefaultPlayerSkin.getDefaultSkin(uuid);
        Minecraft.getInstance().getSkinManager().loadProfileTextures(new GameProfile(uuid, accountName), (type, loc, tex) -> {
            if (type == MinecraftProfileTexture.Type.SKIN) {
                skin = loc;
            }
        }, true);
    }
    public static UUID resolveUUID(String name) throws IOException {
        UUID uUID;
        InputStreamReader in = new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream(), StandardCharsets.UTF_8);
        try {
            uUID = UUID.fromString(new Gson().fromJson(in, JsonObject.class).get("id").getAsString().replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
        }
        catch (Throwable uuid) {
            try {
                try {
                    in.close();
                }
                catch (Throwable throwable) {
                    uuid.addSuppressed(throwable);
                }
                throw uuid;
            }
            catch (Throwable ignored) {
                return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
            }
        }
        in.close();
        return uUID;
    }

    public void render(MatrixStack matrixStack){
        this.animSelect = AnimationMath.fast(this.animSelect,mc.session.getUsername().equalsIgnoreCase(accountName) ? 1:0, 10);

        width = 150;
        height = 70;



        GL11.glPushMatrix();

//        GL11.glTranslatef((x+width/2f),(y+height/2f),0);
//        GL11.glScalef(1+0.05f*this.animSelect,1+0.05f*this.animSelect,0);
//        GL11.glTranslatef(-(x+width/2f),-(y+height/2f),0);

        RenderUtility.drawShadow(x + 2, y + 2, width - 4, height - 4, 10,
                ColorUtil.getColor(4, 180, 255, (int) (255*animSelect)),
                ColorUtil.getColor(16, 31, 255, (int) (255*animSelect)),
                ColorUtil.getColor(4, 180, 255, (int) (255*animSelect)),
                ColorUtil.getColor(16, 31, 255, (int) (255*animSelect))
        );
        RenderUtility.drawRoundedGradientRect(x, y, width, height,5,
                new Color(10,10,10).getRGB(),
                new Color(10,10,10).getRGB(),
                new Color(30,30,30).getRGB(),
                new Color(10,10,10).getRGB()
        );
      //  RenderUtility.drawRoundedCorner(x + width - 30, y, 30, 15, new Vector4f(0, 5, 5, 0), new Color(100, 100, 255).getRGB());
        RenderUtility.drawImage(new ResourceLocation("neiron/images/alt/close.png"), x + width - 12, y + 4, 8, 8);
        RenderUtility.drawImage(new ResourceLocation("neiron/images/alt/copy.png"), x + width - 25, y + 4, 8, 8);


        RenderUtility.renderString(Fonts.INTER_BOLD.get(18), accountName, x + 70, y + 20, 50, -1);
        Fonts.INTER_BOLD.get(17).draw(matrixStack, "Дата создания", x + 70, y + 30, new Color(80, 80, 80).getRGB());
        Date dateAdded = new Date(this.dateAdded);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String formattedDate = dateFormat.format(dateAdded);
        Fonts.INTER_BOLD.get(15).draw(matrixStack, formattedDate, x + 70, y + 40, new Color(80, 80, 80).getRGB());


        RenderUtility.drawHead(skin, x + 2.5f, y + 3.5f, height - 10, height - 10, 5, 1, 0);

        GL11.glPopMatrix();
    }
}
