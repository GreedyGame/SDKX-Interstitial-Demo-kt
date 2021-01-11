package com.app.sdkxinterstitialdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.sdkxinterstitialdemo.databinding.ActivitySecondBinding
import com.greedygame.core.adview.modals.AdRequestErrors
import com.greedygame.core.interstitial.general.GGInterstitialAd
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener

private const val TAG = "SecondActivity"
class SecondActivity : AppCompatActivity() {
    private var ggInterstitialAd: GGInterstitialAd? = null
    private var shouldShowAd = false
    private lateinit var binding: ActivitySecondBinding

    private val interstitialEventListener = object : GGInterstitialEventsListener {
        override fun onAdClosed() {
            // Setting flag to false to not show the ad again. This covers the case of opening
            // and ad that is already loaded

            // Setting flag to false to not show the ad again. This covers the case of opening
            // and ad that is already loaded
            Log.d(TAG, "Ad Closed - 2")
            shouldShowAd = false
            startActivity(Intent(this@SecondActivity, MainActivity::class.java))
            finish()
        }

        override fun onAdLeftApplication() {
        }

        override fun onAdLoadFailed(cause: AdRequestErrors) {
            Toast.makeText(this@SecondActivity, "Ad Load failed $cause", Toast.LENGTH_SHORT).show()
            //Called when the ad load failed. The reason is available in cause variable
        }

        override fun onAdLoaded() {
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

        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initAndLoadAd()
    }

    private fun initAndLoadAd() {
        ggInterstitialAd = GGInterstitialAd(this, "float-4839")

        binding.showAgainButton.setOnClickListener {
            showAdAgain()
        }

        loadInterstitialAd()
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
        ggInterstitialAd?.loadAd(listener = interstitialEventListener)
    }
}