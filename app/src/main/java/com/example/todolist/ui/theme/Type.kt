package com.example.todolist.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * 字号阶梯 —— 来源设计规格 §1.2
 *
 * 字体规范：中文 Noto Sans SC，数字/时间 Inter。
 * 集成方式：把 `noto_sans_sc.ttf` / `inter.ttf` 放入 `res/font/`，
 * 然后在此将 `FontFamily.Default` 替换为
 * `FontFamily(Font(R.font.noto_sans_sc), Font(R.font.inter))` 即可。
 */
object Type {
    val bigNumber   = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold,   fontFamily = FontFamily.Default, color = TextPrimary)
    val pageTitle   = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.Default, color = TextPrimary)
    val screenTitle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.Default, color = TextPrimary)
    val body        = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal,  fontFamily = FontFamily.Default, color = TextPrimary)
    val bodySemi    = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.Default, color = TextPrimary)
    val percent     = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold,   fontFamily = FontFamily.Default, color = TextPrimary)
    val caption     = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal,  fontFamily = FontFamily.Default, color = TextSecondary)
    val captionMed  = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium,  fontFamily = FontFamily.Default, color = TextSecondary)
    val legend      = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium,  fontFamily = FontFamily.Default, color = TextMuted)
    val tabLabel    = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium,  fontFamily = FontFamily.Default, color = TextMuted)
    /** 数字 / 时间专用（Inter） */
    val numeral     = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Medium, fontFamily = FontFamily.Default, color = TextPrimary)
}
