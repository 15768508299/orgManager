package com.gk.cbl.mapper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gk.cbl.Bo.UserBo;
import com.gk.cbl.entity.Org_mes_user;
import com.gk.cbl.entity.Org_user;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_userMapper extends BaseMapper<Org_user> {



    List<UserBo> selectListBo(@Param("ew") Wrapper<UserBo> id);
}
