package com.deepcode.campushelp.service.impl;

import com.deepcode.campushelp.entity.TaskComment;
import com.deepcode.campushelp.mapper.TaskCommentMapper;
import com.deepcode.campushelp.service.TaskCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskCommentServiceImpl implements TaskCommentService {

    @Autowired
    private TaskCommentMapper taskCommentMapper;

    @Override
    public int addComment(TaskComment comment) {
        if (comment.getTaskId() == null || comment.getUserId() == null || !StringUtils.hasText(comment.getContent())) {
            return 0;
        }
        comment.setCreateTime(LocalDateTime.now());
        return taskCommentMapper.insert(comment);
    }

    @Override
    public List<TaskComment> getCommentsByTask(Long taskId) {
        return taskId == null ? null : taskCommentMapper.selectByTaskId(taskId);
    }

    @Override
    public int deleteComment(Long id, Long userId) {
        return (id == null || userId == null) ? 0 : taskCommentMapper.deleteById(id, userId);
    }
}
