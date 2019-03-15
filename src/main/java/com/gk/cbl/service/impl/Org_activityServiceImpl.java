package com.gk.cbl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.Bo.ActivityBo;
import com.gk.cbl.entity.Org_act_ol;
import com.gk.cbl.entity.Org_activity;
import com.gk.cbl.mapper.Org_activityMapper;
import com.gk.cbl.service.Org_act_olService;
import com.gk.cbl.service.Org_activityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gk.cbl.util.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
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
public class Org_activityServiceImpl extends ServiceImpl<Org_activityMapper, Org_activity> implements Org_activityService {

    @Autowired
    Org_act_olService actOlService;

    @Override
    public Page<Org_activity> selectPageActi(Integer mypage, Integer size, Map query) {
        Page<Org_activity> page = new Page<>(mypage,size);
        EntityWrapper<Org_activity> wrapper = new EntityWrapper<>();
        //wrapper.like("mesName",where);
        if (query.containsKey("issueBy")){
            wrapper.eq("issueBy",query.get("issueBy"));
        }
        wrapper.orderDesc(Collections.singleton("id"));
        Page<Org_activity> orgActPage = this.selectPage(page,wrapper);
        return orgActPage;
    }

    @Override
    public Map<String, Object> addAct(ActivityBo activityBo) {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //处理下时间的问题
            resultMap.put("status","success");
            resultMap.put("message", "活动发布成功,请耐心等待结果");
            Org_activity orgActivity = this.Bochangge(activityBo);
            this.insert(orgActivity);
        }catch (Exception e){
            resultMap.put("status","success");
            resultMap.put("message", "发布失败,未知错误");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> updateAct(ActivityBo activityBo) {
        Map resultMap = new HashMap();
        resultMap.put("status","success");
        resultMap.put("message","编辑成功");
        try {
            Org_activity orgActivity = this.Bochangge(activityBo);
            this.updateById(orgActivity);
        }catch (Exception e){
            resultMap.put("status","fail");
            resultMap.put("message","编辑失败,未知错误");
        }
        return resultMap;
    }

    @Override
    public ActivityBo getActivityBoById(Integer actid) {
        Org_activity activityEx = this.selectOne(new EntityWrapper<Org_activity>().eq("id",actid));
        ActivityBo activityBo = this.Orgchangge(activityEx);
        return activityBo;
    }

    @Override
    public Page<Org_activity> selectPageExt(Integer myPage, Integer mySize, String where_sql) {
        Page<Org_activity> page = new Page<>(myPage,mySize);
        EntityWrapper<Org_activity> wrapper = new EntityWrapper<>();
        if (where_sql != null) {
            JSONObject jasonObject = JSONObject.parseObject(where_sql);
            Map<String,Object> map =  jasonObject;
            if (map.get("issueBy") != null && !map.get("issueBy").equals("")) {
                wrapper.eq("issueBy", (String) map.get("issueBy"));
            }
            if (map.get("actTheme") != null && !map.get("actTheme").equals("")) {
                wrapper.like("actTheme", (String) map.get("actTheme"));
            }
            if (map.get("actDate") != null && !map.get("actDate").equals("")) {
                wrapper.eq("actDate", (String) map.get("actDate"));
            }
        }
        wrapper.orderAsc(Collections.singleton("id"));
        Page<Org_activity> orgMesPage = this.selectPage(page,wrapper);
        return orgMesPage;
    }

    @Override
    public void deleteByIds(String ids) {
        String[] idsArray = ids.split(",");
        for(String id:idsArray){
            //同时删除从表记录
            this.delete(new EntityWrapper<Org_activity>().eq("id",Integer.parseInt(id)));
            this.actOlService.delete(new EntityWrapper<Org_act_ol>().eq("actid",id));
        }
    }

    @Override
    public Page<Org_act_ol> getOlList(Integer myPage, Integer myRows, String where_sql) {
        Page<Org_act_ol> pageOl = new Page<>(myPage,myRows);
        EntityWrapper<Org_act_ol> wrapper = new EntityWrapper<>();
        if (where_sql != null){
            wrapper.like("description",where_sql);
        }
        wrapper.orderAsc(Collections.singleton("id"));
        Page<Org_act_ol> orgIndexOlPage = this.actOlService.selectPage(pageOl, wrapper);
        return orgIndexOlPage;
    }

    private Org_activity Bochangge(ActivityBo activityBo){
        Org_activity activity = new Org_activity();
        activity.setId(activityBo.getId());
        activity.setIssueBy(activityBo.getIssueBy());
        activity.setReadNum(0);
        activity.setActDate(TimeUnit.StringToDate(activityBo.getActDateStr()+" 00:00:00"));
        activity.setActDetail(activityBo.getActDetail());
        activity.setActIntroduction(activityBo.getActIntroduction());
        activity.setActTheme(activityBo.getActTheme());
        activity.setActTimeInterval(activityBo.getStartTime()+"-"+activityBo.getEndTime());
        activity.setIssueDate(TimeUnit.getDate());
        return activity;
    }

    /**
     * 这个是将Org_activity转为子类ActivityBo
     * @param activity
     * @return
     */
    private ActivityBo Orgchangge(Org_activity activity){
        ActivityBo activityBo = new ActivityBo();
        activityBo.setId(activity.getId());
        activityBo.setIssueBy(activity.getIssueBy());
        activityBo.setReadNum(0);
        activityBo.setActDetail(activity.getActDetail());
        activityBo.setActIntroduction(activity.getActIntroduction());
        activityBo.setActTheme(activity.getActTheme());
        activityBo.setIssueDate(TimeUnit.getDate());
        String[] temp = activity.getActTimeInterval().split("-");
        activityBo.setStartTime(temp[0]);
        activityBo.setEndTime(temp[1]);
        return activityBo;
    }

}
