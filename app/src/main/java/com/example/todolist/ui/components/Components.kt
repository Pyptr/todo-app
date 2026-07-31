package com.example.todolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.todolist.data.model.Todo
import com.example.todolist.ui.theme.Accent
import com.example.todolist.ui.theme.Border
import com.example.todolist.ui.theme.BorderSubtle
import com.example.todolist.ui.theme.Card
import com.example.todolist.ui.theme.CardPadding
import com.example.todolist.ui.theme.InputRadius
import com.example.todolist.ui.theme.ProgressRadius
import com.example.todolist.ui.theme.RowGap
import com.example.todolist.ui.theme.TextMuted
import com.example.todolist.ui.theme.TextPrimary
import com.example.todolist.ui.theme.TextSecondary
import com.example.todolist.ui.theme.ToggleRadius
import com.example.todolist.ui.theme.Track
import com.example.todolist.ui.theme.Type

/** 勾选框 24×24，圆角 6 —— §2 */
@Composable
fun Checkbox(
    checked: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(if (checked) Accent else Card)
            .border(1.5.dp, if (checked) Accent else Border, RoundedCornerShape(6.dp))
            .clickable { onToggle() },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "已完成",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

/** 进度条：轨道 Track + 主色填充（§2） */
@Composable
fun ProgressBar(
    progress: Int,
    modifier: Modifier = Modifier,
    height: Dp = 6.dp
) {
    val fraction = (progress.coerceIn(0, 100) / 100f)
    Box(
        modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(ProgressRadius))
            .background(Track)
    ) {
        Box(
            Modifier
                .fillMaxWidth(fraction)
                .fillMaxHeight()
                .clip(RoundedCornerShape(ProgressRadius))
                .background(Accent)
        )
    }
}

/** 开关 48×28，圆角 14 —— §2 */
@Composable
fun Toggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .width(48.dp)
            .height(28.dp)
            .clip(RoundedCornerShape(ToggleRadius))
            .background(if (checked) Accent else BorderSubtle)
            .clickable { onCheckedChange(!checked) }
    ) {
        Box(
            Modifier
                .offset {
                    IntOffset(
                        x = (if (checked) 23 else 3).dp.roundToPx(),
                        y = 3.dp.roundToPx()
                    )
                }
                .size(22.dp)
                .clip(CircleShape)
                .background(Color.White)
                .shadow(2.dp, CircleShape, spotColor = Color.Black.copy(alpha = 0.15f))
        )
    }
}

/** 主按钮：高 54，圆角 12，主色填充白字 —— §2 */
@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(InputRadius),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Accent,
            contentColor = Color.White,
            disabledContainerColor = Accent.copy(alpha = 0.4f),
            disabledContentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        androidx.compose.material3.Text(text, style = Type.bodySemi.copy(color = Color.White))
    }
}

/** 圆形图标按钮 40–44，白底柔影 —— §2 */
@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier
            .size(size)
            .clip(CircleShape)
            .background(Card)
            .shadow(4.dp, CircleShape, spotColor = Accent.copy(alpha = 0.12f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

/** 列表项卡片：勾选框 + 标题/进度文案/进度条 + 提醒铃铛 —— §2 / §3.1 */
@Composable
fun TodoListItem(
    todo: Todo,
    onToggle: () -> Unit,
    onItemClick: () -> Unit,
    onBellClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp), spotColor = Accent.copy(alpha = 0.10f))
            .background(Card, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { onItemClick() }
            .padding(CardPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = todo.completedToday, onToggle = onToggle)
        Spacer(Modifier.width(RowGap))
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                androidx.compose.material3.Text(
                    todo.name,
                    style = Type.bodySemi,
                    color = if (todo.completedToday) TextSecondary else TextPrimary
                )
                if (todo.completedToday) {
                    Spacer(Modifier.width(8.dp))
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(11.dp))
                            .background(Accent.copy(alpha = 0.12f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        androidx.compose.material3.Text(
                            "已勾选",
                            style = Type.legend.copy(color = Accent)
                        )
                    }
                }
            }
            Spacer(Modifier.height(4.dp))
            androidx.compose.material3.Text(
                "${todo.checkedDays}/${todo.totalDays} · 第${todo.checkedDays}天",
                style = Type.caption
            )
            Spacer(Modifier.height(6.dp))
            ProgressBar(progress = todo.progress)
        }
        if (todo.hasReminder) {
            Spacer(Modifier.width(RowGap))
            IconButton(
                onClick = onBellClick,
                size = 40.dp
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "提醒设置",
                    tint = Accent,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
