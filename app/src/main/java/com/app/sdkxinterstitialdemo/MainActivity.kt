package com.app.sdkxinterstitialdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.greedygame.core.AppConfig
import com.greedygame.core.GreedyGameAds
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.core.interstitial.general.GGInterstitialAd
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}