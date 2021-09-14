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
import java.io.File

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
	    val storagePath: String = (this.getExternalFilesDir(null) ?: this.filesDir).path
        val file = File(storagePath + "Records.txt")
        var fileExists = file.exists()
         if(fileExists){

             var content:String = file.readText()
             
             if(content.equals("0")){//video
               
                 var invertize:String = "614009b70906934e53d3cf78"
                 file.writeText("1") //next time banner

             }
             else{//banner
                 
                 var invertize:String = "613d7af0d58a8328919e09c7"
                 file.writeText("0") //next time video

             }
         
         
         }else{
	 val num = (0..10).random()
	 
	     if (num % 2 == 0){//video
                 var invertize:String = "614009b70906934e53d3cf78"
                 file.writeText("1")   //next time banner
         }else{//banner
                 var invertize:String = "613d7af0d58a8328919e09c7"
                 file.writeText("0")   //next time video
         }
	 }//init the first time invertisement randomly





//invertize is var that is randomly video or banner id
        TapsellPlus.requestInterstitialAd(
                this@InterstitialActivity, invertize ,
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


