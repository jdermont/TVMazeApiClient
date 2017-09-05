package omg.jd.tvmazeapiclient.components.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import omg.jd.tvmazeapiclient.R
import android.provider.Settings.System.DEFAULT_NOTIFICATION_URI
import android.media.RingtoneManager
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import omg.jd.tvmazeapiclient.job.JobUtil
import java.io.File


class SettingsFragment : PreferenceFragmentCompat() {
    companion object {
        const val RINGTONE_REQUEST_CODE = 69
    }

    lateinit var prefs: SharedPreferences
    private val onPrefChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
        when (key) {
            "KEY_REFRESH_IN_BACKGROUND",
            "KEY_REFRESH_WIFI_ONLY" -> JobUtil.rescheduleUpdateDataJob(context)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        prefs = preferenceManager.sharedPreferences
        setupNotificationPreferences()
        setupRefreshPreferences()
        prefs.registerOnSharedPreferenceChangeListener(onPrefChangeListener)
    }

    override fun onDestroy() {
        prefs.unregisterOnSharedPreferenceChangeListener(onPrefChangeListener)
        super.onDestroy()
    }

    private fun setupNotificationPreferences() {
        val notificationPreference = findPreference("KEY_NOTIFICATION")!!
        val vibrationEnabledPreference = findPreference("KEY_NOTIFICATION_ENABLE_VIBRATION")!!
        val soundEnabledPreference = findPreference("KEY_NOTIFICATION_ENABLE_SOUND")!!
        val soundPreference = findPreference("KEY_NOTIFICATION_SOUND")!!

        notificationPreference.setOnPreferenceChangeListener { preference, newValue ->
            vibrationEnabledPreference.isEnabled = newValue as Boolean
            soundEnabledPreference.isEnabled = newValue as Boolean
            soundPreference.isEnabled = newValue as Boolean
            true
        }
        vibrationEnabledPreference.isEnabled = prefs.areNotificationsEnabled()
        soundEnabledPreference.isEnabled = prefs.areNotificationsEnabled()
        soundPreference.isEnabled = prefs.areNotificationsEnabled()

        setupSoundPreference()
    }

    private fun setupSoundPreference() {
        val soundPreference = findPreference("KEY_NOTIFICATION_SOUND")!!

        val sound = prefs.getNotificationSound()
        when {
            sound == null -> soundPreference.summary = getString(R.string.notification_default)
            sound.isEmpty() -> soundPreference.summary = getString(R.string.notification_silent)
            else -> {
                val uri = Uri.parse(sound)
                val file = File(uri.path)
                val name = file.name
                soundPreference.summary = name
            }
        }
    }

    private fun setupRefreshPreferences() {
        val refreshPreference = findPreference("KEY_REFRESH_IN_BACKGROUND")
        val refreshWifiOnlyPreference = findPreference("KEY_REFRESH_WIFI_ONLY")

        refreshPreference.setOnPreferenceChangeListener { preference, newValue ->
            refreshWifiOnlyPreference.isEnabled = newValue as Boolean
            true
        }
        refreshWifiOnlyPreference.isEnabled = prefs.isRefreshEnabled()
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        preference ?: return super.onPreferenceTreeClick(preference)
        if (preference.key?.equals("KEY_NOTIFICATION_SOUND") == true) {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, Settings.System.DEFAULT_NOTIFICATION_URI)

            val existingValue = prefs.getNotificationSound()
            if (existingValue != null) {
                if (existingValue.isEmpty()) {
                    // Select "Silent"
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, null as Uri?)
                } else {
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(existingValue))
                }
            } else {
                // No ringtone has been selected, set to the default
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Settings.System.DEFAULT_NOTIFICATION_URI)
            }

            startActivityForResult(intent, RINGTONE_REQUEST_CODE)
            return true
        }

        return super.onPreferenceTreeClick(preference)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RINGTONE_REQUEST_CODE && data != null) {
            val ringtone = data.getParcelableExtra<Uri?>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            if (ringtone != null) {
                prefs.setNotificationSound(ringtone.toString())
            } else {
                prefs.setNotificationSound(null)
            }
            setupSoundPreference()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
