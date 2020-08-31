package com.im.chat.service;

import com.im.chat.entity.po.SessionView;
import com.im.chat.entity.vo.SessionViewVo;
import com.im.chat.enums.CvsTypeEnum;
import com.mr.response.error.BusinessException;

import java.util.List;

public interface ISessionViewService {

    public void createSessionView(Long userId, CvsTypeEnum cvsType, Long entityId) throws BusinessException;

    public List<SessionView> queryMySessionViewList(Long userId);

    public void deleteSessionView(Long cvsId) throws BusinessException;

    /**
     * 查询当前用户和指定对象的会话
     */
    public SessionView getSessionViewForEntity(Long userId, Long relationEntityId, CvsTypeEnum cvsTypeEnum);

    public SessionView selectById(Long cvsId);
}
