package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByTitleContaining(String keyword);

    @Query("SELECT n FROM Notification n WHERE " +
            "(n.isGlobal = true AND n.parishId = :userParishId) OR " +
            "n.kandaId IN :kandaIds OR " +
            "n.groupId IN :groupIds")
    List<Notification> findRelevantNotifications(@Param("groupIds") Set<Long> groupIds,
                                                 @Param("kandaIds") Set<Long> kandaIds,
                                                 @Param("userParishId") Long userParishId);


}
