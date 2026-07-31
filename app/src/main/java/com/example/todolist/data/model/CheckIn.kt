package com.example.todolist.data.model

import androidx.room.Entity

/**
 * 单日打卡记录 —— 用于统计卡与热力图（§4.6）。
 * count 为该日勾选事项数，作为热力图 5 级强度依据。
 */
@Entity(tableName = "checkins", primaryKeys = ["date"])
data class CheckIn(
    val date: String, // yyyy-MM-dd
    val count: Int = 0
)
