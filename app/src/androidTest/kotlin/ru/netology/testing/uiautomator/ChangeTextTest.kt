package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import junit.framework.Assert.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


const val SETTINGS_PACKAGE = "com.android.settings"
const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val textToSet = "Netology"

//    @Test
//    fun testInternetSettings() {
//        // Press home
//        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        device.pressHome()
//
//        // Wait for launcher
//        val launcherPackage = device.launcherPackageName
//        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
//        waitForPackage(SETTINGS_PACKAGE)
//
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val intent = context.packageManager.getLaunchIntentForPackage(SETTINGS_PACKAGE)
//        context.startActivity(intent)
//        device.wait(Until.hasObject(By.pkg(SETTINGS_PACKAGE)), TIMEOUT)
//
//        device.findObject(
//            UiSelector().resourceId("android:id/title").instance(0)
//        ).click()
//    }

//    @Test
//    fun testChangeText() {
//        // Press home
//        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        device.pressHome()
//
//        // Wait for launcher
//        val launcherPackage = device.launcherPackageName
//        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
//        waitForPackage(SETTINGS_PACKAGE)
//
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val packageName = context.packageName
//        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
//        context.startActivity(intent)
//        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
//
//
//        device.findObject(By.res(packageName, "userInput")).text = textToSet
//        device.findObject(By.res(packageName, "buttonChange")).click()
//
//        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
//        assertEquals(result, textToSet)
//    }

    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @Before
    fun beforeEachTest() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
    }

//    @Test
//    fun testInternetSettings() {
//        waitForPackage(SETTINGS_PACKAGE)
//
//        device.findObject(
//            UiSelector().resourceId("android:id/title").instance(0)
//        ).click()
//    }

    @Test
    fun testChangeText() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        // Явное ожидание элемента userInput
        val userInputElement = device.wait(
            Until.findObject(By.res(packageName, "userInput")),
            5000
        )
        assertNotNull("Element userInput not found", userInputElement)
        userInputElement.text = textToSet

        // Явное ожидание элемента buttonChange
        val buttonChangeElement = device.wait(
            Until.findObject(By.res(packageName, "buttonChange")),
            5000
        )
        assertNotNull("Element buttonChange not found", buttonChangeElement)
        buttonChangeElement.click()

        // Ожидание обновления UI
        device.waitForIdle()

        // Явное ожидание элемента textToBeChanged после клика
        val resultElement = device.wait(
            Until.findObject(By.res(packageName, "textToBeChanged")),
            5000
        )
        assertNotNull("Element textToBeChanged not found after click", resultElement)

        val result = resultElement.text
        assertEquals(result, textToSet)
    }

    @Test
    fun testEmptyString() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        // Явное ожидание элемента textToBeChanged для получения originalText
        val textToBeChangedElement = device.wait(
            Until.findObject(By.res(packageName, "textToBeChanged")),
            5000
        )
        assertNotNull("Element textToBeChanged not found", textToBeChangedElement)
        val originalText = textToBeChangedElement.text

        val emptyText = ""

        // Явное ожидание элемента userInput
        val userInputElement = device.wait(
            Until.findObject(By.res(packageName, "userInput")),
            5000
        )
        assertNotNull("Element userInput not found", userInputElement)
        userInputElement.text = emptyText

        // Явное ожидание элемента buttonChange
        val buttonChangeElement = device.wait(
            Until.findObject(By.res(packageName, "buttonChange")),
            5000
        )
        assertNotNull("Element buttonChange not found", buttonChangeElement)
        buttonChangeElement.click()

        // Ожидание обновления UI
        device.waitForIdle()

        // Явное ожидание элемента textToBeChanged после клика
        val resultElement = device.wait(
            Until.findObject(By.res(packageName, "textToBeChanged")),
            5000
        )
        assertNotNull("Element textToBeChanged not found after click", resultElement)
        val resultText = resultElement.text

        assertEquals(originalText, resultText)
    }

    @Test
    fun testOpenInNewActivity() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        val testText = "Тестовый текст для новой Activity"

        // Явное ожидание элемента userInput
        val userInputElement = device.wait(
            Until.findObject(By.res(packageName, "userInput")),
            5000
        )
        assertNotNull("Element userInput not found", userInputElement)
        userInputElement.text = testText

        // Явное ожидание элемента buttonActivity
        val buttonActivityElement = device.wait(
            Until.findObject(By.res(packageName, "buttonActivity")),
            5000
        )
        assertNotNull("Element buttonActivity not found", buttonActivityElement)
        buttonActivityElement.click()

        // Ожидание появления новой Activity
        device.waitForIdle()

        // Явное ожидание появления текста в новой Activity
        val newActivityTextElement = device.wait(
            Until.findObject(By.text(testText)),
            5000
        )
        assertNotNull("Text '$testText' not found in new Activity", newActivityTextElement)

        assertEquals(testText, newActivityTextElement.text)
    }

}



