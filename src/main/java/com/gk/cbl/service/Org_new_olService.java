package com.gk.cbl.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.entity.Org_new_ol;
import com.baomidou.mybatisplus.service.IService;
import com.gk.cbl.entity.Org_news;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_new_olService extends IService<Org_new_ol> {

    Page<Org_new_ol> selectPageNews(Integer myPage, Integer myRows, Map<String,Object> quyer);

    void deleteOLByIds(String ids);
}
