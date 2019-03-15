package com.gk.cbl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.entity.Org_new_ol;
import com.gk.cbl.entity.Org_news;
import com.gk.cbl.mapper.Org_new_olMapper;
import com.gk.cbl.service.Org_new_olService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Service
public class Org_new_olServiceImpl extends ServiceImpl<Org_new_olMapper, Org_new_ol> implements Org_new_olService {

    @Override
    public  Page<Org_new_ol> selectPageNews(Integer myPage, Integer myRows, Map<String, Object> quyer) {
        Page<Org_new_ol> page = new Page<>(myPage,myRows);
        EntityWrapper<Org_new_ol> wrapper = new EntityWrapper<>();
        if (quyer != null){
            if (quyer.containsKey("description") && !"".equals(quyer.get("description"))){
                wrapper.like("description", (String) quyer.get("description"));
            }
        }
        wrapper.orderAsc(Collections.singleton("id"));
        Page<Org_new_ol> orgNewsPage = this.selectPage(page,wrapper);
        return orgNewsPage;

    }

    @Override
    public void deleteOLByIds(String ids) {
        String[] idsArray = ids.split(",");
        for(String id:idsArray){
            //同时删除从表记录
            this.delete(new EntityWrapper<Org_new_ol>().eq("id",Integer.parseInt(id)));
        }
    }
}
