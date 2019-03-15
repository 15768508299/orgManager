package com.gk.cbl.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.Bo.NewsBo;
import com.gk.cbl.entity.Org_new_ol;
import com.gk.cbl.entity.Org_news;
import com.gk.cbl.service.Org_act_olService;
import com.gk.cbl.service.Org_new_olService;
import com.gk.cbl.service.Org_newsService;
import com.gk.cbl.util.TimeUnit;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社团新闻表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Controller
@RequestMapping("/org_news")
public class Org_newsController {
    
    @Autowired
    Org_newsService newsService;

    @Autowired
    Org_new_olService newOlService;

    /**
     * 前台 ： 社团新闻列表显示
     * @param map
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "newsHomeList",method = RequestMethod.GET)
    public String newsList(ModelMap map, Integer page, Integer size){
        Integer myPage = (page == null) ? 1 : page;
        Integer mySize = (size == null) ? 4 :size;
        Map quyer = new HashMap();
        Page<Org_news> orgNewsPage = newsService.selectPageNews(myPage, mySize,quyer);

        //总页数
        int number = (int) (orgNewsPage.getTotal() % orgNewsPage.getSize() == 0? orgNewsPage.getTotal() / orgNewsPage.getSize() : (orgNewsPage.getTotal() / orgNewsPage.getSize()+1));

        //查询页面轮播图片
        List<Org_new_ol> actOLS1 = newOlService.selectList(new EntityWrapper<Org_new_ol>().eq("olLevel", 1));
        List<Org_new_ol> actOLS2 = newOlService.selectList(new EntityWrapper<Org_new_ol>().eq("olLevel", 2));

        map.put("newOLS",actOLS1);
        map.put("newOLS2",actOLS2);

        map.put("actList_AllPage", number);                     //总页数
        map.put("newsList",orgNewsPage.getRecords());           //分页数据
        map.put("actList_nowPage",orgNewsPage.getCurrent());	//当前页
        return "home/orgNew";
    }


    @RequestMapping(value = "newsListDetail",method = RequestMethod.GET)
    public String detail(Integer newsid,ModelMap modelMap){
        if (newsid == null && newsid == 0) return "数据出错，请重新提交！";
        Org_news orgNews = newsService.selectOne(new EntityWrapper<Org_news>().eq("id", newsid));
        if (orgNews.getReadNum() == null) orgNews.setReadNum(1);
        else orgNews.setReadNum(orgNews.getReadNum()+1);
        newsService.update(orgNews,new EntityWrapper<Org_news>().eq("id",newsid));
        modelMap.put("news",orgNews);
        return "home/orgNewDetail";
    }



        /*
    ------------------------------------------------------------------------------------------------------
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ---------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< |   后台模块   | >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ---------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    ------------------------------------------------------------------------------------------------------
     */

    /*获取所有活动，显示*/
    @RequestMapping(value="getAllNews",method = RequestMethod.POST)
    @ResponseBody
    public List<Org_news> getAllNews() {
        return this.newsService.selectList(new EntityWrapper<Org_news>().orderDesc(Collections.singleton("issueTime")));
    }

    @RequestMapping(value="adminGetList",method = RequestMethod.GET)
    public String getList(ModelMap modelMap){
        return "admin/news/list";
    }

    //获取所有新闻列表
    @RequestMapping(value="adminGetList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(Integer page,Integer rows,String sort,String order,String where_sql){
        Integer myPage = (page==null) ? 1 : page;	//获取分页数据
        Integer myRows = (rows==null) ? 10 : rows;
        Map<String,Object> rqmap = null;
        if (where_sql != null){
            JSONObject jasonObject = JSONObject.parseObject(where_sql);
            rqmap =  jasonObject;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Page<Org_news> orgNewsPage = this.newsService.selectPageNews(myPage, myRows, rqmap);
        map.put("rows",orgNewsPage.getRecords());
        map.put("total", orgNewsPage.getTotal());
        return map;
    }

    //前往增加新闻信息页面
    @RequestMapping(value="add",method = RequestMethod.GET)
    public String addDataShow(ModelMap modelMap){
        return "admin/news/addMes";
    }

    //新增社团信息
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addData(NewsBo news) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message", "新增成功");
        try {
            news.setIssueTime(TimeUnit.StringToDate(news.getIssueTimeStr()+" 00:00:00"));
            this.newsService.insert(news);
        }catch (Exception e){
            resultMap.put("status","fail");
            resultMap.put("message", "未知错误,新增失败");
        }
        return resultMap;
    }

    //前往编辑新闻信息页面
    @RequestMapping(value="edit",method = RequestMethod.GET)
    public String editDataShow(ModelMap modelMap,Integer id){
        Org_news news = this.newsService.selectOne(new EntityWrapper<Org_news>().eq("id",id));
        if (news.getNewDetail() == null){
            news.setNewDetail("No details");
        }
        String data = JSON.toJSONString(news);
        modelMap.addAttribute("id",news.getId());
        modelMap.addAttribute("data",data);
        return "admin/news/addMes";
    }

    //编辑活动信息
    @RequestMapping(value="edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editData(NewsBo newEx) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message", "编辑成功");
        try {
            newEx.setIssueTime(TimeUnit.StringToDate(newEx.getIssueTimeStr()));
            this.newsService.updateById(newEx);
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status","fail");
            resultMap.put("message", "未知错误,编辑失败");
        }
        return resultMap;
    }

    /*删除活动信息*/
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteData(ModelMap modelMap, HttpServletRequest request, String ids) throws IllegalStateException, IOException {
        Map<String, Object> resultMap = new HashMap<>();
        this.newsService.deleteByIds(ids);
        resultMap.put("status", "success");
        resultMap.put("message", "删除成功");
        return resultMap;
    }

    /*获取轮播列表*/
    @RequestMapping(value="newOlList",method = RequestMethod.GET)
    public ModelAndView newOlList(ModelMap modelMap){
        return new ModelAndView("admin/news/new_ol");
    }

    /*获取轮播列表*/
    @RequestMapping(value = "/newOlList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newOlList(Integer page, Integer rows, String sort, String order, String where_sql) {
        Integer myPage = (page==null) ? 1 : page;	//获取分页数据
        Integer myRows = (rows==null) ? 10 : rows;
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> rqmap = new HashMap<String, Object>();
        if (where_sql != null && !"".equals(where_sql)){
            rqmap.put("description",where_sql);
        }
        Page<Org_new_ol> orgNewOlPage = this.newOlService.selectPageNews(myPage, myRows, rqmap);
        map.put("rows",orgNewOlPage.getRecords());
        map.put("total", orgNewOlPage.getTotal());
        return map;
    }

    //前往增加轮播信息页面
    @RequestMapping(value="newsOlAdd",method = RequestMethod.GET)
    public String newsOlAdd(ModelMap modelMap){
        return "admin/news/new_olAdd";
    }

    //增加轮播信息
    @RequestMapping(value="newsOlAdd",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newsOlAdd(Org_new_ol newOL) throws IllegalStateException, IOException, ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message", "新增成功");
        try {
            this.newOlService.insert(newOL);
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status","fail");
            resultMap.put("message", "未知错误,编辑失败");
        }
        return resultMap;
    }

    //前往编辑轮播信息页面
    @RequestMapping(value="newsOlAddEdit",method = RequestMethod.GET)
    public String newsOlAddEdit(ModelMap modelMap,Integer id){
        Org_new_ol newOL =  this.newOlService.selectOne(new EntityWrapper<Org_new_ol>().eq("id",id));
        String data = JSON.toJSONString(newOL);
        modelMap.put("data",data);
        modelMap.addAttribute("id",newOL.getId());
        return "admin/news/new_olAdd";
    }

    //编辑轮播信息
    @RequestMapping(value="newsOlAddEdit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newsOlAddEdit(Org_new_ol newOL) throws IllegalStateException, IOException, ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        this.newOlService.updateById(newOL);
        resultMap.put("status","success");
        resultMap.put("message", "编辑成功");
        return resultMap;
    }

    //删除轮播信息
    @RequestMapping(value="deleteOL",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteOL(ModelMap modelMap,HttpServletRequest request,String ids) throws IllegalStateException, IOException{
        Map<String,Object> resultMap = new HashMap<>();
        this.newOlService.deleteOLByIds(ids);
        resultMap.put("status","success");
        resultMap.put("message", "删除成功");
        return resultMap;
    }
}

