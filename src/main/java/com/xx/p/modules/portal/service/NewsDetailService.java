package com.xx.p.modules.portal.service;

import com.xx.p.common.api.PageR;
import com.xx.p.modules.portal.dto.NmsNewsDetailDTO;
import com.xx.p.modules.portal.module.entity.NmsCommendRecord;

public interface NewsDetailService {

    NmsNewsDetailDTO getById(Long id);


    PageR<NmsCommendRecord> getCommendsById(Long newsId, Integer pageNum, Integer pageSize);
}
