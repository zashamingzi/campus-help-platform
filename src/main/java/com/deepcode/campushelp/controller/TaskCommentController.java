package com.deepcode.campushelp.controller;

import com.deepcode.campushelp.entity.Result;
import com.deepcode.campushelp.entity.TaskComment;
import com.deepcode.campushelp.service.TaskCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "任务留言板模块", description = "任务留言新增、查询、删除接口（需登录）")
@Validated
@RestController
@RequestMapping("/task/comment")
public class TaskCommentController {

    @Autowired
    private TaskCommentService taskCommentService;

    @Operation(summary = "新增任务留言")
    @PostMapping("/add")
    public Result<TaskComment> addComment(@RequestBody @Valid @NotNull TaskComment comment) {
        int res = taskCommentService.addComment(comment);
        return res == 1 ? Result.success("新增留言成功", comment) : Result.error("新增留言失败（必填项不能为空）");
    }

    @Operation(summary = "根据任务ID查询留言")
    @GetMapping("/list/{taskId}")
    public Result<List<TaskComment>> getCommentsByTask(@PathVariable @NotNull(message = "任务ID不能为空") Long taskId) {
        List<TaskComment> comments = taskCommentService.getCommentsByTask(taskId);
        return comments != null ? Result.success(comments) : Result.error("暂无留言数据");
    }

    @Operation(summary = "删除留言")
    @PostMapping("/delete")
    public Result<Void> deleteComment(
            @RequestParam @NotNull(message = "留言ID不能为空") Long id,
            @RequestParam @NotNull(message = "用户ID不能为空") Long userId) {
        int res = taskCommentService.deleteComment(id, userId);
        return res == 1 ? Result.success("删除留言成功", null) : Result.error("删除留言失败（留言不存在/无权限）");
    }
}
