package com.gk.cbl.service;

import com.gk.cbl.entity.Org_act_ol;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_act_olService extends IService<Org_act_ol> {

    void deleteOLByIds(String ids);
}
