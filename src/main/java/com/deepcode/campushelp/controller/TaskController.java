package com.deepcode.campushelp.controller;

import com.deepcode.campushelp.entity.PageResult;
import com.deepcode.campushelp.entity.Result;
import com.deepcode.campushelp.entity.Task;
import com.deepcode.campushelp.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "任务模块", description = "任务发布、接单、完成、查询、分类相关接口（需登录）")
@Validated
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "发布任务")
    @PostMapping("/publish")
    public Result<Task> publish(@RequestBody @Valid @NotNull Task task) {
        int res = taskService.publish(task);
        return res == 1 ? Result.success("发布成功", task) : Result.error("发布失败（必填项不能为空/赏金需大于0）");
    }

    @Operation(summary = "根据ID查询任务")
    @GetMapping("/get/{id}")
    public Result<Task> getTaskById(@PathVariable @NotNull(message = "任务ID不能为空") Long id) {
        Task task = taskService.getTaskById(id);
        return task != null ? Result.success(task) : Result.error("任务不存在");
    }

    // 适配纯 MyBatis 分页
    @Operation(summary = "分页查询所有任务")
    @GetMapping("/page")
    public Result<PageResult<Task>> getTaskPage(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        return Result.success(taskService.getTaskPage(current, size));
    }

    @Operation(summary = "根据发布人ID查询任务")
    @GetMapping("/publisher/{publisherId}")
    public Result<List<Task>> getTasksByPublisher(@PathVariable @NotNull(message = "发布人ID不能为空") Long publisherId) {
        List<Task> tasks = taskService.getTasksByPublisher(publisherId);
        return tasks != null ? Result.success(tasks) : Result.error("暂无任务数据");
    }

    @Operation(summary = "根据接单者ID查询任务")
    @GetMapping("/receiver/{receiverId}")
    public Result<List<Task>> getTasksByReceiver(@PathVariable @NotNull(message = "接单者ID不能为空") Long receiverId) {
        List<Task> tasks = taskService.getTasksByReceiver(receiverId);
        return tasks != null ? Result.success(tasks) : Result.error("暂无任务数据");
    }

    @Operation(summary = "根据分类查询任务")
    @GetMapping("/category/{category}")
    public Result<List<Task>> getTasksByCategory(@PathVariable @NotBlank(message = "分类不能为空") String category) {
        List<Task> tasks = taskService.getTasksByCategory(category);
        return tasks != null ? Result.success(tasks) : Result.error("暂无该分类任务数据");
    }

    @Operation(summary = "接单")
    @PostMapping("/receive")
    public Result<Void> receiveTask(
            @RequestParam @NotNull(message = "任务ID不能为空") Long taskId,
            @RequestParam @NotNull(message = "接单者ID不能为空") Long receiverId) {
        int res = taskService.receiveTask(taskId, receiverId);
        return res == 1 ? Result.success("接单成功", null) : Result.error("接单失败（任务不存在/非待接单状态）");
    }

    @Operation(summary = "取消接单")
    @PostMapping("/cancelReceive")
    public Result<Void> cancelReceive(@RequestParam @NotNull(message = "任务ID不能为空") Long taskId) {
        int res = taskService.cancelReceive(taskId);
        return res == 1 ? Result.success("取消接单成功", null) : Result.error("取消接单失败（任务不存在/非进行中状态）");
    }

    @Operation(summary = "完成任务")
    @PostMapping("/complete")
    public Result<Void> completeTask(@RequestParam @NotNull(message = "任务ID不能为空") Long taskId) {
        int res = taskService.completeTask(taskId);
        return res == 1 ? Result.success("完成任务成功", null) : Result.error("完成任务失败（任务不存在/非进行中状态）");
    }

    @Operation(summary = "上传任务图片/凭证")
    @PostMapping("/uploadPic")
    public Result<Void> uploadTaskPic(
            @RequestParam @NotNull(message = "任务ID不能为空") Long taskId,
            @RequestParam @NotBlank(message = "图片URL不能为空") String picUrl) {
        int res = taskService.uploadTaskPic(taskId, picUrl);
        return res == 1 ? Result.success("图片上传成功", null) : Result.error("图片上传失败（任务不存在）");
    }
}