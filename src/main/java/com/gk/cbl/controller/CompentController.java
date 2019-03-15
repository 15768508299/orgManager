package com.gk.cbl.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gk.cbl.entity.Org_mes;
import com.gk.cbl.entity.Org_user;
import com.gk.cbl.service.Org_mesService;
import com.gk.cbl.service.Org_userService;
import com.gk.cbl.util.Compent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/compent")
public class CompentController {

    @Autowired
    Org_userService userService;

    @Autowired
    Org_mesService mesService;

    @RequestMapping(value = "/editUserMes",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView editUserMes(Model model, Integer userid){
        model.addAttribute("user",this.userService.selectOne(new EntityWrapper<Org_user>().eq("id",userid)));
        return new ModelAndView("compent/editUser");
    }

    //前往图片层页面
    @RequestMapping(value = "/ImgView",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView ImgView(){
        return new ModelAndView("compent/ImgView");
    }

    //文件上传

    @RequestMapping(value = "/uploadImg",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadImg(@RequestParam(value = "input-b1") MultipartFile file, HttpServletRequest request) throws IOException {
        request.getParameter("input-b1");
        Map<String,Object> resutltMap = new HashMap<>();
        resutltMap.put("status","success");
        if(file == null || file.isEmpty()){
            resutltMap.put("status","fail");
            resutltMap.put("message","上传文件为空");
        }
        else{
            String tempPath = Compent.stroeFile(request, (CommonsMultipartFile) file);
            resutltMap.put("uploadUrl",tempPath);
        }
        return resutltMap;
    }

    //前往编辑社团信息页面
    @RequestMapping(value = "/editMes",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView editMes(Integer mesid, Model model){
        Org_mes mes = mesService.selectOne(new EntityWrapper<Org_mes>().eq("id", mesid));
        model.addAttribute("mesWithBLOBs",mes);
        return new ModelAndView("compent/editMes");
    }
}
