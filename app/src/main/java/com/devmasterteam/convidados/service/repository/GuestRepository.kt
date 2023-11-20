package com.devmasterteam.convidados.service.repository

import android.content.Context
import com.devmasterteam.convidados.service.model.GuestModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class GuestRepository(context: Context) {

    private val dataBase = GuestDatabase.getDatabase(context).guestDAO()
    private val firebase = FirebaseFirestore.getInstance()

    fun saveGuest(guest: GuestModel): Boolean {
        val localSaveResult = dataBase.save(guest)

        // Se a operação local foi bem-sucedida, salvar no Firebase
        if (localSaveResult > 0) {
            saveGuestToFirebase(guest)
            return true
        }

        return false
    }

    fun updateGuest(guest: GuestModel): Boolean {
        val localUpdateResult = dataBase.update(guest)

        // Se a operação local foi bem-sucedida, atualizar no Firebase
        if (localUpdateResult > 0) {
            updateGuestInFirebase(guest)
            return true
        }

        return false
    }

    fun deleteGuest(guest: GuestModel) {
        // Deletar localmente
        dataBase.delete(guest)

        // Deletar no Firebase
        deleteGuestInFirebase(guest)
    }

    fun getGuest(id: Int): GuestModel {
        return dataBase.load(id)
    }

    fun getAllGuests(): List<GuestModel> {
        return dataBase.getInvited()
    }

    fun getPresentGuests(): List<GuestModel> {
        return dataBase.getPresent()
    }

    fun getAbsentGuests(): List<GuestModel> {
        return dataBase.getAbsent()
    }

    private fun saveGuestToFirebase(guest: GuestModel) {
        // Salvando no Firestore
        val guestDocument = firebase.collection("guests").document(guest.id.toString())
        guestDocument.set(guest)
    }

    private fun updateGuestInFirebase(guest: GuestModel) {
        // Atualizando no Firestore
        val guestDocument = firebase.collection("guests").document(guest.id.toString())
        guestDocument.set(guest, SetOptions.merge())
    }

    private fun deleteGuestInFirebase(guest: GuestModel) {
        // Deletando no Firestore
        val guestDocument = firebase.collection("guests").document(guest.id.toString())
        guestDocument.delete()
    }
}
