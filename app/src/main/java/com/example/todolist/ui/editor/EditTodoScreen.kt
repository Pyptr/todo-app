package com.example.todolist.ui.editor

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.todolist.ui.components.DetailTopBar
import com.example.todolist.ui.components.PrimaryButton
import com.example.todolist.ui.components.ProgressBar
import com.example.todolist.ui.components.TextInput
import com.example.todolist.ui.components.Toggle
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
import com.example.todolist.ui.theme.TextPrimary
import com.example.todolist.ui.theme.TextSecondary
import com.example.todolist.ui.theme.Type
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight

@Composable
fun EditTodoScreen(nav: NavHostController, todoId: Long) {
    val repository = rememberRepository()
    val viewModel: EditTodoViewModel = viewModel(factory = EditTodoViewModel.factory(todoId, repository))
    val scope = rememberCoroutineScope()
    val isNew = todoId <= 0L

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        DetailTopBar(
            title = if (isNew) "新建待办" else "编辑待办",
            onBack = { nav.popBackStack() },
            actionText = "保存",
            onAction = {
                viewModel.save { nav.popBackStack() }
            }
        )

        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ScreenPadding)
        ) {
            Label("事项名称")
            TextInput(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                placeholder = "例如：阅读 30 分钟"
            )

            Spacer(Modifier.height(SectionGap))

            Row(horizontalArrangement = Arrangement.spacedBy(RowGap)) {
                Column(Modifier.weight(1f)) {
                    Label("总天数")
                    TextInput(
                        value = viewModel.totalDays,
                        onValueChange = { viewModel.totalDays = it.filter { c -> c.isDigit() } },
                        placeholder = "100",
                        keyboardType = KeyboardType.Number
                    )
                }
                Column(Modifier.weight(1f)) {
                    Label("已打卡天数")
                    TextInput(
                        value = viewModel.checkedDays,
                        onValueChange = { viewModel.checkedDays = it.filter { c -> c.isDigit() } },
                        placeholder = "1",
                        keyboardType = KeyboardType.Number
                    )
                }
            }

            Spacer(Modifier.height(SectionGap))

            // 进度预览
            Box(
                Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, shape = RoundedCornerShape(CardRadius), spotColor = Accent.copy(alpha = 0.10f))
                    .background(Card, RoundedCornerShape(CardRadius))
                    .padding(CardPadding)
            ) {
                Column {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("进度预览", style = Type.captionMed, color = TextSecondary)
                        Text("${viewModel.progress}%", style = Type.percent, color = Accent)
                    }
                    Spacer(Modifier.height(10.dp))
                    ProgressBar(progress = viewModel.progress)
                }
            }

            Spacer(Modifier.height(SectionGap))

            // 开关行
            Row(
                Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, shape = RoundedCornerShape(CardRadius), spotColor = Accent.copy(alpha = 0.10f))
                    .background(Card, RoundedCornerShape(CardRadius))
                    .padding(CardPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("勾选后计入今日完成", style = Type.bodySemi, color = TextPrimary)
                Toggle(checked = viewModel.countTowardToday, onCheckedChange = { viewModel.countTowardToday = it })
            }

            Spacer(Modifier.height(SectionGap))

            // 提醒设置入口
            Row(
                Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, shape = RoundedCornerShape(CardRadius), spotColor = Accent.copy(alpha = 0.10f))
                    .background(Card, RoundedCornerShape(CardRadius))
                    .clickable {
                        if (isNew) {
                            scope.launch {
                                val id = viewModel.saveAndGetId()
                                nav.navigate(Routes.reminder(id))
                            }
                        } else {
                            nav.navigate(Routes.reminder(todoId))
                        }
                    }
                    .padding(CardPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("提醒设置", style = Type.bodySemi, color = TextPrimary)
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.height(SectionGapLg))
        }

        // 底部保存按钮
        Box(
            Modifier
                .fillMaxWidth()
                .background(Background)
                .navigationBarsPadding()
                .padding(horizontal = ScreenPadding, vertical = 12.dp)
        ) {
            PrimaryButton(text = "保存待办") {
                viewModel.save { nav.popBackStack() }
            }
        }
    }
}

@Composable
private fun Label(text: String) {
    Text(text, style = Type.captionMed, color = TextSecondary)
    Spacer(Modifier.height(8.dp))
}
