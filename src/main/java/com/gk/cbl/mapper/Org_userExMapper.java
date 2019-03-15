package com.gk.cbl.mapper;

import com.gk.cbl.Bo.UserBo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Mapper
public interface Org_userExMapper {

    List<UserBo> getAll();
}
