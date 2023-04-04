package com.xx.p.modules.portal.service;

import com.xx.p.modules.portal.dto.NewsGroupListDTO;

import java.util.List;

public interface MyPageService {

    List<NewsGroupListDTO> myCollectList(Integer pageNum, Integer pageSize);
}
