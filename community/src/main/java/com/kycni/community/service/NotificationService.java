package com.kycni.community.service;

import com.kycni.community.dto.NotificationDTO;
import com.kycni.community.dto.PaginationDTO;
import com.kycni.community.enums.NotificationStatusEnum;
import com.kycni.community.enums.NotificationTypeEnum;
import com.kycni.community.exception.CustomizeErrorCode;
import com.kycni.community.exception.CustomizeException;
import com.kycni.community.mapper.NotificationMapper;
import com.kycni.community.model.Notification;
import com.kycni.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Kycni
 * @date 2022/1/28 20:30
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    public PaginationDTO list(Long id, Integer page, Integer size) {
        /*创建类的对象*/
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        int totalPage;
        Example notifyExample = new Example(Notification.class);
        notifyExample.createCriteria().andEqualTo("receiver",id);
        notifyExample.setOrderByClause("gmt_create desc");
        Integer totalCount = notificationMapper.selectCountByExample(notifyExample);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        /*应用类的分页方法*/
        paginationDTO.setPagination(totalPage, page);

        /*设置当前页*/
        if (page < 1) {
            page = 1;
        }

        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        /*设置偏移量: size*(page-1): 跳过的数据行*/
        int offset = size * (page - 1);

        Example example = new Example(Notification.class);
        example.createCriteria().andEqualTo("receiver",id);
        List<Notification> notificationList = notificationMapper.selectByExampleAndRowBounds(example, new RowBounds(offset, size));
        
        if (notificationList.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOList = new ArrayList<>();

        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOList);
        return paginationDTO;
    }

    public Long unreadCount(Long userId) {
        Example notifyExample = new Example(Notification.class);
        notifyExample.createCriteria()
                .andEqualTo("receiver",userId)
                .andEqualTo("status",0);
        return (long) notificationMapper.selectCountByExample(notifyExample);
    }

    public NotificationDTO read(Long id, User user) {
        
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FIND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
