package com.eemf.sirgoingfar.routinechecker.viewmodels

import android.app.Application
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.eemf.sirgoingfar.database.RoutineOccurrence

/**
 *
 * This is the ViewModel for the RoutineDetail page
 *
 * @property mApplication is the Application instance of the Caller or the LifeCycle owner
 * that's instantiating the ViewModel
 *
 * @property lifecycleOwner is the instance of the ViewModel lifecycle owner
 *
 * @property mRoutineOccurrenceListMutableLiveData is the livedata object of the list of
 * Routine occurrences
 *
 *
 * */
class RoutineDetailOccurrenceListViewModel(mApplication: Application, private val lifecycleOwner: LifecycleOwner) : BaseViewModel(mApplication) {

    private val mRoutineOccurrenceListMutableLiveData: MutableLiveData<List<RoutineOccurrence>> = MutableLiveData()

    fun getRoutineOccurrenceListObserver(): LiveData<List<RoutineOccurrence>> {
        return mRoutineOccurrenceListMutableLiveData
    }

    private fun setRoutineOccurrenceList(routineList: List<RoutineOccurrence>?) {
        setUiState(STATE_LOADED)
        mRoutineOccurrenceListMutableLiveData.postValue(routineList)
    }

    fun getAllRoutineOccurrences(routineId: Int) {
        setUiState(STATE_LOADING)
        mExecutors?.diskIO()?.execute {
            mDb?.dao?.getAllRoutineOccurrences(routineId)?.observe(lifecycleOwner, Observer {
                setRoutineOccurrenceList(it)
            })
        }
    }

    fun updateRoutineOccurrence(clickedRoutineOccurrence: RoutineOccurrence) {
        mExecutors?.diskIO()?.execute {
            mDb?.dao?.updateOccurrence(clickedRoutineOccurrence)
        }
    }
}