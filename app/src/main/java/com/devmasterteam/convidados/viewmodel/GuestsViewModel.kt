package com.devmasterteam.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmasterteam.convidados.service.constants.GuestConstants
import com.devmasterteam.convidados.service.model.GuestModel
import com.devmasterteam.convidados.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int) {
        if (filter == GuestConstants.FILTER.EMPTY) {
            mGuestList.value = mGuestRepository.getAllGuests()
        } else if (filter == GuestConstants.FILTER.PRESENT) {
            mGuestList.value = mGuestRepository.getPresentGuests()
        } else {
            mGuestList.value = mGuestRepository.getAbsentGuests()
        }
    }

    fun delete(id: Int) {
        mGuestRepository.deleteGuest(mGuestRepository.getGuest(id))
    }
}
