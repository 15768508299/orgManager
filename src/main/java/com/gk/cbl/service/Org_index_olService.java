package com.gk.cbl.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.entity.Org_index_ol;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 轮播图表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_index_olService extends IService<Org_index_ol> {

    Page<Org_index_ol> indexOlPage(Integer page,Integer size,String where_sql);

    int countByName(String where_sql);
}
