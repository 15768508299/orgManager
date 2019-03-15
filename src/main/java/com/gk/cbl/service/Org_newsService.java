package com.gk.cbl.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.entity.Org_news;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 社团新闻表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_newsService extends IService<Org_news> {

    Page<Org_news> selectPageNews(Integer mypage, Integer size, Map qume);

    void deleteByIds(String ids);
}
