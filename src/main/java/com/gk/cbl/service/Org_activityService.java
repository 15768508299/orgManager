package com.gk.cbl.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.Bo.ActivityBo;
import com.gk.cbl.entity.Org_act_ol;
import com.gk.cbl.entity.Org_activity;
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
public interface Org_activityService extends IService<Org_activity> {

    Page<Org_activity> selectPageActi(Integer mypage,Integer size,Map query);

    Map<String,Object> addAct(ActivityBo activityBo);

    Map<String,Object> updateAct(ActivityBo activityBo);

    ActivityBo getActivityBoById(Integer actid);

    Page<Org_activity> selectPageExt(Integer myPage, Integer mySize, String where_sql);

    void deleteByIds(String ids);

    Page<Org_act_ol> getOlList(Integer myRows, Integer myPage, String where_sql);
}
