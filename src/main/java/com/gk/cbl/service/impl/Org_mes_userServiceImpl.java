package com.gk.cbl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.Bo.UserBo;
import com.gk.cbl.entity.Org_mes;
import com.gk.cbl.entity.Org_mes_user;
import com.gk.cbl.entity.Org_send_rece;
import com.gk.cbl.entity.Org_user;
import com.gk.cbl.mapper.Org_mesMapper;
import com.gk.cbl.mapper.Org_mes_userMapper;
import com.gk.cbl.mapper.Org_userExMapper;
import com.gk.cbl.mapper.Org_userMapper;
import com.gk.cbl.service.Org_mesService;
import com.gk.cbl.service.Org_mes_userService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gk.cbl.service.Org_send_receService;
import com.gk.cbl.service.Org_userService;
import com.gk.cbl.util.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 社团与用户关系表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Service
public class Org_mes_userServiceImpl extends ServiceImpl<Org_mes_userMapper, Org_mes_user> implements Org_mes_userService {

    @Autowired
    Org_userService userService;

    @Autowired
    Org_mesService mesService;

    @Autowired
    Org_send_receService sendReceService;

    @Override
    public List<UserBo> getMesUser(Integer mesId) {
        //获得负责人关联表信息
        List<Org_mes_user> org_mes_users = this.selectList(new EntityWrapper<Org_mes_user>().eq("mesId", mesId).in("jobNum",new Integer[]{1,2}));
        if (org_mes_users == null || org_mes_users.size()<1 ) return null;
        List<Integer> userids = new ArrayList<>();

        for (Org_mes_user mesuser: org_mes_users) {
            userids.add(mesuser.getUserId());
        }

        //获取负责人信息 new EntityWrapper<UserBo>().in("id", userids)
        List<Org_user> orgUsers = userService.selectList(new EntityWrapper<Org_user>().in("id", userids));
        Map<String,UserBo> mapuser = new HashMap<>();
        String job = null;

        for (Org_user org : orgUsers){
            UserBo userBo = new UserBo(org);
            mapuser.put(userBo.getId()+"",userBo);
        }

        for (Org_mes_user orgmes : org_mes_users) {
            if (mapuser.containsKey(orgmes.getUserId()+"")){
                if (orgmes.getJobNum() == 1) job = "社长";
                if (orgmes.getJobNum() == 2) job = "负责人";
                mapuser.get(orgmes.getUserId()+"").setMesid(orgmes.getMesId());
                mapuser.get(orgmes.getUserId()+"").setJobNum(orgmes.getJobNum());
                mapuser.get(orgmes.getUserId()+"").setJobNumStr(job);
            }
        }
        //获取map所有value，放进list
        Collection<UserBo> bo =  mapuser.values();
        List<UserBo> userBos = new ArrayList<UserBo>(bo);
        return userBos;
    }

    @Override
    public List<Org_mes> getMesByUserid(Integer userid) {
        if (userid == null) return null;
        List<Org_mes_user> mesUsers = this.selectList(new EntityWrapper<Org_mes_user>().eq("userId", userid));
        List<Org_mes> mesList = new ArrayList<>();
        for (Org_mes_user mesUser: mesUsers) {
            Org_mes org_mes = mesService.selectOne(new EntityWrapper<Org_mes>().eq("id", mesUser.getMesId()));
            mesList.add(org_mes);
        }
        return mesList;
    }

    @Override
    public List<Integer> getPageUserId(Integer mesid, Integer page, Integer size) {
        List<Integer> userIds = new ArrayList<>();
        Page<Org_mes_user> pagemes = new Page<>(page,size);
        Page<Org_mes_user> mesUserPage = this.selectPage(pagemes, new EntityWrapper<Org_mes_user>().eq("mesId", mesid));
        if (mesUserPage.getRecords() == null || mesUserPage.getRecords().size() <= 0) return null;
        for (Org_mes_user users: mesUserPage.getRecords()) {
            userIds.add(users.getUserId());
        }
        return userIds;
    }

    /*前台添加社员*/
    @Override
    public Map<String, Object> addMesUser(String username, Integer mesid,Integer jobNum) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message", "新增成功");
        //查询用户名是否存在
        Org_user user = this.userService.selectOne(new EntityWrapper<Org_user>().eq("userName", username));
        Integer userid = user.getId();
        if (user == null){
            resultMap.put("status","fail");
            resultMap.put("message", "用户不存在");
            return resultMap;
        }
        //查询该成员是否已经进入该社团
        int count1 = this.selectCount(new EntityWrapper<Org_mes_user>().eq("mesId", mesid).eq("userId",userid ));
        if (count1 == 1){
            resultMap.put("status","fail");
            resultMap.put("message", "社团已存在该成员！");
            return resultMap;
        }
        //添加社员
        Org_mes_user mesUser = new Org_mes_user();
        mesUser.setMesId(mesid);
        mesUser.setUserId(userid);
        mesUser.setJobNum(jobNum);
        this.insert(mesUser);
        //入社通知推送
        Org_send_rece sendRece = new Org_send_rece();
        sendRece.setSendId(mesid);
        sendRece.setReceId(userid);
        sendRece.setMesType(1);
        sendRece.setIsread(0);
        sendRece.setContent("您已成功加入社团" + this.mesService.selectOne(new EntityWrapper<Org_mes>().eq("id",mesid)).getMesName() + "，可以开启你的社团之旅啦！");
        sendRece.setMesTime(TimeUnit.getDate());
        try {
            this.sendReceService.insert(sendRece);
        } catch (Exception e) {
            resultMap.put("status", "fail");
            resultMap.put("message", "程序错误，新增失败!");
        }
        return resultMap;
    }

    //删除社员
    @Override
    public void deleteMesUser(Integer userid, Integer mesid) {
        Map<String,Object> queryMap = new HashMap<>();
        this.delete(new EntityWrapper<Org_mes_user>().eq("mesId",mesid).eq("userId",userid));
        //删除成功，推送消息
        Org_send_rece sendRece = new Org_send_rece();
        sendRece.setSendId(mesid);
        sendRece.setReceId(userid);
        sendRece.setMesType(1);
        sendRece.setIsread(0);
        sendRece.setContent("您已被移除出社团" + this.mesService.selectOne(new EntityWrapper<Org_mes>().eq("id",mesid)).getMesName());
        sendRece.setMesTime(TimeUnit.getDate());
        this.sendReceService.insert(sendRece);
    }
}
