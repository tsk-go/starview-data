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
                "VoiceNote", "LaneBrain"}

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
    "setupMapFrame()V": ("patch", "2c996dd965fb"),
    "setupMapFrame$lambda$215(": ("orig", "ad0c9e0644a8"),
    "startHudEdit()V": ("patch", "27d3b9e1d510"),
    "stopHudEdit()V": ("patch", "82e40d3c8e1b"),
    "syncScene(Z)V": ("patch", "84864b415930"),
    "wireControls$lambda$136(": "build",
  },
  "MainActivity$uiTick$1": {
    "run()V": ("patch", "ffb58518d5f8"),
  },
  "MapPane": {
    "<init>(Landroid/content/Context;Lcom/starpilot/starview/MapPane$Host;)V": ("patch", "048b5f0d357e"),
    "goWithLooked$lambda$33(Lcom/starpilot/starview/MapPane;Ljava/lang/String;)Lkotlin/Unit;": ("patch", "f8eb9f7ce999"),
    "refreshBars()V": ("orig", "f014a32e961e"),
  },
  "MapPane$Banner": {
    "onDraw(Landroid/graphics/Canvas;)V": ("patch", "873fcc71eb73"),
  },
  "MapPane$MapCanvas": {
    "onDraw(Landroid/graphics/Canvas;)V": ("orig", "a21a139b77dc"),
  },
  "SettingsActivity": {
    "catalogPage(Ljava/lang/String;Z)Landroid/view/View;": ("orig", "cc736eb6e3f0"),
    "tabletAboutPage()Landroid/view/View;": ("patch", "10cee18e6a78"),
    "tabletBlinkerPage()Landroid/view/View;": ("orig", "2638fbace510"),
    "tabletNavPage()Landroid/view/View;": ("patch", "5ee19106b275"),
    "tabletNavPage$lambda$254(Lcom/starpilot/starview/UiSettings;)Z": ("patch", "4ac2bfb2b9bc"),
    "tabletNavPage$lambda$255(Lcom/starpilot/starview/UiSettings;Z)Lkotlin/Unit;": ("patch", "96487089a5bf"),
    "tabletVoicePage()Landroid/view/View;": ("orig", "4064ec1fde65"),
    "tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382(": ("orig", "0a3d255484af"),
    "uiGroupPage(Ljava/lang/String;)Landroid/view/View;": ("orig", "ca77a7ca4027"),
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
  },
  "VoiceNote": {
    "start(Ljava/io/File;Lkotlin/jvm/functions/Function1;)Ljava/lang/String;": ("patch", "68e8f99ee3f0"),
    "wavHeader(Ljava/io/File;JI)V": ("orig", "1157c101540a"),
  },
}
