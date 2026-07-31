package com.example.todolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.theme.Accent
import com.example.todolist.ui.theme.Card
import com.example.todolist.ui.theme.ScreenPadding
import com.example.todolist.ui.theme.TextMuted
import com.example.todolist.ui.theme.TextPrimary
import com.example.todolist.ui.theme.Type

/** 首页顶部：左（日期 / 大标题）+ 右（添加按钮） —— §3.1 */
@Composable
fun MainTopBar(
    dateLabel: String,
    title: String,
    onAdd: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = ScreenPadding, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(dateLabel, style = Type.captionMed, color = TextMuted)
            Spacer(Modifier.height(4.dp))
            Text(title, style = Type.pageTitle, color = TextPrimary)
        }
        IconButton(onClick = onAdd) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "新建待办",
                tint = Accent,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/** 详情页顶部：返回 + 标题 + 右侧操作（保存） —— §3.2/3.3/3.4 */
@Composable
fun DetailTopBar(
    title: String,
    onBack: () -> Unit,
    actionText: String? = null,
    onAction: (() -> Unit)? = null
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = ScreenPadding, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, size = 40.dp) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "返回",
                    tint = TextPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(title, style = Type.screenTitle, color = TextPrimary)
        }
        if (actionText != null && onAction != null) {
            TextButton(onClick = onAction) {
                Text(actionText, style = Type.bodySemi.copy(color = Accent))
            }
        }
    }
}
