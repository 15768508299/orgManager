package com.gk.cbl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.entity.Org_activity;
import com.gk.cbl.entity.Org_index_ol;
import com.gk.cbl.mapper.Org_index_olMapper;
import com.gk.cbl.service.Org_index_olService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * <p>
 * 轮播图表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Service
public class Org_index_olServiceImpl extends ServiceImpl<Org_index_olMapper, Org_index_ol> implements Org_index_olService {

    @Override
    public Page<Org_index_ol> indexOlPage(Integer page, Integer size, String where_sql) {
        Page<Org_index_ol> pageOl = new Page<>(page,size);
        EntityWrapper<Org_index_ol> wrapper = new EntityWrapper<>();
        if (where_sql != null){
            wrapper.like("descrption",where_sql);
        }
        wrapper.orderAsc(Collections.singleton("id"));
        Page<Org_index_ol> orgIndexOlPage = this.selectPage(pageOl, wrapper);
        return orgIndexOlPage;
    }

    @Override
    public int countByName(String where_sql) {
        EntityWrapper<Org_index_ol> wrapper = new EntityWrapper<>();
        if (where_sql != null){
            wrapper.like("descrption",where_sql);
        }
        return this.selectCount(wrapper);
    }
}
