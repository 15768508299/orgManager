package com.gk.cbl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.entity.Org_new_ol;
import com.gk.cbl.entity.Org_news;
import com.gk.cbl.mapper.Org_newsMapper;
import com.gk.cbl.service.Org_new_olService;
import com.gk.cbl.service.Org_newsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * 社团新闻表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Service
public class Org_newsServiceImpl extends ServiceImpl<Org_newsMapper, Org_news> implements Org_newsService {

    @Autowired
    Org_new_olService newOlService;

    @Override
    public Page<Org_news> selectPageNews(Integer mypage, Integer size, Map quyer) {
        Page<Org_news> page = new Page<>(mypage,size);
        EntityWrapper<Org_news> wrapper = new EntityWrapper<>();
        if (quyer != null){
            if (quyer.containsKey("mesid")){
                wrapper.eq("issueBy",quyer.get("mesid"));
            }
            if (quyer.containsKey("newTheme") && !"".equals(quyer.get("newTheme"))){
                wrapper.like("newTheme", (String) quyer.get("newTheme"));
            }
            if (quyer.containsKey("issueTime") && !"".equals(quyer.get("issueTime"))){
                wrapper.eq("issueTime",quyer.get("issueTime"));
            }
        }
        wrapper.orderAsc(Collections.singleton("id"));
        Page<Org_news> orgNewsPage = this.selectPage(page,wrapper);
        return orgNewsPage;
    }

    @Override
    public void deleteByIds(String ids) {
        String[] idsArray = ids.split(",");
        for(String id:idsArray){
            //同时删除从表记录
            this.delete(new EntityWrapper<Org_news>().eq("id",Integer.parseInt(id)));
            this.newOlService.delete(new EntityWrapper<Org_new_ol>().eq("newid",id));
        }
    }
}
