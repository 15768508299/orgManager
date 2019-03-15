package com.gk.cbl.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gk.cbl.Bo.UserBo;
import com.gk.cbl.Bo.WordBo;
import com.gk.cbl.entity.Org_mes;
import com.gk.cbl.entity.Org_user;
import com.gk.cbl.entity.Org_word;
import com.gk.cbl.service.Org_mesService;
import com.gk.cbl.service.Org_mes_userService;
import com.gk.cbl.service.Org_wordService;
import com.gk.cbl.util.TimeUnit;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>
 * 社团留言表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Controller
@RequestMapping("/org_word")
public class Org_wordController {

    @Autowired
    Org_wordService wordService;

    @Autowired
    Org_mes_userService orgMesUserService;

    @Autowired
    Org_mesService orgMesService;


    @RequestMapping("/comment")
    public String comment(Org_word wordm, ModelMap map, String tempContent) throws UnsupportedEncodingException {
        tempContent = URLDecoder.decode(tempContent,"utf-8");
        wordm.setContent(tempContent);
        Org_user user = (Org_user) SecurityUtils.getSubject().getPrincipal();
        if (user == null ) return "redirect:/org_index_ol/login";
        wordm.setUserid(user.getId());
        wordm.setWordTime(TimeUnit.getDate());
        wordService.insert(wordm);
        int mesId = wordm.getMesid();
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

}

