package com.gk.cbl.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.Bo.ActivityBo;
import com.gk.cbl.entity.Org_act_ol;
import com.gk.cbl.entity.Org_activity;
import com.gk.cbl.service.Org_act_olService;
import com.gk.cbl.service.Org_activityService;
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
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Controller
@RequestMapping("/org_activity")
public class Org_activityController {

    @Autowired
    Org_activityService activityService;

    @Autowired
    Org_act_olService actOlService;

    /**
     * 前台活动列表显示
     * @param map
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("homeList")
    public String homelist(ModelMap map, Integer page, Integer size){
        Integer myPage = (page == null) ? 1 : page;
        Integer mySize = (size == null) ? 4 :size;
        Map query = new HashMap();
        Page<Org_activity> activityPage = activityService.selectPageActi(myPage, mySize,query);
        //总页数
        int number = (int) (activityPage.getTotal() % activityPage.getSize() == 0? activityPage.getTotal() / activityPage.getSize() : (activityPage.getTotal() / activityPage.getSize()+1));

        //查询页面轮播图片
        List<Org_act_ol> actOLS1 = actOlService.selectList(new EntityWrapper<Org_act_ol>().eq("olLevel", 1));
        List<Org_act_ol> actOLS2 = actOlService.selectList(new EntityWrapper<Org_act_ol>().eq("olLevel", 2));

        map.put("actOLs1",actOLS1);
        map.put("actOLs2",actOLS2);

        map.put("actList_AllPage", number);

        map.put("actList",activityPage.getRecords());

        map.put("actList_count",activityPage.getTotal());		//总记录数
        map.put("actList_nowPage",activityPage.getCurrent());	//当前页
        return "home/orgActivity";
    }


    /**
     * 显示活动详情
     * @param modelMap
     * @param actid
     * @return
     */
    @RequestMapping("homeList_detail")
    public String detail(ModelMap modelMap,Integer actid){
        if (actid == null && actid == 0) return "数据出错，请重新提交！";
        Org_activity orgActivity = activityService.selectOne(new EntityWrapper<Org_activity>().eq("id", actid));
        orgActivity.setReadNum(orgActivity.getReadNum()+1);
        activityService.update(orgActivity,new EntityWrapper<Org_activity>().eq("id",actid));
        modelMap.put("activity",orgActivity);
        return "home/orgActDetail";
    }


    /*
    ------------------------------------------------------------------------------------------------------
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ---------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< |   后台模块   | >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ---------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    ------------------------------------------------------------------------------------------------------
     */

    /**
     * 后台：联动显示所有活动
     * @return
     */
    @RequestMapping(value = "getAllActivity",method = RequestMethod.POST)
    @ResponseBody
    public List<Org_activity> getAll(){
        return this.activityService.selectList(new EntityWrapper<Org_activity>().orderAsc(Collections.singleton("id")));
    }

    //前往查看社团活动列表页面(后台)
    @RequestMapping(value="adminGetList",method = RequestMethod.GET)
    public String getList(ModelMap modelMap){
        return "admin/activity/list";
    }

    //获取所有社团列表
    @RequestMapping(value="adminGetList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(Integer page,Integer rows,String sort,String order,String where_sql){
        Integer myPage = (page==null) ? 1 : page;	//获取分页数据
        Integer mySize = (rows==null) ? 10 : rows;
        Page<Org_activity> orgMesPage = this.activityService.selectPageExt(myPage, mySize, where_sql);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows",orgMesPage.getRecords());
        map.put("total", orgMesPage.getTotal());
        return map;
    }

    //前往增加活动信息页面
    @RequestMapping(value="add",method = RequestMethod.GET)
    public ModelAndView addDataShow(ModelMap modelMap,Integer mesid){
        modelMap.addAttribute("mesid",mesid);
        return new ModelAndView("admin/activity/addMes");
    }

    //新增活动信息
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addData(ActivityBo activityEx) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message", "新增成功");
        try {
            this.activityService.addAct(activityEx);
        }catch (Exception e){
            resultMap.put("status","fail");
            resultMap.put("message", "未知错误,新增失败");
        }
        return resultMap;
    }


    //前往编辑活动信息页面
    @RequestMapping(value="edit",method = RequestMethod.GET)
    public String editDataShow(ModelMap modelMap,Integer id){
        ActivityBo activityEx = this.activityService.getActivityBoById(id);
        String data = JSON.toJSONString(activityEx);
        modelMap.addAttribute("id",activityEx.getId());
        modelMap.addAttribute("data",data);
        return "admin/activity/addMes";
    }

    //编辑活动信息
    @RequestMapping(value="edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editData(ActivityBo activityEx) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message", "编辑成功");
        try {
            activityEx.setIssueDate(this.activityService.selectOne(new EntityWrapper<Org_activity>().eq("id",activityEx.getId())).getIssueDate());
            this.activityService.updateAct(activityEx);
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
        this.activityService.deleteByIds(ids);
        resultMap.put("status", "success");
        resultMap.put("message", "删除成功");
        return resultMap;
    }


    /*获取轮播列表*/
    @RequestMapping(value="actOlList",method = RequestMethod.GET)
    public String newOlList(ModelMap modelMap){
        return "admin/activity/act_ol";
    }

    /*获取轮播列表*/
    @RequestMapping(value = "actOlList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> actOlList(Integer page, Integer rows, String where_sql) {
        Integer myPage = (page==null) ? 1 : page;	//获取分页数据
        Integer myRows = (rows==null) ? 10 : rows;
        Map<String, Object> map = new HashMap<String, Object>();
        Page<Org_act_ol> olList = this.activityService.getOlList(myPage, myRows, where_sql);
        map.put("rows",olList.getRecords());
        map.put("total", olList.getTotal());
        return map;
    }

    //前往增加轮播信息页面
    @RequestMapping(value="actOlAdd",method = RequestMethod.GET)
    public String actOlAdd(ModelMap modelMap){
        return "admin/activity/act_olAdd";
    }

    //增加轮播信息
    @RequestMapping(value="actOlAdd",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newsOlAdd(Org_act_ol actOL) throws IllegalStateException, IOException, ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message", "新增成功");
        try {
            this.actOlService.insert(actOL);
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status","fail");
            resultMap.put("message", "未知错误,编辑失败");
        }
        return resultMap;
    }

    //前往编辑轮播信息页面
    @RequestMapping(value="actOlAddEdit",method = RequestMethod.GET)
    public String newsOlAddEdit(ModelMap modelMap,Integer id){
        Org_act_ol activityOL =  this.actOlService.selectOne(new EntityWrapper<Org_act_ol>().eq("id",id));
        String data = JSON.toJSONString(activityOL);
        modelMap.put("data",data);
        modelMap.addAttribute("id",activityOL.getId());
        return "admin/activity/act_olAdd";
    }

    //编辑轮播信息
    @RequestMapping(value="actOlAddEdit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> actOlAddEdit(Org_act_ol actOL) throws IllegalStateException, IOException, ParseException {
        Map<String,Object> resultMap = new HashMap<>();
        this.actOlService.updateById(actOL);
        resultMap.put("status","success");
        resultMap.put("message", "编辑成功");
        return resultMap;
    }

    //删除轮播信息
    @RequestMapping(value="deleteOL",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteOL(ModelMap modelMap,HttpServletRequest request,String ids) throws IllegalStateException, IOException{
        Map<String,Object> resultMap = new HashMap<>();
        this.actOlService.deleteOLByIds(ids);
        resultMap.put("status","success");
        resultMap.put("message", "删除成功");
        return resultMap;
    }
}

