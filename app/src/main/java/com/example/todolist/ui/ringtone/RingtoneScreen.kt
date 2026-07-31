package com.example.todolist.ui.ringtone

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Search
import com.example.todolist.ui.components.DetailTopBar
import com.example.todolist.ui.components.PrimaryButton
import com.example.todolist.ui.navigation.rememberRepository
import com.example.todolist.ui.theme.Accent
import com.example.todolist.ui.theme.AccentTint
import com.example.todolist.ui.theme.Background
import com.example.todolist.ui.theme.Border
import com.example.todolist.ui.theme.BorderSubtle
import com.example.todolist.ui.theme.Card
import com.example.todolist.ui.theme.CardPadding
import com.example.todolist.ui.theme.CardRadius
import com.example.todolist.ui.theme.InputRadius
import com.example.todolist.ui.theme.Placeholder
import com.example.todolist.ui.theme.RowGap
import com.example.todolist.ui.theme.ScreenPadding
import com.example.todolist.ui.theme.SectionGap
import com.example.todolist.ui.theme.SectionGapLg
import com.example.todolist.ui.theme.TextMuted
import com.example.todolist.ui.theme.TextPrimary
import com.example.todolist.ui.theme.TextSecondary
import com.example.todolist.ui.theme.Type

@Composable
fun RingtoneScreen(nav: NavHostController, todoId: Long) {
    val repository = rememberRepository()
    val viewModel: RingtoneViewModel = viewModel(factory = RingtoneViewModel.factory(todoId, repository))

    Column(Modifier.fillMaxSize().background(Background)) {
        DetailTopBar(
            title = "提醒铃声",
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
            // 当前选中预览
            CardSurface {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Accent)
                            .clickable { viewModel.togglePlay() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (viewModel.isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = "播放",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(ringtoneName(viewModel.selectedId), style = Type.bodySemi, color = TextPrimary)
                        Text("当前选中", style = Type.caption, color = TextSecondary)
                    }
                }
            }

            Spacer(Modifier.height(SectionGap))
            Text("系统铃声", style = Type.captionMed, color = TextSecondary)
            Spacer(Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(RowGap)) {
                SYSTEM_RINGTONES.forEach { r ->
                    val sel = r.id == viewModel.selectedId
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, shape = RoundedCornerShape(CardRadius), spotColor = Accent.copy(alpha = 0.10f))
                            .background(if (sel) AccentTint else Card, RoundedCornerShape(CardRadius))
                            .clickable { viewModel.select(r.id) }
                            .padding(CardPadding),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(r.name, style = Type.bodySemi, color = TextPrimary)
                        Box(
                            Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(if (sel) Accent else Card)
                                .border(2.dp, if (sel) Accent else Border, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (sel) {
                                Box(
                                    Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(SectionGap))
            Text("在线搜歌", style = Type.captionMed, color = TextSecondary)
            Spacer(Modifier.height(8.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(InputRadius))
                    .background(Card)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(InputRadius))
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    BasicTextField(
                        value = viewModel.searchQuery,
                        onValueChange = { viewModel.setQuery(it) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = Type.body,
                        decorationBox = { inner ->
                            if (viewModel.searchQuery.isEmpty()) {
                                Text("搜索歌曲名称，例如 River Flows in You", style = Type.body, color = Placeholder)
                            }
                            inner()
                        }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            viewModel.results.forEach { song ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MusicNote,
                        contentDescription = null,
                        tint = Accent,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(song.name, style = Type.bodySemi, color = TextPrimary)
                        if (song.artist != null) {
                            Text(song.artist, style = Type.caption, color = TextSecondary)
                        }
                    }
                    val isD = viewModel.downloaded.contains(song.id)
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(InputRadius))
                            .background(if (isD) AccentTint else Card)
                            .border(1.dp, if (isD) Accent else BorderSubtle, RoundedCornerShape(InputRadius))
                            .clickable { viewModel.download(song.id) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isD) {
                                Icon(
                                    imageVector = Icons.Outlined.Download,
                                    contentDescription = null,
                                    tint = Accent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                            }
                            Text(
                                if (isD) "已下载" else "下载",
                                style = Type.captionMed,
                                color = if (isD) Accent else TextPrimary
                            )
                        }
                    }
                }
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
            PrimaryButton(text = "使用选中铃声") { viewModel.save { nav.popBackStack() } }
        }
    }
}

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
