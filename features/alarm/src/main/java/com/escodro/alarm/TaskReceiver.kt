package com.escodro.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import timber.log.Timber

/**
 * [BroadcastReceiver] to be notified by the [android.app.AlarmManager].
 */
internal class TaskReceiver : BroadcastReceiver(), KoinComponent {

    // TODO code commented due to Koin x Robolectric issue
    //  https://github.com/InsertKoinIO/koin/issues/503

//    private val completeTaskUseCase: CompleteTask by inject()
//
//    private val showAlarmUseCase: ShowAlarm by inject()
//
//    private val snoozeAlarmUseCase: SnoozeAlarm by inject()
//
//    private val rescheduleUseCase: RescheduleFutureAlarms by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("onReceive() - intent ${intent?.action}")

        GlobalScope.launch {
            handleIntent(intent)
        }
    }

    private suspend fun handleIntent(intent: Intent?) =
        when (intent?.action) {
//            ALARM_ACTION -> getTaskId(intent)?.let { showAlarmUseCase(it) }
//            COMPLETE_ACTION -> getTaskId(intent)?.let { completeTaskUseCase(it) }
//            SNOOZE_ACTION -> getTaskId(intent)?.let { snoozeAlarmUseCase(it) }
//            Intent.ACTION_BOOT_COMPLETED -> rescheduleUseCase()
            else -> Timber.e("Action not supported")
        }

//    private fun getTaskId(intent: Intent?) = intent?.getLongExtra(EXTRA_TASK, 0)

    companion object {

        const val EXTRA_TASK = "extra_task"

        const val ALARM_ACTION = "com.escodro.alkaa.SET_ALARM"

        const val COMPLETE_ACTION = "com.escodro.alkaa.SET_COMPLETE"

        const val SNOOZE_ACTION = "com.escodro.alkaa.SNOOZE"
    }
}
