package com.gk.cbl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.Bo.UserBo;
import com.gk.cbl.entity.Org_mes;
import com.gk.cbl.entity.Org_mes_user;
import com.gk.cbl.entity.Org_user;
import com.gk.cbl.mapper.Org_mesMapper;
import com.gk.cbl.service.Org_mesService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gk.cbl.service.Org_mes_userService;
import com.gk.cbl.service.Org_userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 社团信息表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Service
public class Org_mesServiceImpl extends ServiceImpl<Org_mesMapper, Org_mes> implements Org_mesService {

    @Autowired
    Org_mes_userService mesUserService;

    @Autowired
    Org_userService userService;

    @Override
    public Page<Org_mes> selectPageExt(Integer mypage, Integer size,String where) {

        Page<Org_mes> page = new Page<>(mypage,size);
        EntityWrapper<Org_mes> wrapper = new EntityWrapper<>();
        if (where != null) {
            wrapper.like("mesName",where);
        }
        Page<Org_mes> orgMesPage = this.selectPage(page,wrapper);
        return orgMesPage;
    }

    @Override
    public Map<String, Object> getMesUsers(List<Integer> userIds) {
        if (userIds == null || userIds.size() <= 0) return null;
        List<Org_user> users = userService.selectList(new EntityWrapper<Org_user>().in("id", userIds));
        Map resultMap = new HashMap();
        resultMap.put("rows",users);
        return resultMap;
    }

    @Override
    public void insertMess(Org_mes mesWithBLOBs, String exId) {
        this.insert(mesWithBLOBs);
        int mesid = mesWithBLOBs.getId();
        String userIds[] = exId.split(",");
        for (int i = 0; i < userIds.length; i++){
            Org_mes_user orgMesUser = new Org_mes_user();
            orgMesUser.setMesId(mesid);
            orgMesUser.setUserId(Integer.parseInt(userIds[i]));
            if (i == 0)  orgMesUser.setJobNum(1);
            else if (i == 1) orgMesUser.setJobNum(2);
            else orgMesUser.setJobNum(3);
            this.mesUserService.insert(orgMesUser);

        }

    }

    @Override
    public List<UserBo> getUnion(Integer id) {
        List<Org_mes_user> mesUsers = this.mesUserService.selectList(new EntityWrapper<Org_mes_user>().eq("mesId", id));
        if (mesUsers == null){
            return null;
        }
        List<UserBo> users = new ArrayList<>();
        for (Org_mes_user mesUser : mesUsers){
            Org_user userEx = userService.selectOne(new EntityWrapper<Org_user>().eq("id",mesUser.getUserId()));
            UserBo user = new UserBo(userEx);
            if(mesUser.getJobNum() == 1) {
                user.setJobNumStr("社长");
            }else if(mesUser.getJobNum() == 2){
                user.setJobNumStr("负责人");
            }
            users.add(user);
        }
        return users;
    }

    @Override
    public void updateMes(Org_mes mesWithBLOBs, String exId) {
        this.updateById(mesWithBLOBs);
        int mesId = mesWithBLOBs.getId();
        String userIds[] = exId.split(",");
        String[] jobNums = {"1","2"};
        mesUserService.delete(new EntityWrapper<Org_mes_user>().eq("mesId",mesId).in("jobNum",jobNums));
        for (int i = 0; i < userIds.length; i++){
            Org_mes_user orgMesUser = new Org_mes_user();
            orgMesUser.setMesId(mesId);
            orgMesUser.setUserId(Integer.parseInt(userIds[i]));
            if (i == 0)  orgMesUser.setJobNum(1);
            else if (i == 1) orgMesUser.setJobNum(2);
            else orgMesUser.setJobNum(3);
            this.mesUserService.insert(orgMesUser);
        }
    }

    @Override
    public void deleteByIds(String ids) {
        String[] split = ids.split(",");
        for (String id: split) {
            this.delete(new EntityWrapper<Org_mes>().eq("id",id));
            mesUserService.delete(new EntityWrapper<Org_mes_user>().eq("mesId",id));
        }

    }

}
