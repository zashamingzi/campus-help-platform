package com.deepcode.campushelp.service;

import com.deepcode.campushelp.entity.TaskComment;
import java.util.List;

public interface TaskCommentService {
    int addComment(TaskComment comment);
    List<TaskComment> getCommentsByTask(Long taskId);
    int deleteComment(Long id, Long userId);
}
