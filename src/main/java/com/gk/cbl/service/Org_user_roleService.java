package com.gk.cbl.service;

import com.gk.cbl.entity.Org_user_role;
import com.baomidou.mybatisplus.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_user_roleService extends IService<Org_user_role> {

    Set<String> findByUsername(String username);

}
