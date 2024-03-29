package ru.freeit.notes.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.freeit.notes.data.db.AppDatabase
import ru.freeit.notes.domain.entity.Note
import ru.freeit.notes.domain.repository.NoteRepository
import ru.freeit.notes.domain.repository.SortingType

class NoteRepositoryImpl(appDatabase: AppDatabase, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : NoteRepository {

    private val noteDao = appDatabase.noteDao()

    override suspend fun notesBy(sortingType: SortingType) = withContext(dispatcher) {
        val dbNotes =  when (sortingType) {
            SortingType.TITLE -> {
                noteDao.notesByTitle()
            }
            SortingType.CREATED_DATE -> {
                noteDao.notesByCreatedDate()
            }
            SortingType.EDITED_DATE -> {
                noteDao.notesByEditedDate()
            }
            SortingType.NO_SORTING -> {
                noteDao.notes()
            }
        }
        dbNotes.map { note -> note.toDomain() }
    }

    override suspend fun add(note: Note) = withContext(dispatcher) { noteDao.add(note.toDb()) }
    override suspend fun noteBy(noteId: Long) = withContext(dispatcher) { noteDao.noteBy(noteId).toDomain() }
    override suspend fun remove(note: Note) = withContext(dispatcher) { noteDao.remove(note.toDb()) }
    override suspend fun update(note: Note) = withContext(dispatcher) { noteDao.update(note.toDb()) }
}