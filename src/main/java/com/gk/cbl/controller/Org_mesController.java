package com.gk.cbl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.Bo.UserBo;
import com.gk.cbl.Bo.WordBo;
import com.gk.cbl.entity.Org_mes;
import com.gk.cbl.entity.Org_mes_user;
import com.gk.cbl.entity.Org_user;
import com.gk.cbl.service.Org_mesService;
import com.gk.cbl.service.Org_mes_userService;
import com.gk.cbl.service.Org_userService;
import com.gk.cbl.service.Org_wordService;
import com.gk.cbl.util.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社团信息表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Controller
@RequestMapping("/org_mes")
public class Org_mesController {

    @Autowired
    Org_mesService orgMesService;

    @Autowired
    Org_mes_userService orgMesUserService;

    @Autowired
    Org_wordService wordService;

    @Autowired
    Org_userService userService;

    /**
     * 分页显示社团信息
     * @page  表示当前页数   默认：1
     * @size  表示每页显示数目   默认：4
     * @param where_sql 按名称查询条件
     * */
    @RequestMapping(value = "homeGetList")
    public String getList(ModelMap map, Integer page, Integer size,String where_sql){
        Integer myPage = (page == null) ? 1 : page;
        Integer mySize = (size == null) ? 4 :size;
        Page<Org_mes> mesIPage = orgMesService.selectPageExt(myPage, mySize,where_sql);
        //总页数
        map.put("mesIPage",mesIPage);
        map.put("number",mesIPage.getPages()); //总页数，便于生成动态分页按钮
        map.put("where_sql",where_sql); //返显
        map.put("actList_nowPage",mesIPage.getCurrent());
        return "home/orgMes";
    }

    /**
     * 前台显示社团详细信息
     * @param mesId 社团id
     * */
    @RequestMapping("messageDetail")
    public String mesdetail(ModelMap map,Integer mesId){
        if (mesId == null && mesId == 0)return "网络错误，请刷新重试！";
        //获取社团详细信息
        Org_mes org_mes = orgMesService.selectOne(new EntityWrapper<Org_mes>().eq("id",mesId));
        //负责人信息
        List<UserBo> mesUser = orgMesUserService.getMesUser(mesId);
        //留言信息
        List<WordBo> wordBos = wordService.getmesWord(mesId);

        map.addAttribute("mesWithBLOBs",org_mes);
        map.addAttribute("users",mesUser);
        map.addAttribute("words",wordBos);

        return "home/orgMesDetail";
    }

    @RequestMapping(value = "getMes_user",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> Mesuser(Integer mesId,Integer page,Integer rows){
        Integer myPage = (page==null) ? 1 : page;
        Integer myRows = (rows==null) ? 10 : rows;	//获取分页数据

        List<Integer> userId = this.orgMesUserService.getPageUserId(mesId, page, rows);
        Map<String, Object> map = this.orgMesService.getMesUsers(userId);
        map.put("page",myPage);
        return map;
    }

    /**
     *  前台删除社员，并进行消息推送
     * @param userid
     * @param mesid
     * @return
     */
    @RequestMapping(value = "delete_user",method = RequestMethod.POST)
    @ResponseBody
    public Map deleteUser(Integer userid,Integer mesid){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message","删除成功!");
        try {
            orgMesUserService.deleteMesUser(userid,mesid);
        }catch (Exception e){
            resultMap.put("status","fail");
            resultMap.put("message","程序错误,删除失败");
        }
        return resultMap;
    }


    /*
        ------------------------------------------------------------------------------------------------------
        | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ---------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
        | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< |   后台模块   | >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
        | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ---------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
        ------------------------------------------------------------------------------------------------------
     */

    //前往查看社团列表页面(后台)
    @RequestMapping(value="adminGetList",method = RequestMethod.GET)
    public String getList(ModelMap modelMap){
        return "admin/mes/list";
    }

    //获取所有社团列表
    @RequestMapping(value="adminGetList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(Integer page,Integer rows,String sort,String order,String where_sql){
        Integer myPage = (page==null) ? 1 : page;	//获取分页数据
        Integer mySize = (rows==null) ? 10 : rows;
       // Integer count = this.messageService.count(where_sql);
        Page<Org_mes> orgMesPage = this.orgMesService.selectPageExt(myPage, mySize, where_sql);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows",orgMesPage.getRecords());
       map.put("total", orgMesPage.getTotal());
        return map;
    }

    //前往增加社团信息页面
    @RequestMapping(value="add",method = RequestMethod.GET)
    public ModelAndView addDataShow(ModelMap modelMap){
        return new ModelAndView("admin/mes/addMes");
    }

    //新增社团信息
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addData(String mescreatetimeStr, Org_mes mesWithBLOBs, String exId) throws IllegalStateException{
        Map<String,Object> resultMap = new HashMap<>();
        if (mescreatetimeStr == null || exId == null){
            resultMap.put("status","fail");
            resultMap.put("message", "数据出错");
            return resultMap;
        }
        Date myDate = TimeUnit.StringToDate(mescreatetimeStr);
        mesWithBLOBs.setMesCreateTime(myDate);
        this.orgMesService.insertMess(mesWithBLOBs,exId);
        resultMap.put("status","success");
        resultMap.put("message", "新增成功");
        return resultMap;
    }

    //前往编辑社团信息页面
    @RequestMapping(value="edit",method = RequestMethod.GET)
    public String editDataShow(ModelMap model, Integer id){
        Org_mes mesWithBLOBs = this.orgMesService.selectOne(new EntityWrapper<Org_mes>().eq("id",id));
        String data = JSON.toJSONString(mesWithBLOBs);
        model.put("data",data);
        model.addAttribute("id",mesWithBLOBs.getId());
        return "admin/mes/addMes";
    }

    //获得社团关联人
    @RequestMapping(value="getByUnion",method = RequestMethod.GET)
    @ResponseBody
    public List<UserBo> getByUnion(Model model, Integer id){
        Map<String,Object> map = new HashMap<>();
        List<UserBo> users = this.orgMesService.getUnion(id);
        return users;
    }

    //编辑社团信息
    @RequestMapping(value="edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editData(String mescreatetimeStr,Org_mes mesWithBLOBs, ModelMap modelMap,String exId)throws IllegalStateException{
        Map<String,Object> resultMap = new HashMap<>();
        if (mescreatetimeStr == null || exId == null){
            resultMap.put("status","fail");
            resultMap.put("message", "数据出错");
            return resultMap;
        }
        Date myDate = TimeUnit.StringToDate(mescreatetimeStr);
        mesWithBLOBs.setMesCreateTime(myDate);
        this.orgMesService.updateMes(mesWithBLOBs,exId);
        resultMap.put("status","success");
        resultMap.put("message", "修改成功");
        return resultMap;
    }

    //删除社团信息
    @RequestMapping(value="delete",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteData(ModelMap modelMap,HttpServletRequest request,String ids) throws IllegalStateException, IOException{
        Map<String,Object> resultMap = new HashMap<>();
        this.orgMesService.deleteByIds(ids);
        resultMap.put("status","success");
        resultMap.put("message", "删除成功");
        return resultMap;
    }

    /*
     * 获取社团用户
     * */
    //前往查看社团列表页面(后台)
    @RequestMapping(value="getUserList",method = RequestMethod.GET)
    public String getUserList(ModelMap modelMap){
        return "admin/mes/mes_userList";
    }


    //获取所有社团用户列表
    @RequestMapping(value="getUserList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUserList(Integer page,Integer rows,String where_sql){
        Map<String, Object> map = new HashMap<String, Object>();
        Integer myPage = (page==null) ? 1 : page;	//获取分页数据
        Integer myRows = (rows==null) ? 10 : rows;
        Map<String, Object> userMesList = this.userService.getUserMesList(myPage, myRows, where_sql);
        return userMesList;
    }

    /*
     * 社团用户授权
     * */
    @RequestMapping(value="mes_userReo",method = RequestMethod.GET)
    public String mes_userReo(ModelMap modelMap,Integer id,Integer mesid,Integer jobNum){
        Map<String,Object> param = new HashMap<>();
        param.put("jobNum",jobNum);
        param.put("mesid",mesid);
        param.put("userid",id);
        String data = JSON.toJSONString(param);
        modelMap.put("id",id);
        modelMap.put("data",data);
        return "admin/mes/mes_userReo";
    }

    /*
     * 社团用户授权
     * */
    @RequestMapping(value="mes_userReo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> mes_userReo(Integer userid,Integer mesid,Integer jobNum){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message","授权成功");
        try {
            Org_mes_user mesUser = new Org_mes_user();
            mesUser.setJobNum(jobNum);
            this.orgMesUserService.update(mesUser,new EntityWrapper<Org_mes_user>().eq("userId",userid).eq("mesId",mesid));
        }catch (Exception e){
            resultMap.put("status","fail");
            resultMap.put("message","修改失败，未知错误");
        }
        return resultMap;
    }

    /*
     * 获取所有的社团
     * */
    @RequestMapping("/getAllName")
    @ResponseBody
    public List<Org_mes> getAllName() {
        List<Org_mes> mesList = this.orgMesService.selectList(null);
        return mesList;
    }

    /*
     * 社团用户新增
     * */
    @RequestMapping(value="mes_userAdd",method = RequestMethod.GET)
    public String mes_userAddShow(ModelMap modelMap,Integer mesid){
        modelMap.put("mesid",mesid);
        return "admin/mes/mes_userAdd";
    }

    @RequestMapping(value="mes_userAdd",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> mes_userAdd(ModelMap modelMap,Org_mes_user mesUser,String username){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message","已成功添加");
        try {
            if(!username.equals("")){
                resultMap = this.orgMesUserService.addMesUser(username, mesUser.getMesId(), mesUser.getJobNum());
            }
        }catch (Exception e){
            resultMap.put("status","fail");
            resultMap.put("message","新增失败，未知错误!");
        }
        return resultMap;
    }

    /*
     * 社团用户移除
     * */
    @RequestMapping(value="mes_userDetele",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> mes_userDetele(String param){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status","success");
        resultMap.put("message","已成功移除");
        try {
            JSONArray jsonArray = (JSONArray) JSON.parse(param);
            for(int ii=0;ii<jsonArray.size();ii++){
                JSONObject jsonObject =  jsonArray.getJSONObject(ii);
                this.orgMesUserService.deleteMesUser(jsonObject.getInteger("userid"),jsonObject.getInteger("mesid"));
                //this.mesUserService.deleteByUserAndMesId(jsonObject.getInteger("userid"),jsonObject.getInteger("mesid"));
            }
        }catch (Exception e){
            resultMap.put("status","fail");
            resultMap.put("message","移除，未知错误");
        }
        return resultMap;
    }
}

