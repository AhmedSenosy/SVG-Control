package com.senosy.svgtask

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class LineViewModelTest : TestCase() {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val viewModel = LineViewModel(context)

    @Test
    fun testParseData() {
        viewModel.parseData()
    }
}