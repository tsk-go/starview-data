package com.starpilot.starview;


public final class MapCams extends OverpassNodes {
   public static final MapCams INSTANCE = new MapCams();

   private MapCams() {
      super("map_cams", "node[\"highway\"=\"speed_camera\"]", "speed_cam");
   }
}
