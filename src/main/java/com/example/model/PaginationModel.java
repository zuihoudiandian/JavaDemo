package com.example.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaginationModel implements Serializable {

    private Integer pageSize = 10;
    private Integer pageNumber = 1;

}
