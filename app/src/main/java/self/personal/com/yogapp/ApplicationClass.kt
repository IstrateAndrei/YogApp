package self.personal.com.yogapp

import android.app.Application
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.android.startKoin
import self.personal.com.yogapp.koin.AppModule

class ApplicationClass: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, AppModule.appModules)
        Hawk.init(this).build()
    }
}