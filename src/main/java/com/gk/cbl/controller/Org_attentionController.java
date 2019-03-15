package com.gk.cbl.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gk.cbl.entity.Org_attention;
import com.gk.cbl.service.Org_attentionService;
import com.gk.cbl.util.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 关注表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Controller
@RequestMapping("/org_attention")
public class Org_attentionController {

    @Autowired
    Org_attentionService attentionService;

    /**
     *  异步处理
     * @param attention 带有两个参数：用户id，社团id
     * @return 返回关注结果
     */
    @RequestMapping(value = "addAttention",method = RequestMethod.POST)
    @ResponseBody
    public Map addAttention(Org_attention attention){
        Map<String,Object> resultMap = new HashMap();
        if (attention.getMesid() == null || attention.getMesid() == null){
            resultMap.put("status", "fail");
            resultMap.put("message", "关注失败,请重新刷新页面！");
        }
        resultMap.put("status", "success");
        resultMap.put("message", "关注成功");
        Integer result = this.attentionService.selectCount(new EntityWrapper<Org_attention>().eq("userid",attention.getUserid()).eq("mesid",attention.getMesid()));
        if(result>0){
            resultMap.put("status", "fail");
            resultMap.put("message", "你已关注过啦！");
        }else{
            attention.setAttTime(TimeUnit.getDate());
            try {
                this.attentionService.insert(attention);
            }catch (Exception e){
                resultMap.put("status", "fail");
                resultMap.put("message", "关注失败,请联系管理员");
            }
        }
        return resultMap;
    }

}

