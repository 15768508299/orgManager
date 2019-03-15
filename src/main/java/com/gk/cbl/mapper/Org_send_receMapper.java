package com.gk.cbl.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gk.cbl.Bo.sendReceBo;
import com.gk.cbl.entity.Org_send_rece;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息传递表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_send_receMapper extends BaseMapper<Org_send_rece> {


    List<sendReceBo> selectListBo(Map queryMap);

}
