package sertyo.events.module.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleAnnotation {
   String name();
   String description();
   Category category();
}
