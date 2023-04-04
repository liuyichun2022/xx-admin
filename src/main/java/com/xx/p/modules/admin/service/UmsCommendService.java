package com.xx.p.modules.admin.service;

import com.xx.p.common.api.PageR;
import com.xx.p.modules.admin.dto.CommendDTO;

public interface UmsCommendService {
    PageR<CommendDTO> getList(String title, String nickName, String phone, Integer tag , Integer pageNum, Integer pageSize);

    boolean deleteCommend(Long id);


    boolean wonderfulCommend(Long id, Integer tag);
}
