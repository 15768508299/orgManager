package com.gk.cbl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.Bo.sendReceBo;
import com.gk.cbl.entity.*;
import com.gk.cbl.mapper.Org_mesMapper;
import com.gk.cbl.mapper.Org_send_receMapper;
import com.gk.cbl.service.Org_mesService;
import com.gk.cbl.service.Org_mes_userService;
import com.gk.cbl.service.Org_send_receService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gk.cbl.service.Org_userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 消息传递表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Service
public class Org_send_receServiceImpl extends ServiceImpl<Org_send_receMapper, Org_send_rece> implements Org_send_receService {


    @Autowired
    Org_mesService mesService;

    @Autowired
    Org_userService userService;

    @Autowired
    Org_mes_userService mes_userService;

    @Override
    public Map<String,Object> getUserSends(Integer page,Integer size,Integer userid, Integer isRead,Integer isAdmin) {
        //isRead : 0 未读  1 已读 2 全部
        Page<Org_send_rece> pageSend = new Page<>(page,size);
        EntityWrapper<Org_send_rece> wrapper = new EntityWrapper<>();
        Map<String,Object> rqmap = new HashMap<>();
        if(isRead == null){
            isRead = 2;
        }
        if (isRead == 1 || isRead == 0){
            wrapper.eq("isread",isRead);
        }
        wrapper.eq("receId",userid);
        Page<Org_send_rece> orgSendRecePage = this.selectPage(pageSend, wrapper);
        rqmap.put("rows",this.userSend(orgSendRecePage.getRecords(),isAdmin));
        rqmap.put("page",orgSendRecePage.getCurrent());
        rqmap.put("number",orgSendRecePage.getPages());
        return rqmap;
    }

    @Override
    public List<sendReceBo> userSend(List<Org_send_rece> sendReces,Integer isAdmin) {
        List<sendReceBo> sendReceBos = new ArrayList<>();
        for (Org_send_rece sendRece : sendReces){
            sendReceBo sendReceBo = new sendReceBo(sendRece);
            sendReceBos.add(sendReceBo);
        }
        if (isAdmin == 1){
            List<Org_mes> mesList = this.mesService.selectList(new EntityWrapper<>());
            Map<String,String> mesMap = new HashMap<>();
            for (Org_mes mes: mesList) {
                mesMap.put(mes.getId()+"",mes.getMesName());
            }
            for (sendReceBo sendReceBo: sendReceBos){
                if (mesMap.containsKey(sendReceBo.getSendId()+"")){
                    sendReceBo.setSendName(mesMap.get(sendReceBo.getSendId()+""));
                    sendReceBo.setMesTypeTheme("社团通知");
                }
            }
        }else {
            List<Org_user> org_users = this.userService.selectList(null);
            Map<String,String> userMap = new HashMap<>();
            for (Org_user user : org_users){
                userMap.put(user.getId()+"",user.getUserName());
            }
            for (sendReceBo sendReceBo: sendReceBos){
                if (userMap.containsKey(sendReceBo.getSendId()+"")){
                    sendReceBo.setSendName(userMap.get(sendReceBo.getSendId()+""));
                    sendReceBo.setMesTypeTheme("申请信息");
                }
            }
        }



        return sendReceBos;
    }

    @Override
    public sendReceBo findById(Integer id) {
        Org_send_rece sendRece =this.selectOne(new EntityWrapper<Org_send_rece>().eq("id",id));
        sendReceBo sendReceEx = new sendReceBo(sendRece);
        Integer mesType =  sendReceEx.getMesType();
        if(mesType == 1){
            sendReceEx.setSendName(this.mesService.selectOne(new EntityWrapper<Org_mes>().eq("id",sendReceEx.getSendId())).getMesName());
            sendReceEx.setMesTypeTheme("社团通知");
        }
        if(mesType == 2){
            sendReceEx.setSendName(this.userService.selectOne(new EntityWrapper<Org_user>().eq("id",sendReceEx.getSendId())).getUserName());
            sendReceEx.setMesTypeTheme("申请信息");
        }
        return sendReceEx;
    }

    @Override
    public void deleteByIds(String ids) {
        String[] tempIds = ids.split(",");
        for(String s:tempIds){
            this.delete(new EntityWrapper<Org_send_rece>().eq("id",Integer.parseInt(s)));
        }
    }

    @Override
    public String applymes(Integer userid, Integer mesid) {
        Org_user user = userService.selectOne(new EntityWrapper<Org_user>().eq("id", userid));
        int i = mes_userService.selectCount(new EntityWrapper<Org_mes_user>().eq("userId", userid).eq("mesId", mesid));
        if (i > 0) return "你已经是该社团的一员了，不需要申请入社。";
        String word = user.getUserTrueName() + "同学想申请加入社团。该同学联系方式为：" + user.getUserPhone() + "邮箱为：" + user.getUserEmail() + "QQ/微信：" + user.getUserQQWei();
        Org_send_rece send_rece = new Org_send_rece();
        send_rece.setContent(word);
        send_rece.setSendId(userid);
        send_rece.setReceId(mesid);
        send_rece.setIsread(0);
        send_rece.setMesTime(new Date());
        send_rece.setMesType(1);
        this.insert(send_rece);
        return "申请成功";
    }
}
