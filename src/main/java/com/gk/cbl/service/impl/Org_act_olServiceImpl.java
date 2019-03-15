package com.gk.cbl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gk.cbl.entity.Org_act_ol;
import com.gk.cbl.entity.Org_activity;
import com.gk.cbl.mapper.Org_act_olMapper;
import com.gk.cbl.service.Org_act_olService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Service
public class Org_act_olServiceImpl extends ServiceImpl<Org_act_olMapper, Org_act_ol> implements Org_act_olService {

    @Override
    public void deleteOLByIds(String ids) {
        String[] idsArray = ids.split(",");
        for(String id:idsArray){
            //同时删除从表记录
            this.delete(new EntityWrapper<Org_act_ol>().eq("id",Integer.parseInt(id)));
        }
    }
}
