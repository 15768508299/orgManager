package com.gk.cbl.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.Bo.sendReceBo;
import com.gk.cbl.entity.*;
import com.gk.cbl.service.*;
import com.gk.cbl.util.TimeUnit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * <p>
 * 轮播图表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Controller
@RequestMapping("/org_index_ol")
public class Org_index_olController {

    @Autowired
    Org_index_olService indexOlService;

    @Autowired
    Org_activityService activityService;

    @Autowired
    Org_newsService newsService;

    @Autowired
    Org_mesService mesService;

    @Autowired
    Org_userService userService;

    @Autowired
    Org_mes_userService mesUserServices;

    @Autowired
    Org_send_receService sendReceService;

    @RequestMapping(value = {"/","index"})
    public String index(ModelMap map){
        //首页轮播图
        List<Org_index_ol> indexOls = indexOlService.selectList(null);
        map.put("indexOL",indexOls);

        //首页热点新闻 newsList
        List<Org_news> orgNews = newsService.selectList(new EntityWrapper<Org_news>().orderDesc(Collections.singleton("readNum")));
        map.put("newsList",orgNews);

        //首页热门活动 activityList
        List<Org_activity> readNum = activityService.selectList(new EntityWrapper<Org_activity>().orderDesc(Collections.singleton("readNum")));
        map.put("activityList",readNum);

        //首页热门社团 mesWithBLOBsList
        List<Org_mes> orgMesList = mesService.selectList(new EntityWrapper<Org_mes>().orderDesc(Collections.singleton("mesNum")));
        map.put("mesWithBLOBsList",orgMesList);



        return "index";

    }

    /**
     * 前台登录页面跳转
     * @return
     */
    @RequestMapping("login")
    public String login(){
        return "login";
    }

    /**
     * 前台登录处理
     * @param username
     * @param password
     * @param response
     * @return
     */
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkLogin(String username, String password, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "fail");
        resultMap.put("message", "登录名或密码错误!");
        resultMap.put("userMes", null);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
            //System.out.println("--------------------->>" +subject.hasRole("社团管理员"));
            //判断是否为社团管理员，然后截取登录名后的社团id，进行返回
           if(subject.hasRole("社团管理员") && username.length() == 7){
                String strMesId = username.substring(5);
                int mesid = Integer.parseInt(strMesId);
                resultMap.put("message", "社团管理员登录成功");
                resultMap.put("mesid",mesid);
            }else {
                resultMap.put("message", "登录成功");
            }

            resultMap.put("status", "success");

            //使用shiro session保存用户信息
            Session session =  subject.getSession();
            session.setAttribute("username",username);

            //cookie也记录
            Cookie cookie_username = new Cookie("username",username);
            cookie_username.setMaxAge(24*10*3600);
            response.addCookie(cookie_username);
            Cookie cookie_password = new Cookie("password",password);
            cookie_password.setMaxAge(24*10*3600);
            response.addCookie(cookie_password);
        }catch (Exception e){

        }
        return resultMap;
    }

    //前往注册页面
    @RequestMapping(value = "/goRegister", method = RequestMethod.GET)
    public String goRegister() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> register(Org_user user) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message","注册成功，可以进行登录了!");
        try {
            int count = this.userService.selectCount(new EntityWrapper<Org_user>().eq("userName", user.getUserName()));
            if(count == 0){
                this.userService.insert(user);
            }else{
                resultMap.put("status","fail");
                resultMap.put("message","已存在该用户名，请更换用户名进行注册!");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status","fail");
            resultMap.put("message","注册失败，未知错误!");
        }
        return resultMap;
    }

    /**
     * 前台注销
     * @param map
     * @return
     */
    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public String exit(ModelMap map) {
        SecurityUtils.getSubject().logout();
        //首页轮播图
        List<Org_index_ol> indexOls = indexOlService.selectList(null);
        map.put("indexOL",indexOls);

        //首页热点新闻 newsList
        List<Org_news> orgNews = newsService.selectList(new EntityWrapper<Org_news>().orderDesc(Collections.singleton("readNum")));
        map.put("newsList",orgNews);

        //首页热门活动 activityList
        List<Org_activity> readNum = activityService.selectList(new EntityWrapper<Org_activity>().orderDesc(Collections.singleton("readNum")));
        map.put("activityList",readNum);

        //首页热门社团 mesWithBLOBsList
        List<Org_mes> orgMesList = mesService.selectList(new EntityWrapper<Org_mes>().orderDesc(Collections.singleton("mesNum")));
        map.put("mesWithBLOBsList",orgMesList);

        return "index";
    }

    /**
     * 前台个人信息和个人社团显示
     * @param userid
     * @param model
     * @param isRead
     * @return
     */
    @RequestMapping(value = "/goUserMes",method = RequestMethod.GET)
    public String goUserMes(Integer userid, Model model, Integer isRead,Integer page,Integer size){
        Org_user orgUser = userService.selectOne(new EntityWrapper<Org_user>().eq("id", userid));
        model.addAttribute("user",orgUser);

        List<Org_mes> mesList = mesUserServices.getMesByUserid(userid);
        if (mesList != null && mesList.size() > 0){
            model.addAttribute("mesWithBLOBs", mesList);
        }

        Integer myPage1 = (page==null)?1:page;
        Integer mySize1 = (size==null)?10:size;

        Map<String, Object> sendPageMap = sendReceService.getUserSends(myPage1, mySize1, userid, isRead);
        int count = sendReceService.selectCount(new EntityWrapper<Org_send_rece>().eq("receId", userid).eq("isread", 0));
        if (count > 0){
            model.addAttribute("count",count);
        }
        model.addAttribute("sendRecesExes",sendPageMap.get("rows"));
        model.addAttribute("sendRecePage",sendPageMap.get("page")); //当前页
        model.addAttribute("number",sendPageMap.get("number")); //总页数
        model.addAttribute("active",isRead);
        return "home/orgUserMes";
    }

    /**
     * 前台修改个人信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/h_editUser",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> h_editUser(Org_user user) {
        Map<String,Object> resultMap = new HashMap<>();
        this.userService.updateById(user);
        Org_user user1 = (Org_user) SecurityUtils.getSubject().getPrincipal();
        user1.setUserTrueName(user.getUserTrueName());
        resultMap.put("status","success");
        resultMap.put("message", "编辑成功");
        return resultMap;
    }

    @RequestMapping(value = "/goManMes",method = RequestMethod.GET)
    public String goManMes(Integer mesid,Integer page1,Integer size1,String orderCol1,String orderRule1,Integer page2,Integer size2,String orderCol2,String orderRule2,ModelMap modelMap,Integer handleArea){
        modelMap.put("handleArea",handleArea);
        //查询该社团信息
        Org_mes orgMes = mesService.selectOne(new EntityWrapper<Org_mes>().eq("id", mesid));
        if (orgMes != null){
            modelMap.addAttribute("mesWithBLOB", orgMes);

            //查询该社团下的新闻
            Integer myPage1 = (page1==null)?1:page1;
            Integer mySize1 = (size1==null)?10:size1;
            Map query = new HashMap();
            query.put("mesid",orgMes.getId());
            Page<Org_news> newsPage = this.newsService.selectPageNews(myPage1, mySize1, query);
            modelMap.put("newsList",newsPage.getRecords());
            int newsber = (int) (newsPage.getTotal() % newsPage.getSize() == 0? newsPage.getTotal() / newsPage.getSize() : (newsPage.getTotal() / newsPage.getSize()+1));
            modelMap.put("newsList_AllPage", newsber);
            modelMap.put("newsList_nowPage",newsPage.getCurrent());	//当前页

            //查询该社团下的活动
            Integer myPage2 = (page2==null)?1:page2;
            Integer mySize2 = (size2==null)?10:size2;
            Map queryact = new HashMap();
            queryact.put("issueBy",mesid);
            Page<Org_activity> activityPage = this.activityService.selectPageActi(myPage2, mySize2, queryact);
            modelMap.put("actList",activityPage.getRecords());
            int actber = (int) (activityPage.getTotal() % activityPage.getSize() == 0? activityPage.getTotal() / activityPage.getSize() : (activityPage.getTotal() / activityPage.getSize()+1));
            modelMap.put("actList_AllPage", actber);
            modelMap.put("actList_nowPage",activityPage.getCurrent()); //当前页
        }

        return "home/orgManMes";
    }

    //管理者编辑社团信息
    @RequestMapping(value = "/editMes", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editMes(String mescreatetimeStr,Org_mes mesWithBLOBs, HttpServletRequest request) throws IllegalStateException, IOException, ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        Date myDate = TimeUnit.StringToDate(mescreatetimeStr);
        mesWithBLOBs.setMesCreateTime(myDate);
        this.mesService.updateById(mesWithBLOBs);
        resultMap.put("status","success");
        resultMap.put("message", "编辑成功");
        return resultMap;
    }


    /*
    ------------------------------------------------------------------------------------------------------
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ---------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< |   后台模块   | >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ---------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    ------------------------------------------------------------------------------------------------------
     */

    /**
     * 后台：显示轮播页面管理
     * @param modelMap
     * @return
     */
    @RequestMapping(value="adminGetList",method = RequestMethod.GET)
    public String getList(ModelMap modelMap){
        return "admin/indexOL/list";
    }

    /**
     * 列表显示轮播
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @param where_sql
     * @return
     */
    @RequestMapping(value = "adminGetList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> adminGetList(Integer page, Integer rows, String sort, String order, String where_sql){
        Integer myRows = (rows==null) ? 10 : rows;	//获取分页数据
        Integer myPage = (page==null) ? 1 : page;
        Page<Org_index_ol> indexOlPage = this.indexOlService.indexOlPage(myPage,myRows, where_sql);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows",indexOlPage.getRecords());
        map.put("total",indexOlPage.getTotal());
        return map;
    }

    //前往增加轮播信息页面
    @RequestMapping(value="add",method = RequestMethod.GET)
    public ModelAndView addDataShow(ModelMap modelMap){
        return new ModelAndView("admin/indexOL/addMes");
    }

    //增加轮播信息
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addData(Org_index_ol indexOL) throws IllegalStateException{
        Map<String,Object> resultMap = new HashMap<>();
        this.indexOlService.insert(indexOL);
        resultMap.put("status","success");
        resultMap.put("message", "新增成功");
        return resultMap;
    }

    //前往编辑轮播信息页面
    @RequestMapping(value="edit",method = RequestMethod.GET)
    public String editDataShow(ModelMap modelMap,Integer id){
        Org_index_ol indexOL =  this.indexOlService.selectOne(new EntityWrapper<Org_index_ol>().eq("id",id));
        String data = JSON.toJSONString(indexOL);
        modelMap.put("data",data);
        modelMap.addAttribute("id",indexOL.getId());
        return "admin/indexOL/addMes";
    }

    //编辑轮播信息
    @RequestMapping(value="edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editData(Org_index_ol indexOL) throws IllegalStateException, IOException, ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        this.indexOlService.updateById(indexOL);
        resultMap.put("status","success");
        resultMap.put("message", "编辑成功");
        return resultMap;
    }

    //删除轮播信息
    @RequestMapping(value="delete",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteData(ModelMap modelMap,HttpServletRequest request,String ids) throws IllegalStateException, IOException{
        Map<String,Object> resultMap = new HashMap<>();
        this.indexOlService.delete(new EntityWrapper<Org_index_ol>().eq("id",ids));
        resultMap.put("status","success");
        resultMap.put("message", "删除成功");
        return resultMap;
    }
}

