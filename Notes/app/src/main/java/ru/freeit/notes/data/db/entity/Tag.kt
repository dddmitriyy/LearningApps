package ru.freeit.notes.data.db.entity

import androidx.room.ColumnInfo
import ru.freeit.notes.domain.entity.Tag as DomainTag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class Tag(
    val title: String,
    @ColumnInfo(name = "note_id")
    val noteId: Long,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
) {
    fun toDomain() = DomainTag(id, title, noteId)
}