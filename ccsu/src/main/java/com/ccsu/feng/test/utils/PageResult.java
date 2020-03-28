package com.ccsu.feng.test.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @create 2020-02-13-20:30
 */
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    private int pageSize = 10;
    private int pageIndex = 1;
    private Long totalCount = 0L;
    private List<T> nodeList = new ArrayList<T>();

    /**
     * 获取分页记录数量
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取当前页序号
     *
     * @return
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置当前页序号
     *
     * @param pageIndex
     */
    public void setPageIndex(int pageIndex) {
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        this.pageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            pageSize = 1;
        }
        this.pageSize = pageSize;
    }


    /**
     * 获取总记录数
     */
    public Long getTotalCount() {
        return totalCount;
    }

    /**
     * 获取总记录数
     *
     * @param totalCount
     */
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 获取Node检索结果列表
     *
     * @return
     */
    public List<T> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<T> nodeList) {
        this.nodeList = nodeList;
    }
}
