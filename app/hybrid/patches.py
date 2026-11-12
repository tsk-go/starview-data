"""Edits to 2.84.1's own smali (decisions.py "patch"): (class, method) -> [(old text, new text), ...].

Each old text must be in that method exactly once (apktool 2.10.0's output of the 2.84.1 APK, so it is fixed); they
apply in order. The new text only calls into a new method of ours ("hyb...", in our source next to the code it
stands for, which calls it at the same place), swaps a constant or drops a few lines, so 2.84.1's code stays as it
was around it. Written by app/hybrid/genpatch.py from anchors, which also checks registers and labels.
"""

PATCHES = {
  ('MainActivity', '<init>()V'): [
    ('    return-void\n',
     '    invoke-virtual/range {p0 .. p0}, Lcom/starpilot/starview/MainActivity;->hybInit()V\n    return-void\n'),
  ],
  ('MainActivity', 'onCreate(Landroid/os/Bundle;)V'): [
    ('    invoke-super/range {p0 .. p1}, Landroidx/appcompat/app/AppCompatActivity;->onCreate(Landroid/os/Bundle;)V\n',
     '    invoke-super/range {p0 .. p1}, Landroidx/appcompat/app/AppCompatActivity;->onCreate(Landroid/os/Bundle;)V\n    invoke-virtual/range {p0 .. p0}, Lcom/starpilot/starview/MainActivity;->hybCreateEarly()V\n'),
    ('    return-void\n',
     '    invoke-virtual/range {p0 .. p0}, Lcom/starpilot/starview/MainActivity;->hybCreateLate()V\n    return-void\n'),
  ],
  ('MainActivity', 'onNewIntent(Landroid/content/Intent;)V'): [
    ('    invoke-virtual {p0, p1}, Lcom/starpilot/starview/MainActivity;->setIntent(Landroid/content/Intent;)V\n',
     '    invoke-virtual {p0, p1}, Lcom/starpilot/starview/MainActivity;->setIntent(Landroid/content/Intent;)V\n    invoke-virtual {p0, p1}, Lcom/starpilot/starview/MainActivity;->hybNewIntent(Landroid/content/Intent;)Z\n    move-result v0\n    if-eqz v0, :hyb_go_on\n    return-void\n    :hyb_go_on\n'),
  ],
  ('MainActivity', 'navTick(J)V'): [
    ('    move-object v0, p0\n',
     '    move-object v0, p0\n    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->hybNavAlert()V\n'),
    ('    invoke-static/range {v5 .. v12}, Lcom/starpilot/starview/Nav;->poll$default(Lcom/starpilot/starview/Nav;Lcom/starpilot/starview/DataClient;ZJZILjava/lang/Object;)V\n',
     '    invoke-static/range {v5 .. v12}, Lcom/starpilot/starview/Nav;->poll$default(Lcom/starpilot/starview/Nav;Lcom/starpilot/starview/DataClient;ZJZILjava/lang/Object;)V\n    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->hybNavTrack()V\n'),
    ('    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->openMap()V\n',
     ''),
  ],
  ('MainActivity', 'setupMapFrame()V'): [
    ('    const v0, 0x1020002\n',
     '    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->hybMapFrame()V\n    const v0, 0x1020002\n'),
  ],
  ('MainActivity', 'syncScene(Z)V'): [
    ('    if-eqz v4, :cond_7\n\n',
     '    if-eqz v4, :cond_7\n\n    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->hybOldScene()Z\n    move-result v6\n    if-eqz v6, :cond_7\n\n'),
    ('    const-string v6, "layoutSeg"\n\n    if-eqz v5, :cond_16\n\n',
     '    const-string v6, "layoutSeg"\n\n    if-eqz v5, :cond_16\n\n    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->hybOldScene()Z\n    move-result v5\n    if-eqz v5, :cond_16\n\n'),
  ],
  ('MainActivity', 'startHudEdit()V'): [
    ('    iget-object v0, p0, Lcom/starpilot/starview/MainActivity;->hudEditor:Lcom/starpilot/starview/HudEditor;\n',
     '    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->hybEditStart()V\n    iget-object v0, p0, Lcom/starpilot/starview/MainActivity;->hudEditor:Lcom/starpilot/starview/HudEditor;\n'),
  ],
  ('MainActivity', 'stopHudEdit()V'): [
    ('    iget-object v0, p0, Lcom/starpilot/starview/MainActivity;->hudEditor:Lcom/starpilot/starview/HudEditor;\n',
     '    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->hybEditStop()V\n    iget-object v0, p0, Lcom/starpilot/starview/MainActivity;->hudEditor:Lcom/starpilot/starview/HudEditor;\n'),
  ],
  ('MainActivity', 'navigateTo$lambda$177$lambda$176(Lcom/starpilot/starview/MainActivity;Ljava/lang/String;Lorg/json/JSONObject;)Lkotlin/Unit;'): [
    ('    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->openMap()V\n\n    iget-object v0, p0, Lcom/starpilot/starview/MainActivity;->mapPane:Lcom/starpilot/starview/MapPane;\n\n    if-eqz v0, :cond_4\n\n    invoke-virtual {v0}, Lcom/starpilot/starview/MapPane;->expectRoutes()V\n',
     '    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->closeMap()V\n'),
  ],
  ('MainActivity', 'setDestinationNow$lambda$181(Lcom/starpilot/starview/MainActivity;Lorg/json/JSONObject;)Lkotlin/Unit;'): [
    ('    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->openMap()V\n\n    iget-object v0, p0, Lcom/starpilot/starview/MainActivity;->mapPane:Lcom/starpilot/starview/MapPane;\n\n    if-eqz v0, :cond_6\n\n    invoke-virtual {v0}, Lcom/starpilot/starview/MapPane;->expectRoutes()V\n',
     '    invoke-virtual {p0}, Lcom/starpilot/starview/MainActivity;->closeMap()V\n'),
  ],
  ('MainActivity$uiTick$1', 'run()V'): [
    ('    :cond_9c\n',
     '    :cond_9c\n    iget-object v3, v1, Lcom/starpilot/starview/MainActivity$uiTick$1;->this$0:Lcom/starpilot/starview/MainActivity;\n    invoke-virtual {v3}, Lcom/starpilot/starview/MainActivity;->hybCardForced()Z\n    move-result v3\n    if-nez v3, :hyb_navcard_on\n'),
    ('    if-nez v3, :cond_9d\n\n',
     '    if-nez v3, :cond_9d\n\n:hyb_navcard_on\n'),
    ('    invoke-virtual {v0, v3}, Lcom/starpilot/starview/NavCardView;->setVisibility(I)V\n',
     '    invoke-virtual {v0, v3}, Lcom/starpilot/starview/NavCardView;->setVisibility(I)V\n    iget-object v0, v1, Lcom/starpilot/starview/MainActivity$uiTick$1;->this$0:Lcom/starpilot/starview/MainActivity;\n    invoke-virtual {v0}, Lcom/starpilot/starview/MainActivity;->hybNavMapTick()V\n'),
    ('    :cond_118\n    iget-object v0, v1, Lcom/starpilot/starview/MainActivity$uiTick$1;->this$0:Lcom/starpilot/starview/MainActivity;\n\n    invoke-static {v0}, Lcom/starpilot/starview/MainActivity;->access$getMapBtn$p(Lcom/starpilot/starview/MainActivity;)Landroid/widget/TextView;\n\n    move-result-object v0\n\n    if-nez v0, :cond_119\n\n    const-string v0, "mapBtn"\n\n    invoke-static {v0}, Lkotlin/jvm/internal/Intrinsics;->throwUninitializedPropertyAccessException(Ljava/lang/String;)V\n\n    const/4 v0, 0x0\n\n    :cond_119\n    invoke-virtual {v0}, Landroid/widget/TextView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;\n\n    move-result-object v0\n\n    const-string v5, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams"\n\n    invoke-static {v0, v5}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;Ljava/lang/String;)V\n\n    check-cast v0, Landroid/widget/FrameLayout$LayoutParams;\n\n    .local v0, "lp":Landroid/widget/FrameLayout$LayoutParams;\n    iget-object v5, v1, Lcom/starpilot/starview/MainActivity$uiTick$1;->this$0:Lcom/starpilot/starview/MainActivity;\n\n    .line 1697\n    const/4 v6, 0x0\n\n    .line 1532\n    .local v6, "$i$a$-let-MainActivity$uiTick$1$run$7":I\n    invoke-static {v5}, Lcom/starpilot/starview/MainActivity;->access$getWhereBtn$p(Lcom/starpilot/starview/MainActivity;)Landroid/widget/TextView;\n\n    move-result-object v7\n\n    if-nez v7, :cond_11a\n\n    const-string v7, "whereBtn"\n\n    invoke-static {v7}, Lkotlin/jvm/internal/Intrinsics;->throwUninitializedPropertyAccessException(Ljava/lang/String;)V\n\n    const/4 v7, 0x0\n\n    :cond_11a\n    invoke-virtual {v7}, Landroid/widget/TextView;->getVisibility()I\n\n    move-result v7\n\n    if-nez v7, :cond_11b\n\n    const/16 v7, 0x14a\n\n    goto :goto_83\n\n    :cond_11b\n    const/16 v7, 0xa0\n\n    :goto_83\n    invoke-static {v5, v7}, Lcom/starpilot/starview/MainActivity;->access$dp(Lcom/starpilot/starview/MainActivity;I)I\n\n    move-result v7\n\n    .local v7, "want":I\n    invoke-virtual {v0}, Landroid/widget/FrameLayout$LayoutParams;->getMarginStart()I\n\n    move-result v8\n\n    if-eq v8, v7, :cond_11d\n\n    invoke-virtual {v0, v7}, Landroid/widget/FrameLayout$LayoutParams;->setMarginStart(I)V\n\n    invoke-static {v5}, Lcom/starpilot/starview/MainActivity;->access$getMapBtn$p(Lcom/starpilot/starview/MainActivity;)Landroid/widget/TextView;\n\n    move-result-object v5\n\n    if-nez v5, :cond_11c\n\n    const-string v5, "mapBtn"\n\n    invoke-static {v5}, Lkotlin/jvm/internal/Intrinsics;->throwUninitializedPropertyAccessException(Ljava/lang/String;)V\n\n    const/4 v5, 0x0\n\n    :cond_11c\n    move-object v8, v0\n\n    check-cast v8, Landroid/view/ViewGroup$LayoutParams;\n\n    invoke-virtual {v5, v8}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V\n\n    .end local v0    # "lp":Landroid/widget/FrameLayout$LayoutParams;\n    .end local v6    # "$i$a$-let-MainActivity$uiTick$1$run$7":I\n    .end local v7    # "want":I\n    :cond_11d\n',
     '    :cond_118\n    # 2.85.3: the map button is a screen-editor widget now (HudLayout places it)\n    :cond_11d\n'),
    ('    move/from16 v31, v0\n',
     '    invoke-static {v2, v0}, Lcom/starpilot/starview/SpeedShown;->mps(Lcom/starpilot/starview/Telemetry;F)F\n    move-result v0\n    move/from16 v31, v0\n'),
  ],
  ('LaneBrain', 'answer()V'): [
    ('    return-void\n',
     '    invoke-virtual {p0}, Lcom/starpilot/starview/LaneBrain;->hybAnswer()V\n    return-void\n'),
  ],
  ('MapPane', '<init>(Landroid/content/Context;Lcom/starpilot/starview/MapPane$Host;)V'): [
    ('    return-void\n',
     '    invoke-virtual/range {p0 .. p0}, Lcom/starpilot/starview/MapPane;->hybInit()V\n    return-void\n'),
  ],
  ('MapPane', 'goWithLooked$lambda$33(Lcom/starpilot/starview/MapPane;Ljava/lang/String;)Lkotlin/Unit;'): [
    ('    invoke-direct {p0}, Lcom/starpilot/starview/MapPane;->refreshBars()V\n',
     '    invoke-direct {p0}, Lcom/starpilot/starview/MapPane;->refreshBars()V\n    iget-object v0, p0, Lcom/starpilot/starview/MapPane;->host:Lcom/starpilot/starview/MapPane$Host;\n    invoke-interface {v0}, Lcom/starpilot/starview/MapPane$Host;->closeMap()V\n'),
  ],
  ('MapPane$Banner', 'onDraw(Landroid/graphics/Canvas;)V'): [
    ('    const v2, -0xfe891ba\n',
     '    sget-object v2, Lcom/starpilot/starview/NavOverlay;->INSTANCE:Lcom/starpilot/starview/NavOverlay;\n    invoke-virtual {v2}, Lcom/starpilot/starview/NavOverlay;->getBG()I\n    move-result v2\n'),
    ('    invoke-virtual {v7, v1, v2, v3, v4}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V\n',
     '    invoke-virtual {v7, v1, v2, v3, v4}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V\n    invoke-virtual {v0, v7, v8, v9}, Lcom/starpilot/starview/MapPane$Banner;->hybEdge(Landroid/graphics/Canvas;FF)V\n'),
  ],
  ('SettingsActivity', 'tabletAboutPage()Landroid/view/View;'): [
    ('    invoke-virtual {v3, v8, v9}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V\n',
     '    invoke-virtual {v3, v8, v9}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V\n    invoke-virtual {v1, v3}, Lcom/starpilot/starview/SettingsActivity;->hybAboutRows(Landroid/widget/LinearLayout;)V\n'),
  ],
  ('SettingsActivity', 'tabletNavPage()Landroid/view/View;'): [
    ('    const-string v2, "Open the map when a route starts"\n',
     '    const-string v2, "See-through map under the turn card"\n'),
    ('    const-string v3, "Also a destination set in Galaxy or on the comma."\n',
     '    const-string v3, "Street lines over the road view while the car is on, and your route while one is set. Move or resize it, the turn card and every button in the screen editor (hold the road view). Tap the map or the card for the full navigation screen."\n'),
  ],
  ('SettingsActivity', 'tabletNavPage$lambda$254(Lcom/starpilot/starview/UiSettings;)Z'): [
    ('    const-string v0, "map_auto_open"\n',
     '    const-string v0, "nav_map"\n'),
  ],
  ('SettingsActivity', 'tabletNavPage$lambda$255(Lcom/starpilot/starview/UiSettings;Z)Lkotlin/Unit;'): [
    ('    const-string v0, "map_auto_open"\n',
     '    const-string v0, "nav_map"\n'),
  ],
  ('WheelButton', 'onDraw(Landroid/graphics/Canvas;)V'): [
    ('    iget-object v6, p0, Lcom/starpilot/starview/WheelButton;->exp:Landroid/graphics/Bitmap;\n',
     '    iget-object v6, p0, Lcom/starpilot/starview/WheelButton;->wheel:Landroid/graphics/Bitmap;\n'),
    ('    if-nez v9, :cond_d\n',
     ''),
  ],
  ('HudEditor', '<init>(Landroid/content/Context;Landroid/widget/FrameLayout;Lcom/starpilot/starview/HudLayout;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V'): [
    ('    const v4, 0x3f147ae1    # 0.58f\n',
     '    const v4, 0x3f4ccccd    # 0.8f\n'),
    ('    const v4, 0x3ed70a3d    # 0.42f\n',
     '    const v4, 0x3e4ccccd    # 0.2f\n'),
  ],
  ('HudEditor', 'editTab$lambda$43(Lcom/starpilot/starview/HudEditor;Lcom/starpilot/starview/HudLayout$State;)Lkotlin/Unit;'): [
    ('    invoke-direct {p0}, Lcom/starpilot/starview/HudEditor;->changed()V\n',
     '    const/high16 v0, 0x7fc00000    # Float.NaN\n    invoke-virtual {p1, v0}, Lcom/starpilot/starview/HudLayout$State;->setW(F)V\n    invoke-virtual {p1, v0}, Lcom/starpilot/starview/HudLayout$State;->setH(F)V\n    invoke-direct {p0}, Lcom/starpilot/starview/HudEditor;->changed()V\n'),
  ],
  ('HudEditor', 'panel(Landroid/content/Context;)Landroid/view/View;'): [
    ('    invoke-virtual {v4, v10, v5, v6}, Lcom/starpilot/starview/CarUi;->button(Ljava/lang/String;ZLkotlin/jvm/functions/Function0;)Landroid/widget/TextView;\n\n    move-result-object v4\n\n    check-cast v4, Landroid/view/View;\n\n    invoke-virtual {v3, v4}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V\n',
     '    invoke-virtual {v4, v10, v5, v6}, Lcom/starpilot/starview/CarUi;->button(Ljava/lang/String;ZLkotlin/jvm/functions/Function0;)Landroid/widget/TextView;\n\n    move-result-object v4\n\n    check-cast v4, Landroid/view/View;\n\n    invoke-virtual {v3, v4}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V\n    invoke-virtual {v0, v3, v1}, Lcom/starpilot/starview/HudEditor;->hybButtonRows(Landroid/widget/LinearLayout;Landroid/content/Context;)V\n'),
  ],
  ('HudEditor', 'editTab()V'): [
    ('    const/4 v9, 0x0\n\n    invoke-static {v6, v13, v7, v8, v9}, Lcom/starpilot/starview/CarUi;->lp$default(Lcom/starpilot/starview/CarUi;IIILjava/lang/Object;)Landroid/widget/LinearLayout$LayoutParams;\n\n    move-result-object v6\n\n    check-cast v6, Landroid/view/ViewGroup$LayoutParams;\n\n    invoke-virtual {v4, v5, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V\n',
     '    const/4 v9, 0x0\n\n    invoke-static {v6, v13, v7, v8, v9}, Lcom/starpilot/starview/CarUi;->lp$default(Lcom/starpilot/starview/CarUi;IIILjava/lang/Object;)Landroid/widget/LinearLayout$LayoutParams;\n\n    move-result-object v6\n\n    check-cast v6, Landroid/view/ViewGroup$LayoutParams;\n\n    invoke-virtual {v4, v5, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V\n    invoke-virtual {v0, v1, v2}, Lcom/starpilot/starview/HudEditor;->hybFreeSize(Lcom/starpilot/starview/HudLayout$Item;Lcom/starpilot/starview/HudLayout$State;)V\n'),
  ],
  ('HudEditor$Preview', 'onTouchEvent(Landroid/view/MotionEvent;)Z'): [
    ('    iput v7, v0, Lcom/starpilot/starview/HudEditor$Preview;->startW:F\n',
     '    iput v7, v0, Lcom/starpilot/starview/HudEditor$Preview;->startW:F\n    invoke-virtual {v0, v3}, Lcom/starpilot/starview/HudEditor$Preview;->hybStartH(Landroid/graphics/RectF;)V\n'),
    ('    move-object v7, v10\n',
     '    move-object v7, v10\n    invoke-virtual {v0, v1, v10, v3, v11}, Lcom/starpilot/starview/HudEditor$Preview;->hybFreeResize(Lcom/starpilot/starview/HudLayout$Item;Lcom/starpilot/starview/HudLayout$State;FF)Z\n    move-result v5\n    if-nez v5, :goto_0\n'),
  ],
  ('LaneSuggest', 'exitDue(Lcom/starpilot/starview/LaneSuggest$In;J)Z'): [
    ('    const/4 v4, 0x2\n',
     '    const/4 v4, 0x1\n'),
    ('    if-lt v1, v4, :cond_4\n',
     '    if-eq v1, v4, :cond_4\n'),
  ],
  ('VoiceNote', 'start(Ljava/io/File;Lkotlin/jvm/functions/Function1;)Ljava/lang/String;'): [
    ('    new-instance v9, Lcom/starpilot/starview/VoiceNote$start$1;\n\n    invoke-direct {v9, p0}, Lcom/starpilot/starview/VoiceNote$start$1;-><init>(Lcom/starpilot/starview/VoiceNote;)V\n',
     '    invoke-virtual {p0}, Lcom/starpilot/starview/VoiceNote;->hybListener()Landroid/speech/RecognitionListener;\n\n    move-result-object v9\n'),
    ('    invoke-virtual {v4, v7}, Landroid/speech/SpeechRecognizer;->startListening(Landroid/content/Intent;)V\n',
     '    invoke-virtual {p0, v7}, Lcom/starpilot/starview/VoiceNote;->hybIntent(Landroid/content/Intent;)V\n    invoke-virtual {v4, v7}, Landroid/speech/SpeechRecognizer;->startListening(Landroid/content/Intent;)V\n'),
  ],
}
