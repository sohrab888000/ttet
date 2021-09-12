package com.draco.ludere.ui
import android.widget.Button
import android.widget.RelativeLayout
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ir.tapsell.plus.AdRequestCallback
import ir.tapsell.plus.AdShowListener
import ir.tapsell.plus.TapsellPlus
import ir.tapsell.plus.model.TapsellPlusAdModel
import ir.tapsell.plus.model.TapsellPlusErrorModel
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import kotlinx.android.synthetic.main.activity_interstitial.*
import android.content.Intent

class InterstitialActivity : AppCompatActivity() {


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // val Constraint = findViewById(R.id.Constraint) as ConstraintLayout 
//        setContentView(com.draco.ludere.R.layout.activity_interstitial)
//setContentView(Constraint)

        TapsellPlus.requestInterstitialAd(
                this@InterstitialActivity, "613d8bd6d58a8328919e09ce",
                object : AdRequestCallback() {
                   
                    override fun response(tapsellPlusAdModel : TapsellPlusAdModel) {
                        super.response(tapsellPlusAdModel)
                        if (isDestroyed())
                        return   //startActivity(Intent(this@InterstitialActivity, GameActivity::class.java))    

                       var responseId = tapsellPlusAdModel.getResponseId()

                       
                               TapsellPlus.showInterstitialAd(this@InterstitialActivity, responseId,
                object : AdShowListener() {
                   
                    override fun onOpened(tapsellPlusAdModel : TapsellPlusAdModel) {
                        super.onOpened(tapsellPlusAdModel)
                        //showLogToDeveloper("onOpened", Log.DEBUG)
                    }

                    override fun onClosed(tapsellPlusAdModel : TapsellPlusAdModel) {
                        super.onClosed(tapsellPlusAdModel)
                        startActivity(Intent(this@InterstitialActivity, GameActivity::class.java))    
                    }

                    override fun onError(tapsellPlusErrorModel : TapsellPlusErrorModel) {
                        super.onError(tapsellPlusErrorModel)
                       startActivity(Intent(this@InterstitialActivity, GameActivity::class.java))    
                    }
                })
                       

                    }

                    
                    override fun error(message : String?) {
                        startActivity(Intent(this@InterstitialActivity, GameActivity::class.java))
                       // showLogToDeveloper(message, Log.ERROR);
                    }
                })
    
               
    }


}


