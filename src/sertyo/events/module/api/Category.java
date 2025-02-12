package sertyo.events.module.api;


import sertyo.events.util.animation.Animation;
import sertyo.events.util.animation.impl.DecelerateAnimation;

public enum Category {
   COMBAT("Combat", "a"),
   MOVEMENT("Movement", "b"),
   RENDER("Render", "c"),
   PLAYER("Player", "d"),
   UTIL("Util", "e"),
   THEMES("Themes", "h");
   private final String name;
   private final String icon;
   private boolean bottom = false;
   private final Animation animation = new DecelerateAnimation(340, 1.0F);

   Category(String name, String icon) {
      this.name = name;
      this.icon = icon;
   }

   private Category(String name, String icon, boolean bottom) {
      this.name = name;
      this.icon = icon;
      this.bottom = bottom;
   }

   public String getName() {
      return this.name;
   }

   public String getIcon() {
      return this.icon;
   }

   public boolean isBottom() {
      return this.bottom;
   }

   public Animation getAnimation() {
      return this.animation;
   }
}
