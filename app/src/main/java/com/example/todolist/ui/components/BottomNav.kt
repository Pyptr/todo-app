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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.theme.Accent
import com.example.todolist.ui.theme.Card
import com.example.todolist.ui.theme.NavPillRadius
import com.example.todolist.ui.theme.NavTabRadius
import com.example.todolist.ui.theme.TextMuted
import com.example.todolist.ui.theme.Type

/** 底部 4 tab 路由 —— §3.1 / §3.5 */
enum class Tab(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    TODO("home", "待办", Icons.Outlined.List),
    REMINDER("reminders", "提醒", Icons.Outlined.Notifications),
    STATS("stats", "统计", Icons.Outlined.BarChart),
    MINE("mine", "我的", Icons.Outlined.Person)
}

/** 底部胶囊导航：4 个 tab，选中态实填主色（§2） */
@Composable
fun BottomNav(
    currentRoute: String,
    onSelect: (String) -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(start = 21.dp, end = 21.dp, bottom = 21.dp, top = 12.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(NavPillRadius))
                .background(Card)
                .shadow(8.dp, RoundedCornerShape(NavPillRadius), spotColor = Accent.copy(alpha = 0.12f))
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Tab.values().forEach { tab ->
                val selected = currentRoute == tab.route
                Box(
                    Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(NavTabRadius))
                        .background(if (selected) Accent else Color.Transparent)
                        .clickable { onSelect(tab.route) }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            tint = if (selected) Color.White else TextMuted,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            tab.label,
                            style = Type.tabLabel.copy(color = if (selected) Color.White else TextMuted)
                        )
                    }
                }
            }
        }
    }
}
