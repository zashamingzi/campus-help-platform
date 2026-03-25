package com.deepcode.campushelp.mapper;

import com.deepcode.campushelp.entity.Task;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TaskMapper {
    @Insert("INSERT INTO task(title, description, reward, deadline, category, pic_url, status, publisher_id, create_time) " +
            "VALUES(#{title}, #{description}, #{reward}, #{deadline}, #{category}, #{picUrl}, #{status}, #{publisherId}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Task task);

    @Select("SELECT * FROM task WHERE id = #{id}")
    Task selectById(Long id);

    // 纯 MyBatis 分页查询（手动加 limit）
    @Select("SELECT * FROM task ORDER BY create_time DESC LIMIT #{offset}, #{size}")
    List<Task> selectPage(@Param("offset") Long offset, @Param("size") Long size);

    // 查询总条数
    @Select("SELECT COUNT(*) FROM task")
    Long selectTotalCount();

    @Select("SELECT * FROM task WHERE publisher_id = #{publisherId} ORDER BY create_time DESC")
    List<Task> selectByPublisherId(Long publisherId);

    @Select("SELECT * FROM task WHERE receiver_id = #{receiverId} ORDER BY create_time DESC")
    List<Task> selectByReceiverId(Long receiverId);

    @Select("SELECT * FROM task WHERE category = #{category} ORDER BY create_time DESC")
    List<Task> selectByCategory(String category);

    @Update("UPDATE task SET receiver_id = #{receiverId}, status = #{status}, update_time = NOW() WHERE id = #{id} AND status = 0")
    int receiveTask(@Param("id") Long id, @Param("receiverId") Long receiverId, @Param("status") Integer status);

    @Update("UPDATE task SET receiver_id = NULL, status = 0, update_time = NOW() WHERE id = #{id} AND status = 1")
    int cancelReceive(@Param("id") Long id);

    @Update("UPDATE task SET status = #{status}, update_time = NOW() WHERE id = #{id} AND status = 1")
    int completeTask(@Param("id") Long id, @Param("status") Integer status);

    @Update("UPDATE task SET pic_url = #{picUrl}, update_time = NOW() WHERE id = #{id}")
    int updatePicUrl(@Param("id") Long id, @Param("picUrl") String picUrl);
}