package com.gk.cbl.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.Bo.UserBo;
import com.gk.cbl.entity.Org_mes;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社团信息表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_mesService extends IService<Org_mes> {

    Page<Org_mes> selectPageExt(Integer page, Integer size,String where);

    Map<String,Object> getMesUsers(List<Integer> userIds);

    void insertMess(Org_mes mesWithBLOBs, String exId);

    List<UserBo> getUnion(Integer id);

    void updateMes(Org_mes mesWithBLOBs, String exId);

    void deleteByIds(String ids);
}
