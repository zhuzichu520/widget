package com.zhuzichu.android.widget.notify

import android.annotation.SuppressLint
import android.os.Bundle
import android.service.notification.StatusBarNotification
import androidx.annotation.VisibleForTesting
import androidx.core.app.NotificationCompat

internal class NotifyExtender : NotificationCompat.Extender {

    internal companion object {
        private const val EXTRA_NOTIFY_EXTENSIONS = "com.zhuzichu.base.notifycation.EXTENSIONS"
        private const val VALID = "notify_valid"
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        internal const val STACKABLE = "stackable"
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        internal const val STACKED = "stacked"
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        internal const val STACK_KEY = "stack_key"
        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        internal const val SUMMARY_CONTENT = "summary_content"

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        internal fun getExtensions(extras: Bundle): Bundle {
            return extras.getBundle(EXTRA_NOTIFY_EXTENSIONS) ?: Bundle()
        }

        internal fun getKey(extras: Bundle): CharSequence? {
            return getExtensions(extras).getCharSequence(STACK_KEY, null)
        }
    }

    var valid: Boolean = false
        internal set
    var stackable: Boolean = false
        internal set
    var stacked: Boolean = false
        internal set
    var stackKey: CharSequence? = null
        internal set
    var stackItems: ArrayList<CharSequence>? = null
        internal set

    var summaryContent: CharSequence? = null
        internal set

    constructor() {
        this.valid = true
    }

    @SuppressLint("NewApi")
    constructor(notification: StatusBarNotification) {
        NotificationCompat.getExtras(notification.notification)?.let { bundle ->
            bundle.getBundle(EXTRA_NOTIFY_EXTENSIONS)?.let {
                loadConfigurationFromBundle(it)
            }
            bundle.getCharSequenceArray(NotificationCompat.EXTRA_TEXT_LINES)?.let {
                stackItems = ArrayList(it.toList())
            }
        }
    }

    override fun extend(builder: NotificationCompat.Builder): NotificationCompat.Builder {
        val notifyExtensions = builder.extras.getBundle(EXTRA_NOTIFY_EXTENSIONS) ?: Bundle()
        loadConfigurationFromBundle(notifyExtensions)

        notifyExtensions.putBoolean(VALID, valid)

        if (stackable) {
            notifyExtensions.putBoolean(STACKABLE, stackable)
        }

        if (!stackKey.isNullOrBlank()) {
            notifyExtensions.putCharSequence(STACK_KEY, stackKey)
        }

        if (stacked) {
            notifyExtensions.putBoolean(STACKED, stacked)
        }

        if (!summaryContent.isNullOrBlank()) {
            notifyExtensions.putCharSequence(SUMMARY_CONTENT, summaryContent)
        }

        builder.extras.putBundle(EXTRA_NOTIFY_EXTENSIONS, notifyExtensions)
        return builder
    }

    private fun loadConfigurationFromBundle(bundle: Bundle) {
        valid = bundle.getBoolean(VALID, valid)
        stackable = bundle.getBoolean(STACKABLE, stackable)
        stacked = bundle.getBoolean(STACKED, stacked)
        stackKey = bundle.getCharSequence(STACK_KEY, stackKey)
        summaryContent = bundle.getCharSequence(SUMMARY_CONTENT, summaryContent)
    }

    internal fun setStackable(stackable: Boolean = true): NotifyExtender {
        this.stackable = stackable
        return this
    }

    internal fun setStacked(stacked: Boolean = true): NotifyExtender {
        this.stacked = stacked
        return this
    }

    internal fun setKey(key: CharSequence?): NotifyExtender {
        this.stackKey = key
        return this
    }

    internal fun setSummaryText(text: CharSequence?): NotifyExtender {
        this.summaryContent = text
        return this
    }
}
