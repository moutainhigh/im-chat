package com.im.chat.service.strategy;


import com.im.chat.annotation.SessionViewStrategyAnnotation;
import com.im.chat.entity.po.SessionView;
import com.im.chat.enums.CvsTypeEnum;
import com.im.chat.mapper.SessionViewMapper;
import com.im.user.entity.po.User;
import com.im.user.service.IFriendService;
import com.im.user.service.IUserService;
import com.mr.response.error.BusinessException;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@SessionViewStrategyAnnotation(cvsType= CvsTypeEnum.U)
public class SingleChatSessionViewCreatorStrategy implements SessionViewCreatorStrategy
{
    @Reference
    private IUserService iUserService;
    @Reference
    private IFriendService iFriendService;

    @Resource
    private SessionViewMapper sessionViewMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSessionView(User curUser, Long entityId) throws BusinessException
    {
        SessionView sessionView1 = sessionViewMapper.getSessionViewForEntity(curUser.getId(), entityId, CvsTypeEnum.U.getCode());
        User destUser = iUserService.getUserById(entityId);
        if(sessionView1 == null){
            Long id = createSingleSessionView(curUser.getId(), destUser);
            sessionView1 = new SessionView();
            sessionView1.setId(id);
        }

        SessionView sessionViewForDest = sessionViewMapper.getSessionViewForEntity(destUser.getId(), curUser.getId(), CvsTypeEnum.U.getCode());
        if(sessionViewForDest == null)
        {
            createSingleSessionView(destUser.getId(), curUser);
        }

        return sessionView1.getId();

    }



    private Long createSingleSessionView(Long ownerId, User destUser) throws BusinessException
    {
        SessionView sessionView = new SessionView();

        String note = iFriendService.queryFriendNote(ownerId, destUser.getId());
        if(note.isEmpty()){
            sessionView.setCvsName(destUser.getUsername());
        }else{
            sessionView.setCvsName(note);
        }
        sessionView.setCvsName(destUser.getUsername());
        sessionView.setAvatarUrl(destUser.getAvatarUrl());

        sessionView.setOwnerId(ownerId);
        sessionView.setCvsType(CvsTypeEnum.U.getCode());
        sessionView.setRelationEntityId(destUser.getId());

        int res = sessionViewMapper.insertSelective(sessionView);
        if(res < 1){
            throw new BusinessException("创建会话识图失败");
        }

        return sessionView.getId();
    }
}
