"""Method by method, what ships in the classes we changed on purpose (see methodmerge.py).

METHOD_LEVEL: these classes (with their inner classes) are merged method by method; every method not listed in
DECISIONS ships as 2.84.1's own code.

DECISIONS: for each method our source changed since the recovery (changes.py diff), what ships:
  "build"            our build's method (small methods we rewrote; new methods are always ours and need no line)
  ("orig", digest)   2.84.1's code: our change only fixed the decompiler's copy, which 2.84.1 never needed
  ("patch", digest)  2.84.1's code with the edits in patches.py (a few smali lines calling into new methods of ours)
The digest is changes.py's for our source of that method when the decision was made: if the source changes again,
guard.py stops the build until the decision (and the patch) are looked at again.
"""

METHOD_LEVEL = {"MainActivity", "SettingsActivity", "MapPane", "HudEditor", "WheelButton", "UiSettings", "LaneSuggest",
                "VoiceNote", "LaneBrain", "GalaxyRow", "GalaxySettingsView", "FastUpdate"}

DECISIONS = {
  "MainActivity": {
    "<init>()V": ("patch", "363599fe2d09"),
    "onCreate(Landroid/os/Bundle;)V": ("patch", "bb1973a9e79c"),
    "onNewIntent(Landroid/content/Intent;)V": ("patch", "946f37e86b44"),
    "floating_delegate$lambda$172(Lcom/starpilot/starview/MainActivity;)Ljava/util/List;": "build",
    "layoutMap(I)V": "build",
    "navTick(J)V": ("patch", "d308598f4b1a"),
    "navigateTo$lambda$177$lambda$176(Lcom/starpilot/starview/MainActivity;Ljava/lang/String;Lorg/json/JSONObject;)Lkotlin/Unit;": ("patch", "7299839d73ef"),
    "openMap()V": ("orig", "645fd8d44b5f"),
    "setDestinationNow$lambda$181(Lcom/starpilot/starview/MainActivity;Lorg/json/JSONObject;)Lkotlin/Unit;": ("patch", "da183b947a9a"),
    "shareIntent$lambda$18(Ljava/lang/String;Ljava/lang/String;Lcom/starpilot/starview/MainActivity;)V": ("patch", "8433d99f8e9a"),
    "setupMapFrame()V": ("patch", "2c996dd965fb"),
    "setupMapFrame$lambda$215(": ("orig", "ad0c9e0644a8"),
    "startHudEdit()V": ("patch", "27d3b9e1d510"),
    "stopHudEdit()V": ("patch", "82e40d3c8e1b"),
    "syncScene(Z)V": ("patch", "84864b415930"),
    "wireControls$lambda$136(": "build",
  },
  "MainActivity$uiTick$1": {
    "run()V": ("patch", "d9ec2894eadd"),
  },
  "MapPane": {
    "<init>(Landroid/content/Context;Lcom/starpilot/starview/MapPane$Host;)V": ("patch", "048b5f0d357e"),
    "fillLook(Landroid/widget/LinearLayout;)V": ("patch", "83a7b04eda92"),
    "fillRoutes(Landroid/widget/LinearLayout;)V": ("patch", "95421cf5f1d8"),
    "goWithLooked$lambda$33(Lcom/starpilot/starview/MapPane;Ljava/lang/String;)Lkotlin/Unit;": ("patch", "f8eb9f7ce999"),
    "refreshBars()V": ("patch", "e52aa17dc6f2"),
  },
  "MapPane$Banner": {
    "onDraw(Landroid/graphics/Canvas;)V": ("patch", "873fcc71eb73"),
  },
  "MapPane$MapCanvas": {
    "onDraw(Landroid/graphics/Canvas;)V": ("patch", "fa7dff4d661f"),
    "style()Ljava/lang/String;": ("patch", "974f6617827e"),
  },
  "SettingsActivity": {
    "areas()Ljava/util/List;": ("patch", "daa27f5f3019"),
    "buildPageUnsafe(Ljava/lang/String;)Landroid/view/View;": ("patch", "9d894baf177d"),
    "catalogPage(Ljava/lang/String;Z)Landroid/view/View;": ("orig", "cc736eb6e3f0"),
    "tabletAboutPage()Landroid/view/View;": ("patch", "10cee18e6a78"),
    "tabletBlinkerPage()Landroid/view/View;": ("patch", "40ac20d7c029"),
    "tabletNavPage()Landroid/view/View;": ("patch", "4a2c842277c1"),
    "tabletNavPage$lambda$254(Lcom/starpilot/starview/UiSettings;)Z": ("patch", "4ac2bfb2b9bc"),
    "tabletNavPage$lambda$255(Lcom/starpilot/starview/UiSettings;Z)Lkotlin/Unit;": ("patch", "96487089a5bf"),
    "tabletVoicePage()Landroid/view/View;": ("orig", "4064ec1fde65"),
    "tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382(": ("orig", "0a3d255484af"),
    "uiGroupPage(Ljava/lang/String;)Landroid/view/View;": ("patch", "dd6eaa5d0550"),
  },
  "HudEditor": {
    "<init>(Landroid/content/Context;Landroid/widget/FrameLayout;Lcom/starpilot/starview/HudLayout;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V": ("patch", "587342b3ec1d"),
    "editTab()V": ("patch", "d688234c6cd0"),
    "editTab$lambda$43(Lcom/starpilot/starview/HudEditor;Lcom/starpilot/starview/HudLayout$State;)Lkotlin/Unit;": ("patch", "e0e8718256a3"),
    "panel(Landroid/content/Context;)Landroid/view/View;": ("patch", "a0136263f62a"),
  },
  "HudEditor$Preview": {
    "onTouchEvent(Landroid/view/MotionEvent;)Z": ("patch", "43e2c75aa7e8"),
  },
  "WheelButton": {
    "onDraw(Landroid/graphics/Canvas;)V": ("patch", "7c53142e1755"),
  },
  "UiSettings": {
    "<clinit>()V": "build",
  },
  "LaneSuggest": {
    "exitDue(Lcom/starpilot/starview/LaneSuggest$In;J)Z": ("patch", "e115f51b7f09"),
  },
  "LaneBrain": {
    "answer()V": ("patch", "48e94868aa19"),
    "edges$side$12(Lcom/starpilot/starview/LaneBrain;FJILcom/starpilot/starview/LaneBrain$Vote;FFFLcom/starpilot/starview/LaneMarks$Mark;F)V": ("patch", "e5fc79ddb7c7"),
    # 2.90.6: with the RTK fix placing us, the card's count is the Scene's (hybCount) and its title counts through lanes
    # and adds the turn lanes (hybLabel)
    "laneCount()Lkotlin/Pair;": ("patch", "7f5da679f49e"),
    "label()Ljava/lang/String;": ("patch", "20194559f73c"),
    # 2.90.7: a ramp the route takes that opens straight off the edge lane is no exit lane (hybModel)
    "roadModel(Lcom/starpilot/starview/LaneBrain$Road;Lcom/starpilot/starview/LaneBrain$RoadInfo;)Lcom/starpilot/starview/LaneBrain$RoadModel;": ("patch", "4324506970cb"),
  },
  # 2.90.1: Galaxy's settings rows as StarPilot's Nov 18 testing update presents them (GalaxyMirror)
  "GalaxyRow": {
    "<init>(ILorg/json/JSONObject;)V": ("patch", "d6102427f348"),
  },
  "GalaxySettingsView": {
    "sliderRow(Lcom/starpilot/starview/GalaxyRow;)V": ("patch", "add69477ed90"),
    # Nov 18 (2) sync: the wiki guide link after the back bar (hybGuide)
    "draw(Lorg/json/JSONObject;)V": ("patch", "ffbd7674e8c5"),
    # Nov 24 sync: Galaxy's link buttons under a readout row (GalaxyMirror.links)
    "readoutRow(Lcom/starpilot/starview/GalaxyRow;)V": ("patch", "ce2392ca0869"),
  },
  # Nov 20 sync: the update's AGNOS step shows its own percent (hybPct)
  "FastUpdate": {
    "from70(Lorg/json/JSONObject;)Lorg/json/JSONObject;": ("patch", "e392bb7d8a07"),
  },
  "VoiceNote": {
    "start(Ljava/io/File;Lkotlin/jvm/functions/Function1;)Ljava/lang/String;": ("patch", "68e8f99ee3f0"),
    "wavHeader(Ljava/io/File;JI)V": ("orig", "1157c101540a"),
  },
}
