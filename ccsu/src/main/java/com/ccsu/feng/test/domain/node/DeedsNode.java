package com.ccsu.feng.test.domain.node;



import com.ccsu.feng.test.domain.base.BaseNode;
import lombok.Data;


/**
 * @author admin
 * @create 2020-01-06-13:53
 * @description 事件
 */
@Data
public class DeedsNode extends BaseNode {


    /**
     * 事件起因
     */
    private String origin;

    /**
     * 事件结果
     */
    private String result;

    /**
     * 事件的具体连接
     */
    private String url;





}
