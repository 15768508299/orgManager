package com.gk.cbl.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gk.cbl.Bo.sendReceBo;
import com.gk.cbl.entity.Org_send_rece;
import com.gk.cbl.entity.Org_user;
import com.gk.cbl.service.Org_send_receService;
import com.gk.cbl.service.Org_userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息传递表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Controller
@RequestMapping("/org_send_rece")
public class Org_send_receController {

    @Autowired
    Org_send_receService sendReceService;

    @Autowired
    Org_userService userService;


    //前往消息详情页面
    @RequestMapping(value = "/goInformDetail",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView goInformDetail(Integer id, Model model){
        sendReceBo sendReceEx = this.sendReceService.findById(id);
        sendReceEx.setIsread(1);    //标记为已读
        this.sendReceService.updateById(sendReceEx);
        model.addAttribute("sendReceEx",sendReceEx);
        return new ModelAndView("home/detail/orgUserMes_mesDe");
    }

    //用户删除某条消息通知
    @RequestMapping(value = "/deleteInform",method = RequestMethod.GET)
    public String deleteInform(String ids,Integer userid, Model model,Integer isRead){
        this.sendReceService.deleteByIds(ids);
        model.addAttribute("active",2);
        //查询用户消息情况
       /* Map<String, Object> userSends = this.sendReceService.getUserSends(1, 10, userid, isRead);
        model.addAttribute("sendRecePage",userSends.get("page")); //当前页
        model.addAttribute("number",userSends.get("number")); //总页数
        model.addAttribute("user",this.userService.selectOne(new EntityWrapper<Org_user>().eq("id",userid)));
        model.addAttribute("sendRecesExes",userSends.get("rows"));*/
        return "redirect:/org_index_ol/goUserMes?userid="+userid;
    }

    //用户删除所有通知
    @RequestMapping(value = "/deleteAllInform",method = RequestMethod.GET)
    public String deleteAllInform(Integer userid, Model model,Integer isRead){
        this.sendReceService.delete(new EntityWrapper<Org_send_rece>().eq("receId",userid));
        model.addAttribute("active",2);
        //查询用户消息情况
       /* Map<String, Object> userSends = this.sendReceService.getUserSends(1, 10, userid, isRead);
        model.addAttribute("sendRecePage",userSends.get("page")); //当前页
        model.addAttribute("number",userSends.get("number")); //总页数
        model.addAttribute("user",this.userService.selectOne(new EntityWrapper<Org_user>().eq("id",userid)));
        model.addAttribute("sendRecesExes",userSends.get("rows"));
        return "home/orgUserMes";*/
        return "redirect:/org_index_ol/goUserMes?userid="+userid;
    }

    @RequestMapping("/applymes")
    @ResponseBody
    public Map<String, Object> applymes(Integer userid,Integer mesid,ModelMap map){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", "申请失败！请刷新重试。");
        if(userid == null || map == null) return resultMap;
        if(userid == 0){
            resultMap.put("message", "请登录重试。");}
        else {
            String applymes = sendReceService.applymes(userid, mesid);
            resultMap.put("message", applymes);
        }
        return resultMap;
    }

}

