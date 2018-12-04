package self.personal.com.yogapp.koin

import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import self.personal.com.yogapp.data.local.LocalDataSource
import self.personal.com.yogapp.data.network.RemoteDataSource
import self.personal.com.yogapp.data.repository.Repository

object AppModule {

    private val retrofitModule: Module = module {
        single {
            //todo create retrofit module
        }
    }

    private val remoteDataModule: Module = module {
        single {
            RemoteDataSource()
        }
    }

    private val localDataModule: Module = module {
        single {
            LocalDataSource()
        }
    }

    private val repoModule: Module = module{
        single{
            Repository()
        }
    }

    val appModules = listOf<Module>(retrofitModule, remoteDataModule, localDataModule, repoModule)
}