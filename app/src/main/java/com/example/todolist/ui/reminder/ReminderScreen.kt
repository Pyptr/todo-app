package com.example.todolist.ui.reminder

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import com.example.todolist.ui.components.DetailTopBar
import com.example.todolist.ui.components.Pill
import com.example.todolist.ui.components.PrimaryButton
import com.example.todolist.ui.components.SegmentedOption
import com.example.todolist.ui.components.TimeBox
import com.example.todolist.ui.components.Toggle
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
import com.example.todolist.ui.theme.SectionGapLg
import com.example.todolist.ui.theme.TextMuted
import com.example.todolist.ui.theme.TextPrimary
import com.example.todolist.ui.theme.TextSecondary
import com.example.todolist.ui.theme.Type

@Composable
fun ReminderScreen(nav: NavHostController, todoId: Long) {
    val repository = rememberRepository()
    val viewModel: ReminderViewModel = viewModel(factory = ReminderViewModel.factory(todoId, repository))

    Column(Modifier.fillMaxSize().background(Background)) {
        DetailTopBar(
            title = "提醒设置",
            onBack = { nav.popBackStack() },
            actionText = "保存",
            onAction = { viewModel.save { nav.popBackStack() } }
        )

        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = ScreenPadding)
        ) {
            // 开启提醒总开关
            CardSurface {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("开启提醒", style = Type.bodySemi, color = TextPrimary)
                    Toggle(checked = viewModel.hasReminder, onCheckedChange = { viewModel.hasReminder = it })
                }
            }

            if (viewModel.hasReminder) {
                Spacer(Modifier.height(SectionGap))

                // 定时提醒
                CardSurface {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("定时提醒", style = Type.bodySemi, color = TextPrimary)
                        Pill("精确到秒")
                    }
                    Spacer(Modifier.height(14.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TimeBox(value = viewModel.hour) { viewModel.hour = it }
                        Text(":", style = Type.numeral, color = TextMuted, modifier = Modifier.padding(horizontal = 8.dp))
                        TimeBox(value = viewModel.minute) { viewModel.minute = it }
                        Text(":", style = Type.numeral, color = TextMuted, modifier = Modifier.padding(horizontal = 8.dp))
                        TimeBox(value = viewModel.second) { viewModel.second = it }
                    }
                    Spacer(Modifier.height(14.dp))
                    Text("重复", style = Type.captionMed, color = TextSecondary)
                    Spacer(Modifier.height(8.dp))
                    val options = listOf("每天", "工作日", "周末", "自定义")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        options.forEach { opt ->
                            val sel = if (opt == "每天") viewModel.repeatDaily else !viewModel.repeatDaily
                            SegmentedOption(selected = sel, text = opt) { viewModel.repeatDaily = (opt == "每天") }
                        }
                    }
                }

                Spacer(Modifier.height(SectionGap))

                // 间隔提醒
                CardSurface {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("间隔提醒", style = Type.bodySemi, color = TextPrimary)
                        Toggle(checked = viewModel.intervalEnabled, onCheckedChange = { viewModel.intervalEnabled = it })
                    }
                    if (viewModel.intervalEnabled) {
                        Spacer(Modifier.height(12.dp))
                        Text("从设定时间起，每隔一段时间提醒你", style = Type.caption, color = TextSecondary)
                        Spacer(Modifier.height(12.dp))
                        Text("开始时间", style = Type.captionMed, color = TextSecondary)
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TimeBox(value = viewModel.intervalHour) { viewModel.intervalHour = it }
                            Text(":", style = Type.numeral, color = TextMuted, modifier = Modifier.padding(horizontal = 8.dp))
                            TimeBox(value = viewModel.intervalMinute) { viewModel.intervalMinute = it }
                        }
                        Spacer(Modifier.height(12.dp))
                        Text("间隔", style = Type.captionMed, color = TextSecondary)
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TimeBox(value = viewModel.intervalValue, maxLength = 3) { viewModel.intervalValue = it }
                            Spacer(Modifier.width(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                SegmentedOption(
                                    selected = viewModel.intervalUnit == "MINUTE",
                                    text = "分钟"
                                ) { viewModel.intervalUnit = "MINUTE" }
                                SegmentedOption(
                                    selected = viewModel.intervalUnit == "HOUR",
                                    text = "小时"
                                ) { viewModel.intervalUnit = "HOUR" }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(SectionGap))

                // 提醒铃声入口
                CardSurface {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { nav.navigate(Routes.ringtone(todoId)) },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("提醒铃声", style = Type.bodySemi, color = TextPrimary)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(ringtoneName(viewModel.ringtoneId), style = Type.caption, color = TextSecondary)
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Outlined.ChevronRight,
                                contentDescription = null,
                                tint = TextSecondary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            } else {
                Spacer(Modifier.height(SectionGap))
                Text("开启提醒后，可设置定时提醒、间隔提醒与铃声。", style = Type.caption, color = TextMuted)
            }

            Spacer(Modifier.height(SectionGapLg))
        }

        Box(
            Modifier
                .fillMaxWidth()
                .background(Background)
                .navigationBarsPadding()
                .padding(horizontal = ScreenPadding, vertical = 12.dp)
        ) {
            PrimaryButton(text = "保存设置") { viewModel.save { nav.popBackStack() } }
        }
    }
}

/** 卡片容器（柔影 + 圆角 + 白底） */
@Composable
private fun CardSurface(content: @Composable ColumnScope.() -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .shadow(6.dp, shape = RoundedCornerShape(CardRadius), spotColor = Accent.copy(alpha = 0.10f))
            .background(Card, RoundedCornerShape(CardRadius))
            .padding(CardPadding),
        content = content
    )
}
