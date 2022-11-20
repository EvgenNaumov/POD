package com.naumov.pictureoftheday.room

import android.graphics.drawable.Icon
import androidx.room.*
import com.naumov.pictureoftheday.utils.PriorityEnum

@Dao
interface  NoticeDao{
    annotation class Dao
    @Query("INSERT INTO notice_table (id_section, title, someText,description,priority) VALUES(:id_section,:title, :someText,:description,:priority)")
    fun nativeInsertNotice(id_section:String, title: String, someText:String ,description: String, priority: Int)

    @Query("SELECT * FROM notice_table")
    fun getAllNotice():List<NoticeEntity>

    @Query("SELECT * FROM notice_table INNER JOIN section_table ON notice_table.id_section = section_table.id AND section_table.title = :title ORDER BY notice_table.title ASC")
    fun getNoticeFromSection(title:String):List<NoticeEntity>

    @Query("INSERT INTO section_table (title) VALUES(:title)")
    fun nativeInsertSection(title: String)

    @Query("SELECT * FROM section_table")
    fun getAllSection():List<SectionEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: NoticeEntity)

    @Delete
    fun delete(entity: NoticeEntity)

    @Update
    fun update(entity: NoticeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: SectionEntity)

    @Delete
    fun delete(entity: SectionEntity)

    @Update
    fun update(entity: SectionEntity)


}