package com.example.myapplication


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat

import org.junit.Test

class ResourceCompareTest {

    private val resourceCompare = ResourceCompare()

    @Test
    fun stringResourceSameAsGivenString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceCompare.isEqual(context,R.string.app_name, "UnitTesting")
        assertThat(result)
    }

    @Test
    fun stringResourceDifferentAsGivenString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceCompare.isEqual(context,R.string.app_name, "Hello")
        assertThat(result).isFalse()
    }
}