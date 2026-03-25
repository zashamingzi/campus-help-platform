package com.deepcode.campushelp.mapper;

import com.deepcode.campushelp.entity.TaskComment;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TaskCommentMapper {
    @Insert("INSERT INTO task_comment(task_id, user_id, content, create_time) " +
            "VALUES(#{taskId}, #{userId}, #{content}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TaskComment comment);

    @Select("SELECT * FROM task_comment WHERE task_id = #{taskId} ORDER BY create_time DESC")
    List<TaskComment> selectByTaskId(Long taskId);

    @Delete("DELETE FROM task_comment WHERE id = #{id} AND user_id = #{userId}")
    int deleteById(@Param("id") Long id, @Param("userId") Long userId);
}
