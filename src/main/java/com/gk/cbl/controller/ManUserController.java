package com.gk.cbl.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gk.cbl.Bo.ActivityBo;
import com.gk.cbl.entity.Org_activity;
import com.gk.cbl.entity.Org_news;
import com.gk.cbl.service.Org_activityService;
import com.gk.cbl.service.Org_mesService;
import com.gk.cbl.service.Org_mes_userService;
import com.gk.cbl.service.Org_newsService;
import com.gk.cbl.util.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.plaf.synth.SynthScrollBarUI;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/manUser")
public class ManUserController {

    @Autowired
    Org_mes_userService mesUserService;

    @Autowired
    Org_newsService newsService;

    @Autowired
    Org_activityService activityService;

    //管理者前往新增界面
    @RequestMapping(value = "/newUserShow", method = RequestMethod.GET)
    public ModelAndView inviteNewUserShow(String mesid, Model model){
        model.addAttribute("mesid",mesid);
        return new ModelAndView("home/modelView/NewUser");
    }

    //管理者新增成员
    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newUser(String username, Integer mesid) throws IllegalStateException{
        return  mesUserService.addMesUser(username,mesid,4);
    }

    //管理者前往发布新闻页面
    @RequestMapping(value = "/addNewsShow", method = RequestMethod.GET)
    public ModelAndView addNesShow(Integer mesid, Model model){
        Org_news news = new Org_news();
        news.setIssueBy(mesid);
        model.addAttribute("news",news);
        return new ModelAndView("home/modelView/ManAddNew");
    }

    @RequestMapping(value = "/addNews",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addNews(ModelMap map,Org_news news){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message", "发布成功");
        try {
            news.setIssueTime(TimeUnit.getDate());
            newsService.insert(news);
        }catch (Exception e){
            resultMap.put("status","success");
            resultMap.put("message", "发布失败,未知错误");
        }
        return resultMap;
    }

    //管理者前往编辑新闻页面
    @RequestMapping(value = "/editNewsShow", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView editNewsShow(Integer newid,Model model){
        Org_news news = this.newsService.selectOne(new EntityWrapper<Org_news>().eq("id",newid));
        model.addAttribute("news",news);
        model.addAttribute("edit",1);
        return new ModelAndView("home/modelView/ManAddNew");
    }

    //管理者编辑新闻
    @RequestMapping(value = "/editNews", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editNews(Org_news news,Model model){
        Map resultMap = new HashMap();
        resultMap.put("status","success");
        resultMap.put("message","编辑成功");
        try {
            news.setIssueTime(TimeUnit.getDate());
            this.newsService.updateById(news);
        }catch (Exception e){
            resultMap.put("status","fail");
            resultMap.put("message","编辑失败,未知错误");
        }
        return resultMap;
    }

    //管理者删除新闻
    @RequestMapping(value = "/deleteNews", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteNews(Integer newsid,Model model){
        Map resultMap = new HashMap();
        resultMap.put("status","success");
        resultMap.put("message","删除成功");
        try {
            this.newsService.delete(new EntityWrapper<Org_news>().eq("id",newsid));
        }catch (Exception e){
            resultMap.put("status","fail");
            resultMap.put("message","删除失败,未知错误");
        }
        return resultMap;
    }

    //管理者前往发布活动页面
    @RequestMapping(value = "/addActShow", method = RequestMethod.GET)
    public ModelAndView addActShow(Integer mesid, Model model){
        Org_activity activity = new Org_activity();
        activity.setIssueBy(mesid);
        model.addAttribute("activity",activity);
        return new ModelAndView("home/modelView/ManAddAct");
    }

    //管理者发布活动
    @RequestMapping(value = "/addAct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addAct(ModelMap modelMap,ActivityBo activityEx) throws ParseException {
        return this.activityService.addAct(activityEx);
    }

    //管理者前往编辑活动页面
    @RequestMapping(value = "/editActShow", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView editActShow(Integer actid,Model model){
        ActivityBo activityBo = this.activityService.getActivityBoById(actid);
        model.addAttribute("activity",activityBo);
        model.addAttribute("edit",1);
        return new ModelAndView("home/modelView/ManAddAct");
    }

    //管理者对活动进行更新
    @RequestMapping(value = "/editAct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editAct(ActivityBo activityEx,Model model){
        return this.activityService.updateAct(activityEx);
    }

    //管理者删除活动
    @RequestMapping(value = "/deleteAct", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteAct(Integer actid,Model model){
        Map resultMap = new HashMap();
        resultMap.put("status","success");
        resultMap.put("message","删除成功");
        try {
            this.activityService.delete(new EntityWrapper<Org_activity>().eq("id",actid));
        }catch (Exception e){
            resultMap.put("status","fail");
            resultMap.put("message","删除失败,未知错误");
        }
        return resultMap;
    }


}
