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
    ('    :goto_6e\n',
     '    :goto_6e\n    move/from16 v4, v43\n    move/from16 v15, v99\n    invoke-static {v4, v15}, Lcom/starpilot/starview/ExitGuard;->side(II)I\n    move-result v4\n    move/from16 v99, v4\n'),
    ('    :goto_7f\n    invoke-virtual {v0, v4}, Landroid/widget/TextView;->setVisibility(I)V\n',
     '    :goto_7f\n    invoke-virtual {v0, v4}, Landroid/widget/TextView;->setVisibility(I)V\n    iget-object v0, v1, Lcom/starpilot/starview/MainActivity$uiTick$1;->this$0:Lcom/starpilot/starview/MainActivity;\n    invoke-virtual {v0}, Lcom/starpilot/starview/MainActivity;->hybWhereTick()V\n'),
  ],
  ('LaneBrain', 'answer()V'): [
    ('    return-void\n',
     '    invoke-virtual {p0}, Lcom/starpilot/starview/LaneBrain;->hybAnswer()V\n    return-void\n'),
  ],
  ('LaneBrain', 'edges$side$12(Lcom/starpilot/starview/LaneBrain;FJILcom/starpilot/starview/LaneBrain$Vote;FFFLcom/starpilot/starview/LaneMarks$Mark;F)V'): [
    ('    if-eqz v0, :cond_e\n',
     '    invoke-virtual {v9, v0, v10}, Lcom/starpilot/starview/LaneBrain;->hybEdgeRoom(Ljava/lang/Integer;I)Ljava/lang/Integer;\n    move-result-object v0\n    if-eqz v0, :cond_e\n'),
  ],
  ('LaneBrain', 'laneCount()Lkotlin/Pair;'): [
    ('    invoke-virtual {p0}, Lcom/starpilot/starview/LaneBrain;->total()Lkotlin/Pair;\n',
     '    invoke-virtual {p0}, Lcom/starpilot/starview/LaneBrain;->hybCount()Lkotlin/Pair;\n    move-result-object v0\n    if-eqz v0, :hyb_count_own\n    return-object v0\n    :hyb_count_own\n    invoke-virtual {p0}, Lcom/starpilot/starview/LaneBrain;->total()Lkotlin/Pair;\n'),
  ],
  ('LaneBrain', 'roadModel(Lcom/starpilot/starview/LaneBrain$Road;Lcom/starpilot/starview/LaneBrain$RoadInfo;)Lcom/starpilot/starview/LaneBrain$RoadModel;'): [
    ('    return-object v6\n',
     '    move-object/from16 v7, p0\n    invoke-virtual {v7, v6}, Lcom/starpilot/starview/LaneBrain;->hybModel(Lcom/starpilot/starview/LaneBrain$RoadModel;)Lcom/starpilot/starview/LaneBrain$RoadModel;\n    move-result-object v6\n    return-object v6\n'),
  ],
  ('LaneBrain', 'label()Ljava/lang/String;'): [
    ('    invoke-virtual {p0}, Lcom/starpilot/starview/LaneBrain;->laneFromLeft()Lkotlin/Pair;\n',
     '    invoke-virtual {p0}, Lcom/starpilot/starview/LaneBrain;->hybLabel()Ljava/lang/String;\n    move-result-object v0\n    if-eqz v0, :hyb_label_own\n    return-object v0\n    :hyb_label_own\n    invoke-virtual {p0}, Lcom/starpilot/starview/LaneBrain;->laneFromLeft()Lkotlin/Pair;\n'),
  ],
  ('MapPane', '<init>(Landroid/content/Context;Lcom/starpilot/starview/MapPane$Host;)V'): [
    ('    return-void\n',
     '    invoke-virtual/range {p0 .. p0}, Lcom/starpilot/starview/MapPane;->hybInit()V\n    return-void\n'),
  ],
  ('MapPane', 'goWithLooked$lambda$33(Lcom/starpilot/starview/MapPane;Ljava/lang/String;)Lkotlin/Unit;'): [
    ('    invoke-direct {p0}, Lcom/starpilot/starview/MapPane;->refreshBars()V\n',
     '    invoke-direct {p0}, Lcom/starpilot/starview/MapPane;->refreshBars()V\n    iget-object v0, p0, Lcom/starpilot/starview/MapPane;->host:Lcom/starpilot/starview/MapPane$Host;\n    invoke-interface {v0}, Lcom/starpilot/starview/MapPane$Host;->closeMap()V\n'),
  ],
  ('MapPane', 'refreshBars()V'): [
    ('    move-result-object v17\n',
     '    move-result-object v17\n    invoke-static/range {v17 .. v17}, Lcom/starpilot/starview/NavEta;->remaining(Lkotlin/Pair;)Lkotlin/Pair;\n    move-result-object v17\n'),
  ],
  ('MapPane', 'fillRoutes(Landroid/widget/LinearLayout;)V'): [
    ('    .line 475\n    invoke-virtual {v14}, Lcom/starpilot/starview/Nav$Route;->getVia()Ljava/lang/String;\n',
     '    .line 475\n    invoke-static {v14}, Lcom/starpilot/starview/NavVia;->of(Lcom/starpilot/starview/Nav$Route;)Ljava/lang/String;\n'),
    ('    if-eqz v0, :cond_20\n\n    invoke-virtual {v14}, Lcom/starpilot/starview/Nav$Route;->getVia()Ljava/lang/String;\n',
     '    if-eqz v0, :cond_20\n\n    invoke-static {v14}, Lcom/starpilot/starview/NavVia;->of(Lcom/starpilot/starview/Nav$Route;)Ljava/lang/String;\n'),
  ],
  ('MapPane', 'fillLook(Landroid/widget/LinearLayout;)V'): [
    ('    :cond_8\n    invoke-virtual {v9}, Lcom/starpilot/starview/Nav$Route;->getVia()Ljava/lang/String;\n',
     '    :cond_8\n    invoke-static {v9}, Lcom/starpilot/starview/NavVia;->of(Lcom/starpilot/starview/Nav$Route;)Ljava/lang/String;\n'),
    ('    if-eqz v1, :cond_a\n\n    invoke-virtual {v9}, Lcom/starpilot/starview/Nav$Route;->getVia()Ljava/lang/String;\n',
     '    if-eqz v1, :cond_a\n\n    invoke-static {v9}, Lcom/starpilot/starview/NavVia;->of(Lcom/starpilot/starview/Nav$Route;)Ljava/lang/String;\n'),
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
    ('    return-object v4\n',
     '    invoke-static {v6, v8}, Lcom/starpilot/starview/GoogleMaps;->addTo(Landroid/app/Activity;Landroid/widget/LinearLayout;)V\n    return-object v4\n'),
  ],
  ('SettingsActivity', 'tabletNavPage$lambda$254(Lcom/starpilot/starview/UiSettings;)Z'): [
    ('    const-string v0, "map_auto_open"\n',
     '    const-string v0, "nav_map"\n'),
  ],
  ('SettingsActivity', 'tabletNavPage$lambda$255(Lcom/starpilot/starview/UiSettings;Z)Lkotlin/Unit;'): [
    ('    const-string v0, "map_auto_open"\n',
     '    const-string v0, "nav_map"\n'),
  ],
  ('SettingsActivity', 'uiGroupPage(Ljava/lang/String;)Landroid/view/View;'): [
    ('    return-object v2\n',
     '    invoke-static {p0, p1, v0}, Lcom/starpilot/starview/SpeedAlertPage;->addTo(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/LinearLayout;)V\n    return-object v2\n'),
  ],
  ('SettingsActivity', 'areas()Ljava/util/List;'): [
    ('    const/16 v11, 0x13\n\n    new-array v11, v11, [Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    new-instance v14, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_link:I\n\n    const-string v13, "tablet:connection"\n\n    const-string v0, "Connection"\n\n    invoke-direct {v14, v0, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    aput-object v14, v11, v9\n\n    .line 177\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "tablet:nav"\n\n    const-string v14, "Navigation map"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    aput-object v0, v11, v10\n\n    .line 176\n    nop\n\n    .line 178\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_camera:I\n\n    const-string v13, "tablet:view"\n\n    const-string v14, "Camera & view"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    aput-object v0, v11, v4\n\n    .line 176\n    nop\n\n    .line 179\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "tablet:maps"\n\n    const-string v14, "Street maps"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/4 v7, 0x3\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 180\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "activity:sensors"\n\n    const-string v14, "GPS & sensors"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    aput-object v0, v11, v12\n\n    .line 176\n    nop\n\n    .line 181\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_camera:I\n\n    const-string v13, "activity:lanepics"\n\n    const-string v14, "Lane pictures"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/4 v7, 0x5\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 182\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "roaddata"\n\n    const-string v14, "Lane road data"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/4 v7, 0x6\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 183\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "bookmarks"\n\n    const-string v14, "Bookmarks"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/4 v7, 0x7\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 184\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "tablet:rtk"\n\n    const-string v14, "RTK GPS"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0x8\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 185\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_speed:I\n\n    const-string v13, "tablet:braking"\n\n    const-string v14, "Braking log"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0x9\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 186\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_steering:I\n\n    const-string v13, "tablet:blinker"\n\n    const-string v14, "Blinker & lane changes"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0xa\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 187\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_star:I\n\n    const-string v13, "tablet:ideas"\n\n    const-string v14, "New ideas"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0xb\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 188\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_info:I\n\n    const-string v13, "tablet:voice"\n\n    const-string v14, "Voice alerts"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0xc\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 189\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_info:I\n\n    const-string v13, "tablet:voicestudio"\n\n    const-string v14, "Voice studio"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0xd\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 190\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_terminal:I\n\n    const-string v13, "native:terminal"\n\n    const-string v14, "Terminal"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0xe\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 191\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_files:I\n\n    const-string v13, "activity:files"\n\n    const-string v14, "Files"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0xf\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 192\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_data:I\n\n    const-string v13, "tablet:storage"\n\n    const-string v14, "Storage"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0x10\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 193\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_data:I\n\n    const-string v13, "tablet:backup"\n\n    const-string v14, "Backup & restore"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0x11\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 194\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_info:I\n\n    const-string v13, "tablet:about"\n\n    const-string v14, "About"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0x12\n\n    aput-object v0, v11, v7\n',
     '    const/16 v11, 0x14\n\n    new-array v11, v11, [Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    new-instance v14, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_link:I\n\n    const-string v13, "tablet:connection"\n\n    const-string v0, "Connection"\n\n    invoke-direct {v14, v0, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    aput-object v14, v11, v9\n\n    .line 177\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "tablet:nav"\n\n    const-string v14, "Navigation map"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    aput-object v0, v11, v10\n\n    .line 176\n    nop\n\n    .line 178\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_camera:I\n\n    const-string v13, "tablet:view"\n\n    const-string v14, "Camera & view"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    aput-object v0, v11, v4\n\n    .line 176\n    nop\n\n    .line 179\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "tablet:maps"\n\n    const-string v14, "Street maps"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/4 v7, 0x3\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 180\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "activity:sensors"\n\n    const-string v14, "GPS & sensors"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    aput-object v0, v11, v12\n\n    .line 176\n    nop\n\n    .line 181\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_camera:I\n\n    const-string v13, "activity:lanepics"\n\n    const-string v14, "Lane pictures"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/4 v7, 0x5\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 182\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "roaddata"\n\n    const-string v14, "Lane road data"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/4 v7, 0x6\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 183\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "bookmarks"\n\n    const-string v14, "Bookmarks"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/4 v7, 0x7\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 184\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_pin:I\n\n    const-string v13, "tablet:rtk"\n\n    const-string v14, "RTK GPS"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0x8\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 185\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_speed:I\n\n    const-string v13, "tablet:braking"\n\n    const-string v14, "Braking log"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0x9\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 186\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_steering:I\n\n    const-string v13, "tablet:blinker"\n\n    const-string v14, "Blinker & lane changes"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0xa\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 187\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_star:I\n\n    const-string v13, "tablet:ideas"\n\n    const-string v14, "New ideas"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0xb\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 188\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_info:I\n\n    const-string v13, "tablet:voice"\n\n    const-string v14, "Voice alerts"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0xc\n\n    aput-object v0, v11, v7\n    nop\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_limit:I\n    const-string v13, "tablet:speedalert"\n    const-string v14, "Speed limit warning"\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n    const/16 v7, 0xd\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 189\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_info:I\n\n    const-string v13, "tablet:voicestudio"\n\n    const-string v14, "Voice studio"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0xe\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 190\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_terminal:I\n\n    const-string v13, "native:terminal"\n\n    const-string v14, "Terminal"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0xf\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 191\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_files:I\n\n    const-string v13, "activity:files"\n\n    const-string v14, "Files"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0x10\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 192\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_data:I\n\n    const-string v13, "tablet:storage"\n\n    const-string v14, "Storage"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0x11\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 193\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_data:I\n\n    const-string v13, "tablet:backup"\n\n    const-string v14, "Backup & restore"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0x12\n\n    aput-object v0, v11, v7\n\n    .line 176\n    nop\n\n    .line 194\n    new-instance v0, Lcom/starpilot/starview/SettingsActivity$Rail;\n\n    sget v7, Lcom/starpilot/starview/R$drawable;->ic_info:I\n\n    const-string v13, "tablet:about"\n\n    const-string v14, "About"\n\n    invoke-direct {v0, v14, v7, v13}, Lcom/starpilot/starview/SettingsActivity$Rail;-><init>(Ljava/lang/String;ILjava/lang/String;)V\n\n    const/16 v7, 0x13\n\n    aput-object v0, v11, v7\n'),
  ],
  ('SettingsActivity', 'buildPageUnsafe(Ljava/lang/String;)Landroid/view/View;'): [
    ('    nop\n\n    .line 526\n',
     '    invoke-static/range {p0 .. p1}, Lcom/starpilot/starview/SpeedAlertPage;->hybPage(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;\n    move-result-object v0\n    if-eqz v0, :hyb_speedalert_no\n    return-object v0\n    :hyb_speedalert_no\n    nop\n\n    .line 526\n'),
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
     '    const/4 v9, 0x0\n\n    invoke-static {v6, v13, v7, v8, v9}, Lcom/starpilot/starview/CarUi;->lp$default(Lcom/starpilot/starview/CarUi;IIILjava/lang/Object;)Landroid/widget/LinearLayout$LayoutParams;\n\n    move-result-object v6\n\n    check-cast v6, Landroid/view/ViewGroup$LayoutParams;\n\n    invoke-virtual {v4, v5, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V\n    invoke-virtual {v0, v1, v2}, Lcom/starpilot/starview/HudEditor;->hybFreeSize(Lcom/starpilot/starview/HudLayout$Item;Lcom/starpilot/starview/HudLayout$State;)V\n    invoke-virtual {v0, v1, v2}, Lcom/starpilot/starview/HudEditor;->hybOwnPanel(Lcom/starpilot/starview/HudLayout$Item;Lcom/starpilot/starview/HudLayout$State;)Z\n    move-result v4\n    if-nez v4, :cond_1\n'),
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
  ('MapPane$MapCanvas', 'style()Ljava/lang/String;'): [
    ('    return-object v0\n',
     '    invoke-static {v0}, Lcom/starpilot/starview/GoogleMaps;->style(Ljava/lang/String;)Ljava/lang/String;\n    move-result-object v0\n    return-object v0\n'),
  ],
  ('MapPane$MapCanvas', 'onDraw(Landroid/graphics/Canvas;)V'): [
    ('    const-string v3, "\\u00a9 Mapbox \\u00a9 OpenStreetMap"\n',
     '    const-string v3, "\\u00a9 Mapbox \\u00a9 OpenStreetMap"\n    invoke-static {v3}, Lcom/starpilot/starview/GoogleMaps;->attribution(Ljava/lang/String;)Ljava/lang/String;\n    move-result-object v3\n'),
  ],
  ('MainActivity', 'shareIntent$lambda$18(Ljava/lang/String;Ljava/lang/String;Lcom/starpilot/starview/MainActivity;)V'): [
    ('    :cond_4\n',
     '    :cond_4\n    iget-object v1, v4, Lkotlin/jvm/internal/Ref$ObjectRef;->element:Ljava/lang/Object;\n    check-cast v1, Lcom/starpilot/starview/SharedPlace$Place;\n    invoke-static {p0, v1}, Lcom/starpilot/starview/GoogleMaps;->shared(Ljava/lang/String;Lcom/starpilot/starview/SharedPlace$Place;)Lcom/starpilot/starview/SharedPlace$Place;\n    move-result-object v1\n    iput-object v1, v4, Lkotlin/jvm/internal/Ref$ObjectRef;->element:Ljava/lang/Object;\n'),
  ],
  ('GalaxyRow', '<init>(ILorg/json/JSONObject;)V'): [
    ('    iput p1, p0, Lcom/starpilot/starview/GalaxyRow;->index:I\n',
     '    iput p1, p0, Lcom/starpilot/starview/GalaxyRow;->index:I\n    invoke-static {p0, p2}, Lcom/starpilot/starview/GalaxyMirror;->row(Lcom/starpilot/starview/GalaxyRow;Lorg/json/JSONObject;)Lorg/json/JSONObject;\n    move-result-object p2\n'),
  ],
  ('GalaxySettingsView', 'sliderRow(Lcom/starpilot/starview/GalaxyRow;)V'): [
    ('    invoke-virtual {v13, v0}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V\n',
     '    invoke-virtual {v13, v0}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V\n\n    invoke-static {v13, v9}, Lcom/starpilot/starview/GalaxyMirror;->learned(Landroid/widget/LinearLayout;Lcom/starpilot/starview/GalaxyRow;)V\n'),
  ],
  ('GalaxySettingsView', 'draw(Lorg/json/JSONObject;)V'): [
    ('    invoke-direct/range {p0 .. p0}, Lcom/starpilot/starview/GalaxySettingsView;->backBar()V\n',
     '    invoke-direct/range {p0 .. p0}, Lcom/starpilot/starview/GalaxySettingsView;->backBar()V\n    invoke-virtual {v9, v8}, Lcom/starpilot/starview/GalaxySettingsView;->hybGuide(Ljava/lang/String;)V\n'),
  ],
  ('FastUpdate', 'from70(Lorg/json/JSONObject;)Lorg/json/JSONObject;'): [
    ('    :goto_a\n',
     '    :goto_a\n    invoke-static {v6, v1}, Lcom/starpilot/starview/FastUpdate;->hybPct(Lorg/json/JSONObject;I)I\n    move-result v1\n'),
  ],
  ('SettingsActivity', 'tabletBlinkerPage()Landroid/view/View;'): [
    ('    const-string v3, "With a destination: signals before each left / right turn of the route - about 12 s ahead, after the last cross street, never under 100 ft - and holds it through red lights until the turn is done. Cancel on the blue card."\n',
     '    const-string v3, "With a destination: signals before each left / right turn of the route - about 12 s ahead, after the last cross street, never under 100 ft - and holds it through red lights until the turn is done. Cancel on the blue card. The comma steers into the turn itself only with StarPilot\\\'s Automatic Turn Initiation on (Steering \\u203a Steering & Torque; off until you turn it on)."\n'),
    ('    const-string v2, "StarPilot\\\'s Turn Signal Guidance (Steering \\u203a Steering and Torque, its on/off) makes your blinker under 20 mph a turn. With this on, the tablet checks its street map first: a turn only when there\\\'s a street to turn into on that side within 250 ft (not a one-way coming at you, not a driveway or parking lot) - signalling to pull over isn\\\'t a turn. Where the tablet has no map, the comma decides by itself. The blinker card shows \\"turn kept\\" while there\\\'s a street. Needs the comma update from the Oct 23 sync."\n',
     '    const-string v2, "StarPilot\\\'s Automatic Turn Initiation (Steering \\u203a Steering & Torque, its on/off; off until you turn it on) makes your blinker under 20 mph a turn. With this on, the tablet checks its street map first: a turn only when there\\\'s a street to turn into on that side within 250 ft (not a one-way coming at you, not a driveway or parking lot) - signalling to pull over isn\\\'t a turn. Where the tablet has no map, the comma decides by itself. The blinker card shows \\"turn kept\\" while there\\\'s a street. Needs the comma update from the Oct 23 sync."\n'),
  ],
  ('GalaxySettingsView', 'readoutRow(Lcom/starpilot/starview/GalaxyRow;)V'): [
    ('    invoke-virtual {v1, v4, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V\n',
     '    invoke-virtual {v1, v4, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V\n    iget-object v1, p0, Lcom/starpilot/starview/GalaxySettingsView;->col:Landroid/widget/LinearLayout;\n    invoke-static {v1, p1}, Lcom/starpilot/starview/GalaxyMirror;->links(Landroid/widget/LinearLayout;Lcom/starpilot/starview/GalaxyRow;)V\n'),
  ],
}
