package com.xx.p.modules.portal.service;

public interface UserOperationService {
    Boolean commend(Long newsId, String msg);

    boolean likeNews(Long newsId);

    boolean  cancelLike(Long newsId);

    boolean collectNews(Long newsId);

    boolean  cancelCollect(Long newsId);
}
