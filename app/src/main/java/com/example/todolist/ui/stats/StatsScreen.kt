package com.example.todolist.ui.stats

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.ui.navigation.rememberRepository
import com.example.todolist.ui.theme.Accent
import com.example.todolist.ui.theme.Background
import com.example.todolist.ui.theme.Card
import com.example.todolist.ui.theme.CardPadding
import com.example.todolist.ui.theme.CardRadius
import com.example.todolist.ui.theme.HeatEmpty
import com.example.todolist.ui.theme.HeatLevels
import com.example.todolist.ui.theme.RowGap
import com.example.todolist.ui.theme.ScreenPadding
import com.example.todolist.ui.theme.SectionGap
import com.example.todolist.ui.theme.SectionGapLg
import com.example.todolist.ui.theme.BorderSubtle
import com.example.todolist.ui.theme.TextMuted
import com.example.todolist.ui.theme.TextSecondary
import com.example.todolist.ui.theme.Type
import com.example.todolist.util.buildHeatmap
import com.example.todolist.util.computeStreak
import com.example.todolist.util.computeThisMonth
import com.example.todolist.util.heatLevel
import androidx.compose.runtime.collectAsState

private val WEEK_LABELS = listOf("一", "二", "三", "四", "五", "六", "日")
private val CELL = 14.dp
private val CELL_GAP = 4.dp

@Composable
fun StatsScreen(nav: androidx.navigation.NavHostController) {
    val repository = rememberRepository()
    val viewModel: StatsViewModel = viewModel(factory = StatsViewModel.factory(repository))
    val checkIns by viewModel.checkIns.collectAsState(initial = emptyList())

    val grid = remember(checkIns) { buildHeatmap(checkIns) }
    val streak = remember(checkIns) { computeStreak(checkIns) }
    val thisMonth = remember(checkIns) { computeThisMonth(checkIns) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "打卡统计",
            style = Type.pageTitle,
            color = com.example.todolist.ui.theme.TextPrimary,
            modifier = Modifier.padding(horizontal = ScreenPadding, vertical = 16.dp)
        )

        Spacer(Modifier.height(SectionGap))

        // 统计卡：连续打卡 / 本月打卡
        Box(
            Modifier
                .padding(horizontal = ScreenPadding)
                .fillMaxWidth()
                .shadow(6.dp, shape = RoundedCornerShape(CardRadius), spotColor = Accent.copy(alpha = 0.10f))
                .background(Card, RoundedCornerShape(CardRadius))
                .padding(CardPadding)
        ) {
            Row(Modifier.fillMaxWidth()) {
                StatBlock(value = streak.toString(), label = "连续打卡", unit = "天", modifier = Modifier.weight(1f))
                Box(
                    Modifier
                        .width(1.dp)
                        .height(48.dp)
                        .background(com.example.todolist.ui.theme.BorderSubtle)
                        .align(Alignment.CenterVertically)
                )
                StatBlock(value = thisMonth.toString(), label = "本月打卡", unit = "天", modifier = Modifier.weight(1f))
            }
        }

        Spacer(Modifier.height(SectionGapLg))

        // 热力图
        Text(
            "近 10 周打卡热度",
            style = Type.captionMed,
            color = TextSecondary,
            modifier = Modifier.padding(horizontal = ScreenPadding)
        )
        Spacer(Modifier.height(12.dp))

        Row(
            Modifier.padding(horizontal = ScreenPadding),
            verticalAlignment = Alignment.Top
        ) {
            // 左侧日标签
            Column(
                Modifier.padding(end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(CELL_GAP)
            ) {
                WEEK_LABELS.forEachIndexed { index, label ->
                    Box(Modifier.height(CELL), contentAlignment = Alignment.CenterStart) {
                        if (index in listOf(0, 2, 4, 6)) {
                            Text(label, style = Type.legend, color = TextMuted)
                        }
                    }
                }
            }
            // 10 列 × 7 行
            Row(horizontalArrangement = Arrangement.spacedBy(CELL_GAP)) {
                grid.forEach { col ->
                    Column(verticalArrangement = Arrangement.spacedBy(CELL_GAP)) {
                        col.forEach { cell ->
                            val color = when {
                                cell.future || cell.count <= 0 -> HeatEmpty
                                else -> HeatLevels[heatLevel(cell.count)]
                            }
                            Box(
                                Modifier
                                    .size(CELL)
                                    .background(color, RoundedCornerShape(3.dp))
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // 图例
        Row(
            Modifier.padding(horizontal = ScreenPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("少", style = Type.legend, color = TextMuted)
            Spacer(Modifier.width(6.dp))
            HeatLevels.forEach { c ->
                Box(
                    Modifier
                        .size(CELL)
                        .background(c, RoundedCornerShape(3.dp))
                )
                Spacer(Modifier.width(4.dp))
            }
            Spacer(Modifier.width(6.dp))
            Text("多", style = Type.legend, color = TextMuted)
        }

        Spacer(Modifier.height(SectionGapLg))
    }
}

@Composable
private fun StatBlock(
    value: String,
    label: String,
    unit: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(value, style = Type.bigNumber, color = Accent)
            Spacer(Modifier.width(4.dp))
            Text(unit, style = Type.caption, color = TextSecondary)
        }
        Spacer(Modifier.height(6.dp))
        Text(label, style = Type.captionMed, color = TextSecondary)
    }
}
