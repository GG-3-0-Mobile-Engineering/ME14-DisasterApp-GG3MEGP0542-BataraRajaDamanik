package com.batara.gigihproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.batara.gigihproject.databinding.ActivityMainBinding
import com.batara.gigihproject.map.MapsFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, MapsFragment())
            .commit()

    }
}