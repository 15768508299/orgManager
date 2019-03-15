package com.gk.cbl.service;

import com.gk.cbl.Bo.sendReceBo;
import com.gk.cbl.entity.Org_send_rece;
import com.baomidou.mybatisplus.service.IService;

import java.net.Inet4Address;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息传递表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_send_receService extends IService<Org_send_rece> {

    Map<String,Object> getUserSends(Integer page, Integer size, Integer userid, Integer isRead);

    List<sendReceBo> userSend(List<Org_send_rece> sendReces);

    sendReceBo findById(Integer id);

    void deleteByIds(String ids);
}
