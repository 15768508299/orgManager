package com.gk.cbl.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.entity.Org_user;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_userService extends IService<Org_user> {

    Map<String,Object> getUserMesList(Integer myRows, Integer myPage, String where_sql);

    Page<Org_user> getList(Integer myRows, Integer myPage, String where_sql);

    void deleteByIds(String ids);
}
