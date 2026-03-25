package com.deepcode.campushelp.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Task {
    private Long id;
    private String title;
    private String description;
    private BigDecimal reward;
    private LocalDateTime deadline;
    private String category;
    private String picUrl;
    private Integer status;
    private Long publisherId;
    private Long receiverId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
