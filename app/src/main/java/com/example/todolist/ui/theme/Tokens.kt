package com.example.todolist.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** 圆角 Token —— 来源设计规格 §1.3 */
val CardRadius    = 16.dp // 卡片 / 大容器
val InputRadius   = 12.dp // 输入框 / 列表行 / 主按钮
val PillRadius    = 11.dp // 标签（pill）
val ProgressRadius= 4.dp  // 进度条
val ToggleRadius  = 14.dp // 开关轨道
val NavPillRadius = 36.dp // 底部胶囊导航外
val NavTabRadius  = 26.dp // 底部胶囊导航内 tab

/** 间距 Token —— 来源设计规格 §1.4 */
val ScreenPadding = 20.dp // 屏幕左右内边距
val CardPadding   = 16.dp // 卡片内边距
val SectionGap    = 16.dp // 区块间距（小）
val SectionGapLg  = 24.dp // 区块间距（大）
val RowGap        = 12.dp // 列表项间距

/**
 * 卡片柔影：`0 2px 10px rgba(107,168,160,0.10)`（鼠尾草绿低透明柔影）
 * —— 来源设计规格 §1.4
 * 用法：`.sageShadow().background(Card, RoundedCornerShape(CardRadius))`
 */
fun Modifier.sageShadow(
    elevation: Dp = 6.dp,
    shape: RoundedCornerShape = RoundedCornerShape(CardRadius)
): Modifier = this.shadow(
    elevation = elevation,
    shape = shape,
    spotColor = Accent.copy(alpha = 0.10f),
    ambientColor = Accent.copy(alpha = 0.10f),
    clip = false
)
