package com.eemf.sirgoingfar.routinechecker.viewmodels

import android.app.Application
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
class ViewModelModule {

    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Provides
    internal fun getViewModelFactory(providerMap: Map<Class<out ViewModel>, Provider<ViewModel>>): ViewModelFactory {
        return ViewModelFactory(providerMap)
    }

    @Provides
    @IntoMap
    @ViewModelKey(RoutineListActivityViewModel::class)
    fun getRoutineListActivityViewModel(application: Application, lifecycleOwner: LifecycleOwner): ViewModel {
        return RoutineListActivityViewModel(application, lifecycleOwner)
    }

    @Provides
    @IntoMap
    @ViewModelKey(RoutineDetailOccurrenceListViewModel::class)
    fun getRoutineOccurenceListViewModel(application: Application, lifecycleOwner: LifecycleOwner): ViewModel {
        return RoutineDetailOccurrenceListViewModel(application, lifecycleOwner)
    }
}
