package com.gk.cbl.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gk.cbl.Bo.WordBo;
import com.gk.cbl.entity.Org_mes_user;
import com.gk.cbl.entity.Org_send_rece;
import com.gk.cbl.entity.Org_user;
import com.gk.cbl.entity.Org_word;
import com.gk.cbl.mapper.Org_wordMapper;
import com.gk.cbl.service.Org_mes_userService;
import com.gk.cbl.service.Org_userService;
import com.gk.cbl.service.Org_wordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 社团留言表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
@Service
public class Org_wordServiceImpl extends ServiceImpl<Org_wordMapper, Org_word> implements Org_wordService {

    @Autowired
    Org_userService userService;
    
    @Override
    public List<WordBo> getmesWord(Integer mesId) {
        if (mesId == null && mesId == 0) return null;

        List<Org_word> org_words = this.selectList(new EntityWrapper<Org_word>().eq("mesid", mesId));
        List<Org_user> orgUsers = userService.selectList(null);
        Map<String,String> wordmap = new HashMap<>();
        for (Org_user user : orgUsers) {
            wordmap.put(user.getId()+"",user.getUserName());
        }

        List<WordBo> wordBos = new ArrayList<>();
        for (Org_word word : org_words) {
            if (wordmap.containsKey(word.getUserid()+"")){
                WordBo wb = new WordBo();
                wb.setId(word.getId());
                wb.setUsername(wordmap.get(word.getUserid()+""));
                wb.setContent(word.getContent());
                wb.setMesid(word.getMesid());
                wb.setUserid(word.getUserid());
                wb.setWordTime(word.getWordTime());
                System.out.println(wb);
                if (wb == null){
                    break;
                }
                wordBos.add(wb);
            }
        }

        return wordBos;
    }
}
