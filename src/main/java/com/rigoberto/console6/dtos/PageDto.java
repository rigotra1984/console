package com.rigoberto.console6.dtos;

import com.rigoberto.console6.config.ApiConstants;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageDto<T> {
    private List<T> items;
    private Long totalElements;
    private Integer totalPages;
    private Integer pageSize = ApiConstants.PAGE_SIZE;
    private Integer page;
}
