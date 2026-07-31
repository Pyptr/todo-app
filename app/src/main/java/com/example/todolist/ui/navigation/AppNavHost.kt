package com.example.todolist.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.ToDoApplication
import com.example.todolist.data.repository.TodoRepository
import com.example.todolist.ui.components.BottomNav
import com.example.todolist.ui.components.Tab
import com.example.todolist.ui.home.HomeScreen
import com.example.todolist.ui.reminders.RemindersScreen
import com.example.todolist.ui.mine.MineScreen
import com.example.todolist.ui.stats.StatsScreen
import com.example.todolist.ui.editor.EditTodoScreen
import com.example.todolist.ui.reminder.ReminderScreen
import com.example.todolist.ui.ringtone.RingtoneScreen

object Routes {
    const val HOME = "home"
    const val REMINDERS = "reminders"
    const val STATS = "stats"
    const val MINE = "mine"
    const val EDIT = "edit/{todoId}"
    const val REMINDER = "reminder/{todoId}"
    const val RINGTONE = "ringtone/{todoId}"

    fun edit(todoId: Long) = "edit/$todoId"
    fun reminder(todoId: Long) = "reminder/$todoId"
    fun ringtone(todoId: Long) = "ringtone/$todoId"
}

private val MAIN_TABS = setOf(Routes.HOME, Routes.REMINDERS, Routes.STATS, Routes.MINE)

/** 取全局 Repository（通过 Application） */
@Composable
fun rememberRepository(): TodoRepository {
    val app = LocalContext.current.applicationContext as ToDoApplication
    return app.repository
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Routes.HOME
    val showBottom = currentRoute in MAIN_TABS

    Scaffold(
        bottomBar = {
            if (showBottom) {
                BottomNav(
                    currentRoute = currentRoute,
                    onSelect = { route ->
                        navController.navigate(route) {
                            popUpTo(Routes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) { HomeScreen(navController) }
            composable(Routes.REMINDERS) { RemindersScreen(navController) }
            composable(Routes.STATS) { StatsScreen(navController) }
            composable(Routes.MINE) { MineScreen(navController) }

            composable(
                Routes.EDIT,
                arguments = listOf(navArgument("todoId") { type = NavType.LongType })
            ) { back ->
                val id = back.arguments?.getLong("todoId") ?: -1L
                EditTodoScreen(navController, id)
            }
            composable(
                Routes.REMINDER,
                arguments = listOf(navArgument("todoId") { type = NavType.LongType })
            ) { back ->
                val id = back.arguments?.getLong("todoId") ?: -1L
                ReminderScreen(navController, id)
            }
            composable(
                Routes.RINGTONE,
                arguments = listOf(navArgument("todoId") { type = NavType.LongType })
            ) { back ->
                val id = back.arguments?.getLong("todoId") ?: -1L
                RingtoneScreen(navController, id)
            }
        }
    }
}

/** 供 NavHost 内部复用：判断当前 tab 是否选中 */
fun Tab.isSelected(currentRoute: String): Boolean = currentRoute == route
