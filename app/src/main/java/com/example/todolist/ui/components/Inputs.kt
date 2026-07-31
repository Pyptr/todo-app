package com.example.todolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.theme.BorderSubtle
import com.example.todolist.ui.theme.Card
import com.example.todolist.ui.theme.InputRadius
import com.example.todolist.ui.theme.Placeholder
import com.example.todolist.ui.theme.Type

/** 输入框：高 56，圆角 12，白底 + 细描边，左对齐 —— §2 */
@Composable
fun TextInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(InputRadius))
            .background(Card)
            .border(1.dp, BorderSubtle, RoundedCornerShape(InputRadius))
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = Type.body,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(placeholder, style = Type.body, color = Placeholder)
                }
                innerTextField()
            }
        )
    }
}

/** 时分秒选择格：72×60，圆角 12，居中数字 —— §3.3 */
@Composable
fun TimeBox(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxLength: Int = 2
) {
    Box(
        modifier
            .width(72.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(InputRadius))
            .background(Card)
            .border(1.dp, BorderSubtle, RoundedCornerShape(InputRadius))
    ) {
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it.filter { c -> c.isDigit() }.take(maxLength)) },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = Type.numeral.copy(textAlign = TextAlign.Center),
            decorationBox = { innerTextField ->
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    if (value.isEmpty()) {
                        Text("--", style = Type.numeral, color = Placeholder)
                    }
                    innerTextField()
                }
            }
        )
    }
}

/** 行内标签 pill（如「精确到秒」），主色浅底 —— §3.3 */
@Composable
fun Pill(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier
            .clip(RoundedCornerShape(11.dp))
            .background(com.example.todolist.ui.theme.AccentTint)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, style = Type.legend, color = com.example.todolist.ui.theme.Accent)
    }
}

/** 两个可选项之间的间隔行（用于重复/单位切换等） */
@Composable
fun SegmentedOption(
    selected: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .clip(RoundedCornerShape(11.dp))
            .background(if (selected) com.example.todolist.ui.theme.Accent else com.example.todolist.ui.theme.AccentTint)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, style = Type.legend, color = if (selected) androidx.compose.ui.graphics.Color.White else com.example.todolist.ui.theme.Accent)
    }
}
