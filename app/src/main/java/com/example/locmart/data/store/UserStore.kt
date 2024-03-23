package com.example.locmart.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.locmart.data.api.auth.dto.UserDto
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStore @Inject constructor() : BaseStore<UserDto>("user", UserDto::class.java)


