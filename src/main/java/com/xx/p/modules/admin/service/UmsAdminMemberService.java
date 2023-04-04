package com.xx.p.modules.admin.service;

import com.xx.p.common.api.PageR;
import com.xx.p.modules.admin.dto.AddMemberDTO;
import com.xx.p.modules.admin.dto.UmsMemberDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UmsAdminMemberService {

    boolean batchImport(MultipartFile file);

    boolean add(AddMemberDTO addMemberDTO);

    PageR<UmsMemberDTO> getList(String phone , String name, String nickname, Integer pageNum, Integer pageSize);
}
