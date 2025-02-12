package sertyo.events.manager.theme;

import java.awt.*;
import java.util.HashMap;

public enum Themes {
   DARK(new Theme("Dark", Theme.ThemeType.GUI, new Color[]{new Color(30, 30, 30), new Color(25, 25, 25), new Color(75, 75, 75), new Color(65, 65, 65), new Color(42, 42, 42), new Color(37, 37, 37, 200)})),
   NIGRILA(new Theme("XZ", Theme.ThemeType.STYLE, new Color[]{(new Color(68, 213, 116)).brighter(), (new Color(213, 35, 209)).darker()})),
   CANDY(new Theme("Candy", Theme.ThemeType.STYLE, new Color[]{new Color(28, 167, 222), new Color(236, 133, 209)})),
   SUMMER(new Theme("Summer", Theme.ThemeType.STYLE, new Color[]{new Color(34, 193, 195), new Color(253, 187, 45)})),
   RIVER(new Theme("River", Theme.ThemeType.STYLE, new Color[]{new Color(4443810), new Color(1596061)})),
   GLORIA(new Theme("Gloria", Theme.ThemeType.STYLE, new Color[]{new Color(641745), new Color(6363340)})),
   LOLLIPOP(new Theme("Lollipop", Theme.ThemeType.STYLE, new Color[]{new Color(16234331), new Color(15476088)})),
   JESIC(new Theme("Jesic", Theme.ThemeType.STYLE, new Color[]{new Color(12189542), new Color(7049291)})),
   SHINE(new Theme("Shine", Theme.ThemeType.STYLE, new Color[]{new Color(11760581), new Color(7170255)})),
   HERON(new Theme("Heron", Theme.ThemeType.STYLE, new Color[]{new Color(15893890), new Color(6855044)})),
   DOUP(new Theme("Doup", Theme.ThemeType.STYLE, new Color[]{new Color(14412429), new Color(8225371)})),
   LEEN(new Theme("Leen", Theme.ThemeType.STYLE, new Color[]{new Color(10285765), new Color(4291953)})),
   CRIMSON(new Theme("Crimson", Theme.ThemeType.STYLE, new Color[]{new Color(215, 60, 67), new Color(140, 23, 39)})),
   SUNDAE(new Theme("Windy", Theme.ThemeType.STYLE, new Color[]{new Color(11319013), new Color(8846824)})),
   ORANGE(new Theme("Orange", Theme.ThemeType.STYLE, new Color[]{new Color(242, 201, 76), new Color(241, 143, 56)})),
   ATLAS(new Theme("Atlas", Theme.ThemeType.STYLE, new Color[]{new Color(16690270), new Color(4964552)})),
   SUBLIME(new Theme("Sublime", Theme.ThemeType.STYLE, new Color[]{new Color(16538749), new Color(6980347)})),
   AZURE(new Theme("Azure", Theme.ThemeType.STYLE, new Color[]{new Color(239, 50, 217), new Color(137, 255, 253)})),
   MAGIC(new Theme("Magic", Theme.ThemeType.STYLE, new Color[]{new Color(5882227), new Color(6104769)})),
   ORCA(new Theme("Orca", Theme.ThemeType.STYLE, new Color[]{new Color(4497549), new Color(1006685)})),
   EMERALD(new Theme("Emerald", Theme.ThemeType.STYLE, new Color[]{new Color(3731325), new Color(1153422)})),
   WITCHERY(new Theme("Witchery", Theme.ThemeType.STYLE, new Color[]{new Color(12784690), new Color(6430354)})),
   FLARE(new Theme("Flare", Theme.ThemeType.STYLE, new Color[]{new Color(15804177), new Color(16101145)})),
   FALLING(new Theme("Falling", Theme.ThemeType.STYLE, new Color[]{new Color(12993134), new Color(6564723)})),
   MOONLIGHT(new Theme("Moonlight", Theme.ThemeType.STYLE, new Color[]{new Color(10986455), new Color(5855641)})),
   SKY(new Theme("Sky", Theme.ThemeType.STYLE, new Color[]{new Color(135, 206, 250), new Color(135, 206, 235)})),
   AMETHYST(new Theme("Amethyst", Theme.ThemeType.STYLE, new Color[]{new Color(153, 102, 204), new Color(204, 153, 255)})),
   MYSTIC(new Theme("Mystic", Theme.ThemeType.STYLE, new Color[]{new Color(102, 51, 153), new Color(204, 153, 255)})),
   RUBY(new Theme("Ruby", Theme.ThemeType.STYLE, new Color[]{new Color(224, 17, 95), new Color(178, 34, 34)})),
   SAPPHIRE(new Theme("Sapphire", Theme.ThemeType.STYLE, new Color[]{new Color(0, 51, 153), new Color(0, 102, 204)})),
   GOLDEN(new Theme("Golden", Theme.ThemeType.STYLE, new Color[]{new Color(255, 215, 0), new Color(218, 165, 32)})),
   PLATINUM(new Theme("Platinum", Theme.ThemeType.STYLE, new Color[]{new Color(229, 228, 226), new Color(127, 130, 131)})),
   // Добавленные новые темы
   OCEAN(new Theme("Ocean", Theme.ThemeType.STYLE, new Color[]{new Color(50, 80, 200), new Color(30, 50, 150)})),
   FOREST(new Theme("Forest", Theme.ThemeType.STYLE, new Color[]{new Color(34, 139, 34), new Color(0, 100, 0)})),
   LAVENDER(new Theme("Lavender", Theme.ThemeType.STYLE, new Color[]{new Color(230, 230, 250), new Color(176, 196, 222)})),
   CINNAMON(new Theme("Cinnamon", Theme.ThemeType.STYLE, new Color[]{new Color(210, 105, 30), new Color(139, 69, 19)})),
   DESERT(new Theme("Desert", Theme.ThemeType.STYLE, new Color[]{new Color(244, 164, 96), new Color(210, 180, 140)})),
   ARCTIC(new Theme("Arctic", Theme.ThemeType.STYLE, new Color[]{new Color(240, 255, 255), new Color(173, 216, 230)})),
   FIRE(new Theme("Fire", Theme.ThemeType.STYLE, new Color[]{new Color(255, 69, 0), new Color(255, 165, 0)})),
   CHOCOLATE(new Theme("Chocolate", Theme.ThemeType.STYLE, new Color[]{new Color(139, 69, 19), new Color(210, 105, 30)})),
   UNICORN(new Theme("Unicorn", Theme.ThemeType.STYLE, new Color[]{new Color(255, 192, 203), new Color(138, 43, 226)})),
   LEMON(new Theme("Lemon", Theme.ThemeType.STYLE, new Color[]{new Color(255, 250, 205), new Color(255, 215, 0)})),
   RAINBOW(new Theme("Rainbow", Theme.ThemeType.STYLE, new Color[]{new Color(255, 0, 0), new Color(255, 127, 0), new Color(255, 255, 0), new Color(0, 255, 0), new Color(0, 0, 255), new Color(75, 0, 130), new Color(148, 0, 211)})),
   EARTH(new Theme("Earth", Theme.ThemeType.STYLE, new Color[]{new Color(139, 69, 19), new Color(160, 82, 45)})),
   LILAC(new Theme("Lilac", Theme.ThemeType.STYLE, new Color[]{new Color(200, 162, 200), new Color(182, 102, 210)})),
   WINTER(new Theme("Winter", Theme.ThemeType.STYLE, new Color[]{new Color(192, 222, 237), new Color(255, 250, 250)})),
   PEACH(new Theme("Peach", Theme.ThemeType.STYLE, new Color[]{new Color(255, 218, 185), new Color(255, 127, 80)})),
   TROPICAL(new Theme("Tropical", Theme.ThemeType.STYLE, new Color[]{new Color(0, 128, 0), new Color(0, 255, 127)})),
   MERMAID(new Theme("Mermaid", Theme.ThemeType.STYLE, new Color[]{new Color(128, 0, 128), new Color(0, 255, 255)})),
   STORM(new Theme("Storm", Theme.ThemeType.STYLE, new Color[]{new Color(112, 128, 144), new Color(47, 79, 79)})),
   TOPAZ(new Theme("Topaz", Theme.ThemeType.STYLE, new Color[]{new Color(255, 204, 51), new Color(153, 102, 0)})),
   ROSE(new Theme("Rose", Theme.ThemeType.STYLE, new Color[]{new Color(255, 102, 204), new Color(255, 51, 153)})),
   PEARL(new Theme("Pearl", Theme.ThemeType.STYLE, new Color[]{new Color(234, 224, 200), new Color(255, 239, 213)})),
   OPAL(new Theme("Opal", Theme.ThemeType.STYLE, new Color[]{new Color(191, 255, 0), new Color(51, 204, 51)})),
   AQUAMARINE(new Theme("Aquamarine", Theme.ThemeType.STYLE, new Color[]{new Color(127, 255, 212), new Color(0, 255, 255)})),
   PERIDOT(new Theme("Peridot", Theme.ThemeType.STYLE, new Color[]{new Color(153, 255, 51), new Color(51, 102, 0)})),
   CITRINE(new Theme("Citrine", Theme.ThemeType.STYLE, new Color[]{new Color(255, 255, 102), new Color(204, 204, 0)})),
   TANZANITE(new Theme("Tanzanite", Theme.ThemeType.STYLE, new Color[]{new Color(0, 204, 255), new Color(0, 51, 102)})),
   MALACHITE(new Theme("Malachite", Theme.ThemeType.STYLE, new Color[]{new Color(0, 204, 153), new Color(0, 102, 51)})),
   AGATE(new Theme("Agate", Theme.ThemeType.STYLE, new Color[]{new Color(136, 138, 133), new Color(40, 40, 40)}));
   private final Theme theme;
   private static final HashMap<String, Theme> map = new HashMap();

   private Themes(Theme theme) {
      this.theme = theme;
   }

   public static Theme findByName(String name) {
      return map.get(name);
   }

   public Theme getTheme() {
      return this.theme;
   }

   static {
      Themes[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         Themes v = var0[var2];
         map.put(v.theme.getName(), v.theme);
      }
   }
}
