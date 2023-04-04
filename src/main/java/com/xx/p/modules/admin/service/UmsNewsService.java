package com.xx.p.modules.admin.service;

import com.xx.p.common.api.PageR;
import com.xx.p.modules.admin.dto.AddNewsDTO;
import com.xx.p.modules.admin.dto.NewsDTO;
import com.xx.p.modules.admin.dto.UpdateCommendStatusDTO;

public interface UmsNewsService {

    PageR<NewsDTO> getList(String title, String publisher, Integer pubStatus, Integer pageNum, Integer pageSize);

    boolean add(AddNewsDTO addNewsDTO);

    boolean publish(Long id, Integer pubStatus);

    boolean updateCommendStatus(UpdateCommendStatusDTO updateCommendStatusDTO);


    NewsDTO getById(Long id);


    boolean delete(Long id);
}
