package com.gk.cbl.service;

import com.gk.cbl.Bo.WordBo;
import com.gk.cbl.entity.Org_word;
import com.baomidou.mybatisplus.service.IService;

import java.net.Inet4Address;
import java.util.List;

/**
 * <p>
 * 社团留言表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-22
 */
public interface Org_wordService extends IService<Org_word> {

    List<WordBo> getmesWord(Integer mesId);
}
