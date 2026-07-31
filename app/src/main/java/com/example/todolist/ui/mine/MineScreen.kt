package com.example.todolist.ui.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Person
import com.example.todolist.ui.navigation.Routes
import com.example.todolist.ui.theme.Accent
import com.example.todolist.ui.theme.Background
import com.example.todolist.ui.theme.Card
import com.example.todolist.ui.theme.CardPadding
import com.example.todolist.ui.theme.CardRadius
import com.example.todolist.ui.theme.RowGap
import com.example.todolist.ui.theme.ScreenPadding
import com.example.todolist.ui.theme.SectionGap
import com.example.todolist.ui.theme.TextMuted
import com.example.todolist.ui.theme.TextPrimary
import com.example.todolist.ui.theme.TextSecondary
import com.example.todolist.ui.theme.Type

@Composable
fun MineScreen(nav: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Text(
            "我的",
            style = Type.pageTitle,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = ScreenPadding, vertical = 16.dp)
        )
        Spacer(Modifier.height(SectionGap))

        // 个人卡片
        Row(
            Modifier
                .padding(horizontal = ScreenPadding)
                .fillMaxWidth()
                .shadow(6.dp, shape = RoundedCornerShape(CardRadius), spotColor = Accent.copy(alpha = 0.10f))
                .background(Card, RoundedCornerShape(CardRadius))
                .padding(CardPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(56.dp)
                    .background(Accent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = androidx.compose.ui.graphics.Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(Modifier.width(14.dp))
            Column {
                Text("我的清单", style = Type.bodySemi, color = TextPrimary)
                Spacer(Modifier.height(4.dp))
                Text("坚持，是一件温柔的事", style = Type.caption, color = TextSecondary)
            }
        }

        Spacer(Modifier.height(SectionGap))

        Column(
            Modifier.padding(horizontal = ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(RowGap)
        ) {
            MineRow("打卡统计") { nav.navigate(Routes.STATS) }
            MineRow("提醒管理") { nav.navigate(Routes.REMINDERS) }
            MineRow("关于待办清单") { /* 版本信息 */ }
        }

        Spacer(Modifier.height(SectionGap))
        Text(
            "待办清单 App · v1.0",
            style = Type.caption,
            color = TextMuted,
            modifier = Modifier.padding(horizontal = ScreenPadding)
        )
    }
}

@Composable
private fun MineRow(title: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .shadow(6.dp, shape = RoundedCornerShape(CardRadius), spotColor = Accent.copy(alpha = 0.10f))
            .background(Card, RoundedCornerShape(CardRadius))
            .clickable { onClick() }
            .padding(CardPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = Type.bodySemi, color = TextPrimary)
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(22.dp)
        )
    }
}
