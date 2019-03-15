package com.gk.cbl.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.Bo.UserBo;
import com.gk.cbl.entity.Org_mes;
import com.gk.cbl.entity.Org_mes_user;
import com.gk.cbl.entity.Org_user;
import com.gk.cbl.mapper.Org_userMapper;
import com.gk.cbl.service.Org_mes_userService;
import com.gk.cbl.service.Org_userService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Service
public class Org_userServiceImpl extends ServiceImpl<Org_userMapper, Org_user> implements Org_userService {

    @Autowired
    Org_mes_userService mesUserService;


    @Override
    public Map<String,Object> getUserMesList(Integer myPage, Integer myRows, String where_sql) {
        Page<Org_mes_user> page = new Page<>(myPage,myRows);
        EntityWrapper<Org_user> wrapper = new EntityWrapper<>();
        EntityWrapper<Org_mes_user> mesuserwrapper = new EntityWrapper<>();
        Map<String,Object> rqmap = new HashMap<>();
        boolean iswherenull = false;
        if (where_sql != null){
            JSONObject  jasonObject = JSONObject.parseObject(where_sql);
            Map<String,Object> map =  jasonObject;
            if (map.get("mesId") != null && !map.get("mesId").equals("")) {
              mesuserwrapper.eq("mesId",map.get("mesId"));
            }
            if (map.get("userName") != null && !map.get("userName").equals("")) {
                wrapper.like("userName", (String) map.get("userName"));
                iswherenull = true;
            }
            if (map.get("userTrueName") != null && !map.get("userTrueName").equals("")) {
                wrapper.like("userTrueName", (String) map.get("userTrueName"));
                iswherenull = true;
            }
            if (map.get("userIdentifity") != null && !map.get("userIdentifity").equals("")) {
                wrapper.like("userIdentifity", (String) map.get("userIdentifity"));
                iswherenull = true;
            }
        }
        mesuserwrapper.orderAsc(Collections.singleton("userId"));
        List<Org_user> orgUsers = this.selectList(wrapper);
        String[] userids = new String[orgUsers.size()];
        if (iswherenull){
            for (int i = 0; i < orgUsers.size(); i++){
                userids[i] = orgUsers.get(i).getId()+"";
            }
            mesuserwrapper.in("userId",userids);
        }
        if (orgUsers == null) return null;
        Map<String,Object> usermap  = new HashMap<>();
        for (Org_user user : orgUsers){
            usermap.put(user.getId()+"",user);
        }
        Page<Org_mes_user> orgMesUserPage = mesUserService.selectPage(page, mesuserwrapper);
        List<UserBo> userBos = new ArrayList<>();
        for (Org_mes_user mesUser : orgMesUserPage.getRecords()) {
            UserBo user = new UserBo((Org_user) usermap.get(mesUser.getUserId()+""));
            user.setJobNum(mesUser.getJobNum());
            user.setMesid(mesUser.getMesId());
            userBos.add(user);
        }
        rqmap.put("total",orgMesUserPage.getTotal());
        rqmap.put("rows",userBos);
        return rqmap;
    }

    @Override
    public Page<Org_user> getList(Integer myRows, Integer myPage, String where_sql) {
        Page<Org_user> page = new Page<>(myPage,myRows);
        EntityWrapper<Org_user> wrapper = new EntityWrapper<>();
        if (where_sql != null){
            JSONObject  jasonObject = JSONObject.parseObject(where_sql);
            Map<String,Object> map =  jasonObject;
            if (map.get("userName") != null && !map.get("userName").equals("")) {
                wrapper.like("userName", (String) map.get("userName"));
            }
            if (map.get("userTrueName") != null && !map.get("userTrueName").equals("")) {
                wrapper.like("userTrueName", (String) map.get("userTrueName"));
            }
            if (map.get("userIdentifity") != null && !map.get("userIdentifity").equals("")) {
                wrapper.like("userIdentifity", (String) map.get("userIdentifity"));
            }
        }
        wrapper.orderAsc(Collections.singleton("id"));
        Page<Org_user> orgUserPage = this.selectPage(page, wrapper);
        return orgUserPage;
    }

    @Override
    public void deleteByIds(String ids) {
        String[] idsArray = ids.split(",");
        for(String id:idsArray){
            //同时删除从表记录
            this.delete(new EntityWrapper<Org_user>().eq("id",Integer.parseInt(id)));
            this.mesUserService.delete(new EntityWrapper<Org_mes_user>().eq("userId",id));
        }
    }
}
