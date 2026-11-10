package com.starpilot.starview;


public final class WantIdeas {
   public static final WantIdeas INSTANCE = new WantIdeas();
   private static volatile boolean schoolBus;
   private static volatile boolean wipers;

   private WantIdeas() {
   }

   public final boolean getSchoolBus() {
      return schoolBus;
   }

   public final boolean getWipers() {
      return wipers;
   }

   public final void setSchoolBus(boolean var1) {
      schoolBus = var1;
   }

   public final void setWipers(boolean var1) {
      wipers = var1;
   }
}
