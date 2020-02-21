package com.example.dto;

import com.example.model.PaginationModel;
import lombok.Data;

/**
 * Author by admin, Email xx@xx.com, Date on 2020/2/8.
 * PS: Not easy to write code, please indicate.
 */
@Data
public class QuestionByuser extends PaginationModel {
    private Long  id;
    private String keyword;
}
