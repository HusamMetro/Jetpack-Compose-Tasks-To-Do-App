package com.tuwaiq.husam.taskstodoapp.util

object Constants {
    const val DATABASE_TABLE = "todo_table"
    const val DATABASE_NAME = "todo_database"

    const val SPLASH_SCREEN = "splash"
    const val LIST_SCREEN = "list/{action}"
    const val TASK_SCREEN = "task/{taskId}"
    const val CHALLENGES_SCREEN = "challenges"
    const val SETTINGS_SCREEN = "settings"
    const val LOGIN_SCREEN = "login"
    const val REGISTER_SCREEN = "register"

    const val LIST_ARGUMENT_KEY = "action"
    const val TASK_ARGUMENT_KEY = "taskId"

    const val PREFERENCE_NAME = "todo_preferences"
    const val PREFERENCE_KEY = "sort_state"
    const val REMEMBER_KEY = "remember_state"
    const val DARK_THEME_KEY = "dark_theme_state"
    const val FIRST_TIME_KEY = "first_time_state"
    const val LANG_KEY = "lang_state"


    const val MAX_TITLE_LENGTH = 50
    const val SPLASH_SCREEN_DELAY = 3000L
}