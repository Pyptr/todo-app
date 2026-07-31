package com.example.todolist.ui.reminders

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Notifications
import com.example.todolist.data.model.Todo
import com.example.todolist.ui.navigation.Routes
import com.example.todolist.ui.navigation.rememberRepository
import com.example.todolist.ui.ringtone.ringtoneName
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
import androidx.compose.runtime.collectAsState

@Composable
fun RemindersScreen(nav: NavHostController) {
    val repository = rememberRepository()
    val viewModel: RemindersViewModel = viewModel(factory = RemindersViewModel.factory(repository))
    val todos by viewModel.todos.collectAsState(initial = emptyList())
    val withReminder = todos.filter { it.hasReminder }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "提醒",
            style = Type.pageTitle,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = ScreenPadding, vertical = 16.dp)
        )
        Spacer(Modifier.height(SectionGap))

        if (withReminder.isEmpty()) {
            Text(
                "暂无开启提醒的事项。在「待办」中编辑事项并开启提醒即可在此查看。",
                style = Type.caption,
                color = TextMuted,
                modifier = Modifier.padding(horizontal = ScreenPadding)
            )
        } else {
            Column(
                Modifier.padding(horizontal = ScreenPadding),
                verticalArrangement = Arrangement.spacedBy(RowGap)
            ) {
                withReminder.forEach { todo ->
                    ReminderRow(todo = todo) { nav.navigate(Routes.reminder(todo.id)) }
                }
            }
        }
        Spacer(Modifier.height(SectionGap))
    }
}

@Composable
private fun ReminderRow(todo: Todo, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .shadow(6.dp, shape = RoundedCornerShape(CardRadius), spotColor = Accent.copy(alpha = 0.10f))
            .background(Card, RoundedCornerShape(CardRadius))
            .clickable { onClick() }
            .padding(CardPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(40.dp)
                .background(Accent.copy(alpha = 0.12f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                tint = Accent,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(todo.name, style = Type.bodySemi, color = TextPrimary)
            Spacer(Modifier.height(4.dp))
            Text(
                "${todo.reminderTime ?: "--"} · ${if (todo.repeatDaily) "每天" else "自定义"} · ${ringtoneName(todo.ringtoneId)}",
                style = Type.caption,
                color = TextSecondary
            )
        }
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(22.dp)
        )
    }
}
