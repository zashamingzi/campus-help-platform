package com.deepcode.campushelp.service.impl;

import com.deepcode.campushelp.entity.PageResult;
import com.deepcode.campushelp.entity.Task;
import com.deepcode.campushelp.entity.enums.TaskStatusEnum;
import com.deepcode.campushelp.mapper.TaskMapper;
import com.deepcode.campushelp.service.TaskService;
import com.deepcode.campushelp.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${custom.redis.prefix}")
    private String redisPrefix;
    @Value("${custom.redis.task.expire}")
    private long taskExpire;

    private static final String TASK_PAGE_KEY = "task:page:";

    @Override
    public int publish(Task task) {
        if (!StringUtils.hasText(task.getTitle()) || !StringUtils.hasText(task.getDescription()) ||
                task.getReward() == null || task.getReward().compareTo(java.math.BigDecimal.ZERO) <= 0 ||
                task.getDeadline() == null || task.getPublisherId() == null) {
            return 0;
        }
        task.setStatus(TaskStatusEnum.PENDING.getCode());
        task.setCreateTime(LocalDateTime.now());
        int res = taskMapper.insert(task);
        redisUtil.del(redisPrefix + TASK_PAGE_KEY);
        return res;
    }

    @Override
    public Task getTaskById(Long id) {
        return id == null ? null : taskMapper.selectById(id);
    }

    // 纯 MyBatis 手动分页
    @Override
    public PageResult<Task> getTaskPage(Long current, Long size) {
        String cacheKey = redisPrefix + TASK_PAGE_KEY + current + ":" + size;
        Object cache = redisUtil.get(cacheKey);
        if (cache != null) return (PageResult<Task>) cache;

        Long offset = (current - 1) * size; // 计算偏移量
        List<Task> list = taskMapper.selectPage(offset, size);
        Long total = taskMapper.selectTotalCount(); // 查询总条数

        PageResult<Task> result = new PageResult<>(total, list);
        redisUtil.set(cacheKey, result, taskExpire);
        return result;
    }

    @Override
    public List<Task> getTasksByPublisher(Long publisherId) {
        return publisherId == null ? null : taskMapper.selectByPublisherId(publisherId);
    }

    @Override
    public List<Task> getTasksByReceiver(Long receiverId) {
        return receiverId == null ? null : taskMapper.selectByReceiverId(receiverId);
    }

    @Override
    public List<Task> getTasksByCategory(String category) {
        return !StringUtils.hasText(category) ? null : taskMapper.selectByCategory(category);
    }

    @Override
    public int receiveTask(Long taskId, Long receiverId) {
        if (taskId == null || receiverId == null) return 0;
        Task task = taskMapper.selectById(taskId);
        if (task == null || !TaskStatusEnum.PENDING.getCode().equals(task.getStatus())) return 0;
        int res = taskMapper.receiveTask(taskId, receiverId, TaskStatusEnum.PROCESSING.getCode());
        redisUtil.del(redisPrefix + TASK_PAGE_KEY);
        return res;
    }

    @Override
    public int cancelReceive(Long taskId) {
        if (taskId == null) return 0;
        Task task = taskMapper.selectById(taskId);
        if (task == null || !TaskStatusEnum.PROCESSING.getCode().equals(task.getStatus())) return 0;
        int res = taskMapper.cancelReceive(taskId);
        redisUtil.del(redisPrefix + TASK_PAGE_KEY);
        return res;
    }

    @Override
    public int completeTask(Long taskId) {
        if (taskId == null) return 0;
        Task task = taskMapper.selectById(taskId);
        if (task == null || !TaskStatusEnum.PROCESSING.getCode().equals(task.getStatus())) return 0;
        int res = taskMapper.completeTask(taskId, TaskStatusEnum.COMPLETED.getCode());
        redisUtil.del(redisPrefix + TASK_PAGE_KEY);
        return res;
    }

    @Override
    public int uploadTaskPic(Long taskId, String picUrl) {
        if (taskId == null || !StringUtils.hasText(picUrl)) return 0;
        int res = taskMapper.updatePicUrl(taskId, picUrl);
        redisUtil.del(redisPrefix + TASK_PAGE_KEY);
        return res;
    }
}
