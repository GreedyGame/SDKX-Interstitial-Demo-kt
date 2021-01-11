package com.app.sdkxinterstitialdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.app.sdkxinterstitialdemo.databinding.ActivityMainBinding
import com.greedygame.core.AppConfig
import com.greedygame.core.GreedyGameAds
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.core.interstitial.general.GGInterstitialAd
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private var ggInterstitialAd: GGInterstitialAd? = null
    private var shouldShowAd = true
    private lateinit var binding: ActivityMainBinding

    private val interstitialEventListener = object : GGInterstitialEventsListener {
        override fun onAdClosed() {
            // Setting flag to false to not show the ad again. This covers the case of opening
            // and ad that is already loaded

            // Setting flag to false to not show the ad again. This covers the case of opening
            // and ad that is already loaded
            Log.d(TAG, "Ad Closed")
            shouldShowAd = false
            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            finish()
        }

        override fun onAdLeftApplication() {
        }

        override fun onAdLoadFailed(cause: AdRequestErrors) {
            binding.progressBar.isVisible = false
            Toast.makeText(this@MainActivity, "Ad Load failed $cause", Toast.LENGTH_SHORT).show()
            //Called when the ad load failed. The reason is available in cause variable
        }

        override fun onAdLoaded() {
            binding.progressBar.isVisible = false
            if(shouldShowAd){
                // Setting flag to false to not show the ad again.
                shouldShowAd = false
                ggInterstitialAd?.show()
            }
        }

        override fun onAdOpened() {
            //Called when ad has been opened
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initAndLoadAd()
    }

    private fun initAndLoadAd() {
        /*
        Initializing the SDK
         */
        val appConfig = AppConfig.Builder(this)
                .withAppId("89221032")
                .build()
        GreedyGameAds.initWith(appConfig, null)
        ggInterstitialAd = GGInterstitialAd(this, "float-4839")

        binding.loadAdAgain.setOnClickListener {
            showAdAgain()
        }

        binding.loadAd.setOnClickListener {
            binding.progressBar.isVisible = true
            loadInterstitialAd()
        }
    }

    private fun showAdAgain() {
        if(ggInterstitialAd?.isAdLoaded != false) {
            /*
             SDKX automatically refresh the ad automatically when ad is closed. If its already
             loaded, we show it here.
             */
            ggInterstitialAd?.show()
            return
        }
        shouldShowAd = true
        loadInterstitialAd()
    }

    private fun loadInterstitialAd() {
        binding.progressBar.isVisible = true
        ggInterstitialAd?.loadAd(listener = interstitialEventListener)
    }
}