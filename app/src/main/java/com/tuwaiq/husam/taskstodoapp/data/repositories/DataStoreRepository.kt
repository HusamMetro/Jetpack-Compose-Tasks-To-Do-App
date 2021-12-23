package com.tuwaiq.husam.taskstodoapp.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.tuwaiq.husam.taskstodoapp.data.models.Priority
import com.tuwaiq.husam.taskstodoapp.util.Constants.DARK_THEME_KEY
import com.tuwaiq.husam.taskstodoapp.util.Constants.PREFERENCE_KEY
import com.tuwaiq.husam.taskstodoapp.util.Constants.PREFERENCE_NAME
import com.tuwaiq.husam.taskstodoapp.util.Constants.REMEMBER_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

class DataStoreRepository(private val context: Context) {

    private object PreferencesKey {
        val sortKey = stringPreferencesKey(name = PREFERENCE_KEY)
        val rememberKey = booleanPreferencesKey(name = REMEMBER_KEY)
        val darkThemeKey = booleanPreferencesKey(name = DARK_THEME_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.sortKey] = priority.name
        }
    }

    val readSortState: Flow<String> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val sortState = preferences[PreferencesKey.sortKey] ?: Priority.NONE.name
        sortState
    }

    suspend fun persistRememberState(remember: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.rememberKey] = remember
        }
    }

    val readRememberState: Flow<Boolean> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val rememberState = preferences[PreferencesKey.rememberKey] ?: false
        rememberState
    }

    suspend fun persistDarkThemeState(darkThemeState: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.darkThemeKey] = darkThemeState
        }
    }

    val readDarkThemeState: Flow<Boolean> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val darkTheme = preferences[PreferencesKey.darkThemeKey] ?: false
        darkTheme
    }
}