package self.personal.com.yogapp.data.repository

import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import self.personal.com.yogapp.data.network.RemoteDataSource

class Repository: KoinComponent {

    private val remoteDataSource by inject<RemoteDataSource>()

}