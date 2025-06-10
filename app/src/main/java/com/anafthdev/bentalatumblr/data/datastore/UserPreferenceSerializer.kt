package com.anafthdev.bentalatumblr.data.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.anafthdev.bentalatumblr.ProtoUserPreference
import java.io.InputStream
import java.io.OutputStream

object UserPreferenceSerializer : Serializer<ProtoUserPreference> {

    override val defaultValue: ProtoUserPreference
        get() = ProtoUserPreference(
            isFirstInstall = true,
            dailyGoal = 1200.0
        )

    override suspend fun readFrom(input: InputStream): ProtoUserPreference {
        return ProtoUserPreference.ADAPTER.decode(input)
    }

    override suspend fun writeTo(t: ProtoUserPreference, output: OutputStream) {
        ProtoUserPreference.ADAPTER.encode(output, t)
    }

}
