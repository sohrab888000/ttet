package com.draco.ludere

import ir.tapsell.sdk.Tapsell
import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Tapsell.initialize(this,"psreiipoekankonenfrhgassmaqpplhbqgiajtnggmgtaftspqcnbpleoqffprrlmnfili")//insert key of each application here
    }
}
