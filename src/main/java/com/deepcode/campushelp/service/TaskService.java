package com.deepcode.campushelp.service;

import com.deepcode.campushelp.entity.PageResult;
import com.deepcode.campushelp.entity.Task;
import java.util.List;

public interface TaskService {
    int publish(Task task);
    Task getTaskById(Long id);
    // 改用 current/size 手动分页
    PageResult<Task> getTaskPage(Long current, Long size);
    List<Task> getTasksByPublisher(Long publisherId);
    List<Task> getTasksByReceiver(Long receiverId);
    List<Task> getTasksByCategory(String category);
    int receiveTask(Long taskId, Long receiverId);
    int cancelReceive(Long taskId);
    int completeTask(Long taskId);
    int uploadTaskPic(Long taskId, String picUrl);
}