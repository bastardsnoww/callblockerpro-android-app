package com.callblockerpro.app.util

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CrashHandler(private val context: Context) : Thread.UncaughtExceptionHandler {

    private val defaultHandler: Thread.UncaughtExceptionHandler? = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        saveCrashReport(throwable)
        
        // Delegate to default handler to kill app naturally
        defaultHandler?.uncaughtException(thread, throwable)
    }

    private fun saveCrashReport(throwable: Throwable) {
        val timestamp = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US).format(Date())
        val filename = "crash_$timestamp.txt"
        val crashFile = File(context.getExternalFilesDir("crashes"), filename)

        try {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            throwable.printStackTrace(pw)
            val stackTrace = sw.toString()

            val report = StringBuilder()
            report.append("Timestamp: $timestamp\n")
            report.append("Device: ${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}\n")
            report.append("Android: ${android.os.Build.VERSION.RELEASE} (SDK ${android.os.Build.VERSION.SDK_INT})\n")
            report.append("\nUsing CallBlockerPro v1.3\n\n")
            report.append("Stack Trace:\n")
            report.append(stackTrace)

            FileOutputStream(crashFile).use {
                it.write(report.toString().toByteArray())
            }
            
            Log.e("CrashHandler", "Crash report saved to: ${crashFile.absolutePath}")
            
        } catch (e: Exception) {
            Log.e("CrashHandler", "Failed to save crash report", e)
        }
    }

    companion object {
        fun init(context: Context) {
            Thread.setDefaultUncaughtExceptionHandler(CrashHandler(context))
        }
        
        fun getCrashReports(context: Context): List<File> {
            val dir = context.getExternalFilesDir("crashes") ?: return emptyList()
            return dir.listFiles { _, name -> name.startsWith("crash_") }?.toList() ?: emptyList()
        }
    }
}
