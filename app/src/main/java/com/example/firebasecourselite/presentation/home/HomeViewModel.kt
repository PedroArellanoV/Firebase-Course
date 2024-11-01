package com.example.firebasecourselite.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasecourselite.presentation.model.Artist
import com.example.firebasecourselite.presentation.model.Player
import com.example.firebasecourselite.presentation.model.Playlist
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val _artist = MutableStateFlow<List<Artist>>(emptyList())
    val artist: StateFlow<List<Artist>> = _artist
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlist: StateFlow<List<Playlist>> = _playlists

    private val _player = MutableStateFlow<Player?>(null)
    val player: StateFlow<Player?> = _player

    private val db: FirebaseFirestore = Firebase.firestore
    private val rtDb: FirebaseDatabase = Firebase.database

    init {
        getArtists()
        getPlaylists()
        getPlayer()
    }

    private fun getPlayer() {
        viewModelScope.launch {
            collectPlayer().collect {
                val player = it.getValue(Player::class.java)
                _player.value = player
            }
        }
    }

    private fun getArtists() {
        viewModelScope.launch {
            val result: List<Artist> = withContext(Dispatchers.IO) {
                getAllArtists()
            }
            _artist.value = result
        }
    }

    private fun getPlaylists() {
        viewModelScope.launch {
            val result: List<Playlist> = withContext(Dispatchers.IO) {
                getAllPlaylists()
            }
            _playlists.value = result
        }
    }

    suspend fun getAllArtists(): List<Artist> {
        return try {
            db.collection("artists")
                .get()
                .await()
                .documents
                .mapNotNull { snapshot ->
                    snapshot.toObject(Artist::class.java)
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getAllPlaylists(): List<Playlist> {
        return try {
            db.collection("playlists")
                .get()
                .await()
                .documents
                .mapNotNull { snapshot ->
                    snapshot.toObject(Playlist::class.java)
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun collectPlayer(): Flow<DataSnapshot> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("pedro log", "Error: ${error.message}")
                close(error.toException())
            }
        }

        val ref = rtDb.reference.child("player")
        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    fun onPlaySelected() {
        if (player.value != null) {
            val currentPlayer = _player.value?.copy(play = !player.value?.play!!)
            val ref = rtDb.reference.child("player")
            ref.setValue(currentPlayer)
        }

    }

}