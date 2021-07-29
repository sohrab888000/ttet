package com.draco.ludere.ui
import android.os.AsyncTask
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

                       /*
        new-> just copying
        */
        
    val storagePath: String = (this.getExternalFilesDir(null) ?: this.filesDir).path
    val afile = assets.open( "example.zip" )
    val bfile = File(storagePath + "/example.zip")
    val cfile = File(storagePath + "/example.img")//diffrent for each game
        var fileExists = cfile.exists()
    if(fileExists){

    } else {

    val pgsBar = findViewById(R.id.pBar) as ProgressBar
    val textView = findViewById(R.id.textview) as TextView
    var inStream: InputStream? = null
    var outStream: OutputStream? = null
    inStream = afile
    outStream = FileOutputStream(bfile)
    val buffer = ByteArray(1024)
    var length = inStream.read(buffer)
    while (length    > 0 )
    {
        outStream.write(buffer, 0, length)
        length = inStream.read(buffer)
    }
    inStream.close()
    outStream.close()
    
   // unzip(bfile, storagePath)
   someTask(bfile, storagePath).execute()

        var fileExistscheck = bfile.exists()
            if(fileExistscheck){
              bfile.delete()
              }
        
    }
        /*
        new-> just copying
        */
        
        
        
    }


    /** Called when the user touches the button */
    fun start(view: View) {
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
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun sendMsg(view: View) {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("myket://comment?id=com.draco.ludere.AlisiaDragon")
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
        openURL.data = Uri.parse("myket://details?id=com.draco.ludere.AlisiaDragon")
        startActivity(openURL)
    }

    fun exit_game(view: View) {
        this@MainActivity.finish()
        exitProcess(0)
    }
    
    
        fun unzip(zipFilePath: File, destDirectory: String) {
            
          /*
          new
          */  
            val textView = findViewById(R.id.textview) as TextView
            var current : Double = 0.0
            var prev : Double = -1.0
            val storagePath: String = (this.getExternalFilesDir(null) ?: this.filesDir).path
            val ll = File(storagePath + "/example.zip").length()
            /*
          new
          */  
            
        val destDir = File(destDirectory)
        if (!destDir.exists()) {
            destDir.mkdir()
        }
        ZipFile(zipFilePath).use { zip ->

            zip.entries().asSequence().forEach { entry ->

                zip.getInputStream(entry).use { input ->


                        val filePath = destDirectory + File.separator + entry.name
                        
                        current += entry.getCompressedSize()
                    
                        if (!entry.isDirectory) {
                            // if the entry is a file, extracts it
                            extractFile(input, filePath)
                            /*new
                            */
                           if(prev != current / ll * 100) {
                           prev = current / ll * 100;
                           var toshoow = prev.toInt()    
                           textView.text = "$toshoow %" 
                            /*new
                            */    
                           }
                            
                        } else {
                            // if the entry is a directory, make the directory
                            val dir = File(filePath)
                            dir.mkdir()
                        }

                }

            }
        }
    }
    
        private fun extractFile(inputStream: InputStream, destFilePath: String) {
        val bos = BufferedOutputStream(FileOutputStream(destFilePath))
        val bytesIn = ByteArray(BUFFER_SIZE)
        var read: Int
        while (inputStream.read(bytesIn).also { read = it } != -1) {
            bos.write(bytesIn, 0, read)
        }
        bos.close()
    }
        
        
        
        /*asynctask new
        */
        class someTask(  zipFilePath: File, destDirectory: String  ) : AsyncTask<Void, Void, Void>() {
    
                override fun onPreExecute() {
        super.onPreExecute()
        Toast.makeText(getApplicationContext(),"در حال غیر فشرده سازی",Toast.LENGTH_SHORT).show()  
        Toast.makeText(getApplicationContext(),"لطفا شکیبا باشید",Toast.LENGTH_SHORT).show()  
        pgsBar.setVisibility(View.VISIBLE)
        textView.setVisibility(View.VISIBLE)
            var current : Double = 0.0
            var prev : Double = -1.0
            val storagePath: String = (this.getExternalFilesDir(null) ?: this.filesDir).path
            val ll = File(storagePath + "/example.zip").length()
            var toshoow = prev.toInt()  
        // ...
    }
            
            
            
            override fun doInBackground(){

        val destDir = File(destDirectory)
        if (!destDir.exists()) {
            destDir.mkdir()
        }
        ZipFile(zipFilePath).use { zip ->

            zip.entries().asSequence().forEach { entry ->

                zip.getInputStream(entry).use { input ->


                        val filePath = destDirectory + File.separator + entry.name
                        
                        current += entry.getCompressedSize()
                    
                        if (!entry.isDirectory) {
                            // if the entry is a file, extracts it
                            val bos = BufferedOutputStream(FileOutputStream(filePath))
                            val bytesIn = ByteArray(BUFFER_SIZE)
                            var read: Int
                           while (input.read(bytesIn).also { read = it } != -1) {
                           bos.write(bytesIn, 0, read)
                           }
                           bos.close()
                            /*new
                            */
                           if(prev != current / ll * 100) {
                           prev = current / ll * 100;
                           toshoow = prev.toInt()    
                           publishProgress(toshoow)
                            /*new
                            */    
                           }
                            
                        } else {
                            // if the entry is a directory, make the directory
                            val dir = File(filePath)
                            dir.mkdir()
                        }

                }

            }
        }
            }
            
            
      override fun onProgressUpdate() {
            pgsBar.setProgress(toshoow) //Since it's an inner class, Bar should be able to be called directly
            textView.text = "$toshoow %" 
            }
    
    
    override fun onPostExecute() {
       pgsBar.setVisibility(View.GONE)
       textView.setVisibility(View.GONE)
        // ...
    }
}
         /*asynctask new
        */     
        
        
        
        

}
