package com.gk.cbl.service;

import com.gk.cbl.Bo.UserBo;
import com.gk.cbl.entity.Org_mes;
import com.gk.cbl.entity.Org_mes_user;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社团与用户关系表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_mes_userService extends IService<Org_mes_user> {

    List<UserBo> getMesUser(Integer mesId);

    List<Org_mes> getMesByUserid(Integer userid);

    List<Integer> getPageUserId(Integer mesid, Integer page, Integer size);

    Map<String,Object> addMesUser(String username,Integer mesid,Integer jobNum);

    void deleteMesUser(Integer userid,Integer mesid);

}
