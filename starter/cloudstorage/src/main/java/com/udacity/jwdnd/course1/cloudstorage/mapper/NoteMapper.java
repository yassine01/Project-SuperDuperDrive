package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("select * from NOTES where userId = #{userId}")
    List<Note> getUserNotes(Integer userId);

    @Select("select * from NOTES where noteId = #{noteId}")
    Note getNote(Integer noteId);

    @Insert("insert into NOTES (noteTitle, noteDescription, userId) values (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer addNote(Note note);

    @Update("update NOTES set noteTitle = #{noteTitle}, noteDescription = #{noteDescription} where noteId = #{noteId}")
    Integer update(Note note);
    @Delete("delete from NOTES where noteId = #{noteId}")
    Integer delete(Integer noteId);
}
