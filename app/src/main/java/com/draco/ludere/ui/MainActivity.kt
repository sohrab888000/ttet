package com.draco.ludere.ui
import android.net.ConnectivityManager
import java.net.URL
import java.net.URLConnection
import android.content.ContentValues.TAG
import android.app.ProgressDialog
import android.util.Log
import android.view.ViewGroup
import android.os.AsyncTask
import android.content.Context
import android.widget.Toast  
import android.graphics.Paint
import android.widget.TextView
import android.widget.ProgressBar
import java.io.*
import java.util.zip.ZipFile
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.io.FileOutputStream
import android.widget.RelativeLayout
import java.util.*
import kotlin.system.exitProcess
import android.net.Uri
import android.content.Intent
import android.app.Activity
import android.app.Service
import android.content.DialogInterface
import android.hardware.input.InputManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.draco.ludere.R
import com.draco.ludere.gamepad.GamePad
import com.draco.ludere.gamepad.GamePadConfig
//import com.draco.ludere.input.ControllerInput
//import com.draco.ludere.retroview.RetroView
import com.draco.ludere.utils.RetroViewUtils
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import android.graphics.Color
import android.content.pm.ActivityInfo


class MainActivity : AppCompatActivity() {

    
     val BUFFER_SIZE = 4096 * 8
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
             
    }


    /** Called when the user touches the button */
    fun start(view: View) {
	   //for download
	    
	                val ConnectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = ConnectionManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected == true) {
            //    Toast.makeText(this@MainActivity, "Network Available", Toast.LENGTH_LONG).show()

	    
	    //for download
	    
	    
	    
        // Do something in response to button click
        val start_the_game_button = findViewById(R.id.start_the_game_button) as Button
        start_the_game_button.isEnabled = false
        start_the_game_button.visibility = View.INVISIBLE
        val comments = findViewById(R.id.comments) as Button
        comments.isEnabled = false
        comments.visibility = View.INVISIBLE
        val game_page = findViewById(R.id.game_page) as Button
        game_page.isEnabled = false
        game_page.visibility = View.INVISIBLE
        val exit_button = findViewById(R.id.exit_button) as Button
        exit_button.isEnabled = false
        exit_button.visibility = View.INVISIBLE
        val send_email = findViewById(R.id.send_email) as Button
        send_email.isEnabled = false
        send_email.visibility = View.INVISIBLE
        val relative = findViewById(R.id.relative) as RelativeLayout
        relative.setBackgroundResource(0)
        relative.setBackgroundColor(Color.parseColor("#000000"))
        
			
        val storagePath: String = (this.getExternalFilesDir(null) ?: this.filesDir).path
        val cfile = File(storagePath + "/example.iso")//diffrent for each game
        var fileExists = cfile.exists()
    val bfile = File(storagePath + "/system/PPSSPP/example.zip")	
        var fileExistscheck = bfile.exists()
            
    if(fileExists){
            if(fileExistscheck){
              bfile.delete()
              }
                val intent = Intent(this, GameActivity::class.java)
                startActivity(intent)
        } else {
        //val textView = findViewById(R.id.textview) as TextView
       // textView.setVisibility(View.VISIBLE)    
        someTask(this,this).execute()
        }
        
	
	//for download
            } else {
         val storagePath: String = (this.getExternalFilesDir(null) ?: this.filesDir).path
	val cfile = File(storagePath + "/example.iso")//diffrent for each game
        var fileExists = cfile.exists()
	    if(fileExists){
	        Toast.makeText(this@MainActivity, "لطفا اینترنت خود را روشن کنید", Toast.LENGTH_LONG).show()
	    }else{
                Toast.makeText(this@MainActivity, "لطفا اینترنت خود را برای دریافت دیتا روشن کنید", Toast.LENGTH_LONG).show()
	    }
            }
	//for download
    }

    fun sendMsg(view: View) {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("myket://comment?id=com.draco.ludere.subasacD")
        startActivity(openURL)
    }

    fun sendingEmail(view: View) {

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto: siavashiranpak@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "نظر دهی")
        startActivity(intent)

    }

    fun goToPage(view: View) {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("myket://details?id=com.draco.ludere.subasacD")
        startActivity(openURL)
    }

    fun exit_game(view: View) {
        this@MainActivity.finish()
        exitProcess(0)
    }
    
    
        
        
        /*asynctask new
        */
        class someTask( context:Context , mainActivity: MainActivity ) : AsyncTask<Void, String, String>() {
    
            var context: Context = context 
            val roootView = mainActivity
             val BUFFER_SIZE = 4096 * 8
             val pgsBar = roootView.findViewById(R.id.pBar) as ProgressBar
            val textView = roootView.findViewById(R.id.textview) as TextView
             val TAG = "MyMessage"
            var current : Double = 0.0
            var current_copy : Double = 0.0
            var prev : Double = -1.0
		var prev_copy : Double = -1.0
		var prev_download : Double = -1.0
		val storagePath: String = (context.getExternalFilesDir(null) ?: context.filesDir).path
        //    var fd = context.assets.open( "example.zip" )
	//	var ss = fd.get()
			var ll = 7816696 
		        var ll_zip = 7816696
		         var ll_download = 128778240
           // val ll = File(storagePath + "/example.zip").get()
           // var toshoow = prev.toInt()         
            var toshoow = 0
		val zipFilePath = File(storagePath + "/system/PPSSPP/example.zip")
            val destDirectory = storagePath + "/system/PPSSPP/"
		
	        val myProgressDialog = ProgressDialog(context)
//for copy
    val afile = context.assets.open( "example.zip" )
    val bfile = File(storagePath + "/system/PPSSPP/example.zip")	
		
            
     override fun onPreExecute() {
        super.onPreExecute()
        Toast.makeText(context,"برای اجرای اولیه نیاز به آماده سازی وجود دارد",Toast.LENGTH_LONG).show()  
      //  pgsBar.setVisibility(View.VISIBLE)
         // ...
	       	myProgressDialog.setMessage("در حال انجام عملیات...لطفا شکیبا باشید")
		myProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
		myProgressDialog.setCancelable(false)
		myProgressDialog.setMax(100)
	        myProgressDialog.show()
		

    }
            
            
            
            override fun doInBackground(vararg params: Void):String? {
	
		    
		    //copy
		var inStream: InputStream? = null
    var outStream: OutputStream? = null
    inStream = afile
    outStream = FileOutputStream(bfile)
    val buffer = ByteArray(1024*10)
    var length = inStream.read(buffer)
    while (length    > 0 )
    {
	    current_copy += length.toDouble()
	    		if(prev_copy != current_copy / ll * 10) {
                           prev_copy = current_copy / ll * 10
                           toshoow = prev_copy.toInt()    
			   publishProgress(""+toshoow)
                           }   
        outStream.write(buffer, 0, length)
        length = inStream.read(buffer)
    }
    inStream.close()
    outStream.close()
    //copy
    
    
    
        //unzip            
        val destDir = File(destDirectory)
        if (!destDir.exists()) {
            destDir.mkdir()
        }
        ZipFile(zipFilePath).use { zip ->

            zip.entries().asSequence().forEach { entry ->

                zip.getInputStream(entry).use { input ->


                        val filePath = destDirectory + File.separator + entry.name
                                            
                        if (!entry.isDirectory) {
                            // if the entry is a file, extracts it
                            val bos = BufferedOutputStream(FileOutputStream(filePath))
                            val bytesIn = ByteArray(BUFFER_SIZE)
                            var read: Int
                           while (input.read(bytesIn).also { read = it } != -1) {
			   current += read.toDouble()
			   if(prev != current / ll_zip * 10) {
                           prev = current / ll_zip * 10
                           toshoow = prev_copy.toInt() + prev.toInt()    
			   publishProgress(""+toshoow)
                           }   
                           bos.write(bytesIn, 0, read)
                           }
                           bos.close()
                            /*new
                            */

                            
                        } else {
                            // if the entry is a directory, make the directory
                            val dir = File(filePath)
                            dir.mkdir()
                        }

                }

            }
        }
	//unzip
	
	
    
    //download
    
               var url = URL("https://www.googleapis.com/drive/v3/files/1aV2fV4eYAMxT7-eh41jhZJxJ0SfrrcXT?alt=media&key=AIzaSyA8PTOuOLALz7K44026qll_GaUzSnYE7Gw") //put link here
				   
               var connection = url.openConnection()
               connection.connect()

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                var lenghtOfFile = connection.getContentLength()

                // download the file
                var input : InputStream = BufferedInputStream(url.openStream(),
                        8192)

                // Output stream
                var output : OutputStream = FileOutputStream(storagePath + "/example.iso") //choose name of downloading file

                val data = ByteArray(1024)

                var total = 0
                    var count = input.read(data)
                while (count > 0) {
                    total += count
                    // publishing the progress....
                    // After this onProgressUpdate will be called
	            prev_download = (total * 60) / ll_download
		    toshoow = prev_copy.toInt() + prev.toInt() + prev_download.toInt()
                    publishProgress("" + toshoow)

                    // writing data to file
                    output.write(data, 0, count)
		    count = input.read(data)
                }

                // flushing output
                output.flush()

                // closing streams
                output.close()
                input.close()
    
    //download
    
		    
		 
 return "finished"
            }
            
            
      override fun onProgressUpdate(vararg values: String) {
          //super.onProgressUpdate(*values)
	    
          //pgsBar.setProgress(toshoow) //Since it's an inner class, Bar should be able to be called directly
         //   textView.text = "$toshoow %" 
	     // var valu : Int?
          super.onProgressUpdate(values.toString())
          myProgressDialog.setProgress(Integer.parseInt(values[0]))
	      //           Toast.makeText(context,values[0],Toast.LENGTH_SHORT).show()  

            
      }
    
    override fun onPostExecute(values: String) {
	super.onPostExecute(values)
   //     pgsBar.setVisibility(View.GONE)
   //    textView.setVisibility(View.GONE)
	//            Log.i("Completed. Total size: " + values);
   		if(myProgressDialog != null && myProgressDialog.isShowing()){
			myProgressDialog.dismiss()
		}
       
    val bfile = File(storagePath + "/system/PPSSPP/example.zip")	
            var fileExistscheck = bfile.exists()
            if(fileExistscheck){
              bfile.delete()
              }
       
               Toast.makeText(context,"عملیات تکمیل شد...از صبر شما متشکریم",Toast.LENGTH_LONG).show()  
        // showDialog("Downloaded " + values + " bytes");
        val intent = Intent(context, GameActivity::class.java)
        context.startActivity(intent)
        
        // ...
    }
}
         /*asynctask new
        */     
        
        
        
        

}
