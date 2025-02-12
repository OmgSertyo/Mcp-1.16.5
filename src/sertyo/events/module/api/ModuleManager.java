package sertyo.events.module.api;



import sertyo.events.module.impl.combat.SuperBow;
import sertyo.events.module.impl.movement.ZZZZZOVOVOZVOZVOZOVZOVZOVZOVOZV;
import sertyo.events.module.impl.render.Hud;
import sertyo.events.module.impl.util.TabCompleteCrasher;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
   private final List<Module> modules = new ArrayList<>();

   public ModuleManager() {
      //add reg. modules
      registerModule(new SuperBow());
      registerModule(new Hud());
   }

   public void registerModule(Module module) {
      this.modules.add(module);
   }

   public List<Module> getModules() {
      return this.modules;
   }

   public Module[] getModulesFromCategory(Category category) {
      return this.modules.stream().filter((module) -> module.category == category).toArray(Module[]::new);
   }

   public Module getModule(Class<? extends Module> classModule) {
      Module module;
      do {
         if (!this.modules.iterator().hasNext()) {
            return null;
         }

         module = this.modules.iterator().next();
      } while(module == null || module.getClass() != classModule);

      return module;
   }

   public Module getModule(String name) {
      Module module;
      do {
         if (!this.modules.iterator().hasNext()) {
            return null;
         }

         module = this.modules.iterator().next();
      } while(module == null || !module.getName().equalsIgnoreCase(name));

      return module;
   }
}
