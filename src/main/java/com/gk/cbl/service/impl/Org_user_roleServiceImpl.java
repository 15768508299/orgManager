package com.gk.cbl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gk.cbl.entity.Org_user_role;
import com.gk.cbl.mapper.Org_user_roleMapper;
import com.gk.cbl.service.Org_user_roleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Service
public class Org_user_roleServiceImpl extends ServiceImpl<Org_user_roleMapper, Org_user_role> implements Org_user_roleService {

    @Override
    public Set<String> findByUsername(String username) {
        Set<String> setrole = new HashSet<>();
        List<Org_user_role> orgUserRoles = this.selectList(new EntityWrapper<Org_user_role>().eq("username", username));
        for (Org_user_role role : orgUserRoles ){
            setrole.add(role.getRole());
        }
        return setrole;
    }
}
