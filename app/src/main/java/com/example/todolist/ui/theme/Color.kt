package com.example.todolist.ui.theme

import androidx.compose.ui.graphics.Color

/** 配色 Token —— 来源设计规格 §1.1 */
val Background    = Color(0xFFF8F6F2) // 页面背景 · 暖奶油
val Card         = Color(0xFFFFFFFF) // 卡片背景
val TextPrimary  = Color(0xFF1A1A1A) // 文字主色
val TextSecondary= Color(0xFF5C5C5C) // 文字次色
val TextMuted    = Color(0xFF9A9A9A) // 文字弱色 / 未选中
val Placeholder  = Color(0xFFBDBDBD) // 占位符
val BorderSubtle = Color(0xFFE8EDEB) // 描边·细
val Border       = Color(0xFFD4DCD8) // 描边·中（未勾选框）
val Accent       = Color(0xFF6BA8A0) // 主色 · 鼠尾草绿
val AccentDark   = Color(0xFF3E8278) // 主色·深（热力图最高强度）
val AccentTint   = Color(0xFFEAF4F2) // 主色·浅底（选中行/标签/铃声）
val Track        = Color(0xFFEDEAE4) // 进度轨道

/** 热力图 5 级强度（由低到高） —— 来源设计规格 §1.1 */
val HeatLevels = listOf(
    Color(0xFFE8EDEB),
    Color(0xFFC7DED8),
    Color(0xFF9CC4BB),
    Color(0xFF6BA8A0),
    Color(0xFF3E8278),
)

/** 无打卡日底色 */
val HeatEmpty = Color(0xFFF0EEEA)
