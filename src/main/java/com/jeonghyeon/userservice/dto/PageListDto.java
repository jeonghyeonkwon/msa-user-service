package com.jeonghyeon.userservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageListDto<T> {
    private Boolean isFirst;
    private Boolean isLast;
    private int currentPage;
    private int totalPage;
    private long totalElements;
    private int startBlockPage;
    private int endBlockPage;

    private List<T> dataList = new ArrayList<>();

    public PageListDto(
            int currentPage,
            boolean isFirst,
            boolean isLast,
            int totalPage,
            long totalElements,
            List<T> dataList
    ){
        this.currentPage = currentPage;
        this.isFirst = isFirst;
        this.isLast = isLast;
        this.totalPage = totalPage;
        this.totalElements = totalElements;
        this.dataList = dataList;
        this.startBlockPage=((currentPage)/10)*10+1;
        int endBlock = startBlockPage + 10 -1;
        endBlock = totalPage < endBlock?totalPage:endBlock;
        this.endBlockPage =endBlock;
    }

}
