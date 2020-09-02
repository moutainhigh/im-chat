package com.im.chat.service;

import com.im.chat.entity.domain.InboxDo;
import com.mr.response.error.BusinessException;

import java.util.List;

public interface IInboxService {
    public List<InboxDo> queryInboxByUserIdCvsId(Long userId, Long cvsId) throws BusinessException;
}
