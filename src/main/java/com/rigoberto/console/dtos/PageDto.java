package com.rigoberto.console.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageDto<T> {
    private List<T> items;
    private Long totalElements;
    private Integer totalPages;
    private Integer pageSize;
    private Integer page;
}
