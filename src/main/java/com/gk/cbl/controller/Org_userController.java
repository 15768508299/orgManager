package com.gk.cbl.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gk.cbl.entity.Org_user;
import com.gk.cbl.service.Org_userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
@RequestMapping("/org_user")
public class Org_userController {

    @Autowired
    Org_userService userService;




    /*
    ------------------------------------------------------------------------------------------------------
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ---------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< |   后台模块   | >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    | <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ---------------- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> |
    ------------------------------------------------------------------------------------------------------
     */

    @RequestMapping("getAllName")
    @ResponseBody
    public List<Org_user> getAllName(){
        return  this.userService.selectList(null);
    }

    //前往查看社团列表页面
    @RequestMapping(value = "adminGetList", method = RequestMethod.GET)
    public ModelAndView getList(ModelMap modelMap) {
        return new ModelAndView("admin/user/list");
    }

    //获取所有社团列表
    @RequestMapping(value = "adminGetList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(Integer page, Integer rows, String sort, String order, String where_sql) {
        Integer myPage = (page == null) ? 1 : page;    //获取分页数据
        Integer myRows = (rows == null) ? 10 : rows;
        Map<String, Object> map = new HashMap<String, Object>();
        Page<Org_user> orgUserPage = this.userService.getList(myRows, myPage, where_sql);
        map.put("rows",orgUserPage.getRecords());
        map.put("total", orgUserPage.getTotal());
        return map;
    }

    //前往增加用户信息页面
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addDataShow(ModelMap modelMap) {
        return "admin/user/addMes";
    }

    //新增用户信息
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addData(Org_user user) throws IllegalStateException{
        Map<String, Object> resultMap = new HashMap<>();
        this.userService.insert(user);
        resultMap.put("status", "success");
        resultMap.put("message", "新增成功");
        return resultMap;
    }

    //前往编辑用户信息页面
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String editDataShow(Model model, Integer id) {
        Org_user user = this.userService.selectOne(new EntityWrapper<Org_user>().eq("id",id));
        String data = JSON.toJSONString(user);
        model.addAttribute("id", user.getId());
        model.addAttribute("data", data);
        return "admin/user/addMes";
    }

    //编辑用户信息
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editData(Org_user user, HttpServletRequest request, HttpSession session) throws IllegalStateException{
        Map<String, Object> resultMap = new HashMap<>();
        this.userService.updateById(user);
        //刷新下session
        session.setAttribute("user", user);
        resultMap.put("status", "success");
        resultMap.put("message", "修改成功");
        return resultMap;
    }

    //删除用户信息
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteData(ModelMap modelMap, HttpServletRequest request, String ids) throws IllegalStateException {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        resultMap.put("message", "删除成功");
        if (ids == null || ids.length() < 1){
            resultMap.put("status", "fail");
            resultMap.put("message", "删除失败");
        }
        this.userService.deleteByIds(ids);
        return resultMap;
    }
}

