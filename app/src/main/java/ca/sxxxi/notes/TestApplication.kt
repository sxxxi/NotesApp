package ca.sxxxi.notes

import android.app.Application
import ca.sxxxi.notes.repository.NotesRepository
import ca.sxxxi.notes.room.db.NoteDb

class TestApplication: Application() {
    lateinit var  notesRepository: NotesRepository

    override fun onCreate() {
        notesRepository = NotesRepository(NoteDb.dao(this))
        super.onCreate()
    }
}