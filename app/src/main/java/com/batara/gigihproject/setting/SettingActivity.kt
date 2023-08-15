package com.batara.gigihproject.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.batara.gigihproject.MainActivity
import com.batara.gigihproject.R
import com.batara.gigihproject.databinding.ActivitySettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private val settingViewModel: SettingViewModel by viewModels()
    private lateinit var binding : ActivitySettingBinding

    private val notificationId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        with(binding){
            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                settingViewModel.saveThemeSetting(isChecked)
            }

            btnNotification.setOnClickListener{
                showNotification()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showNotification() {
        val channelId = "channelId"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your notification icon
            .setContentTitle("Waspada Ketinggian Air")
            .setContentText("Status : SIAGA 4")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true) // Removes the notification when tapped


        val permission = Manifest.permission.POST_NOTIFICATIONS
        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
            this,
            permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission()
        } else {
            notificationManager.notify(notificationId, notificationBuilder.build())
        }
    }

    private fun requestNotificationPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission),
            notificationId
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            notificationId -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showNotification()
                } else {
                }
            }
        }
    }
}