package com.example.todolist.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.todolist.ui.components.MainTopBar
import com.example.todolist.ui.components.ProgressBar
import com.example.todolist.ui.components.TodoListItem
import com.example.todolist.ui.navigation.Routes
import com.example.todolist.ui.navigation.rememberRepository
import com.example.todolist.ui.theme.Accent
import com.example.todolist.ui.theme.Background
import com.example.todolist.ui.theme.Card
import com.example.todolist.ui.theme.CardPadding
import com.example.todolist.ui.theme.CardRadius
import com.example.todolist.ui.theme.RowGap
import com.example.todolist.ui.theme.ScreenPadding
import com.example.todolist.ui.theme.SectionGap
import com.example.todolist.ui.theme.SectionGapLg
import com.example.todolist.ui.theme.TextSecondary
import com.example.todolist.ui.theme.Type
import com.example.todolist.util.headerDateLabel
import kotlinx.coroutines.flow.collectAsState

@Composable
fun HomeScreen(nav: NavHostController) {
    val repository = rememberRepository()
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory(repository))
    val todos by viewModel.todos.collectAsState(initial = emptyList())

    val completed = todos.count { it.completedToday && it.countTowardToday }
    val total = todos.size
    val percent = if (total == 0) 0 else (completed * 100 / total)

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        MainTopBar(
            dateLabel = headerDateLabel(),
            title = "待办清单",
            onAdd = { nav.navigate(Routes.edit(-1L)) }
        )

        Spacer(Modifier.height(SectionGap))

        Box(Modifier.padding(horizontal = ScreenPadding)) {
            TodayProgressCard(percent = percent, completed = completed, total = total)
        }

        Spacer(Modifier.height(SectionGapLg))
        Text(
            "进行中的事项",
            style = Type.captionMed,
            color = TextSecondary,
            modifier = Modifier.padding(horizontal = ScreenPadding)
        )
        Spacer(Modifier.height(12.dp))

        Column(
            Modifier.padding(horizontal = ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(RowGap)
        ) {
            todos.forEach { todo ->
                TodoListItem(
                    todo = todo,
                    onToggle = { viewModel.toggle(todo) },
                    onItemClick = { nav.navigate(Routes.edit(todo.id)) },
                    onBellClick = { nav.navigate(Routes.reminder(todo.id)) }
                )
            }
        }
        Spacer(Modifier.height(SectionGapLg))
    }
}

/** 今日打卡进度卡 —— §3.1 */
@Composable
private fun TodayProgressCard(percent: Int, completed: Int, total: Int) {
    Box(
        Modifier
            .fillMaxWidth()
            .shadow(6.dp, shape = androidx.compose.foundation.shape.RoundedCornerShape(CardRadius),
                spotColor = Accent.copy(alpha = 0.10f))
            .background(Card, androidx.compose.foundation.shape.RoundedCornerShape(CardRadius))
            .padding(CardPadding)
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("今日打卡进度", style = Type.captionMed, color = TextSecondary)
                Text("$percent%", style = Type.percent, color = Accent)
            }
            Spacer(Modifier.height(10.dp))
            ProgressBar(progress = percent)
            Spacer(Modifier.height(8.dp))
            Text("已勾选 $completed / $total 项", style = Type.caption, color = TextSecondary)
        }
    }
}
