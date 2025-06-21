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

    @Query("""
    SELECT n FROM Notification n WHERE
    (n.userId = :userId) OR
    (n.userId IS NULL AND n.groupId IS NOT NULL AND n.groupId IN :groupIds) OR
    (n.userId IS NULL AND n.groupId IS NULL AND n.kandaId IS NOT NULL AND n.kandaId IN :kandaIds) OR
    (n.userId IS NULL AND n.groupId IS NULL AND n.kandaId IS NULL AND n.parishId = :parishId) OR
    (n.userId IS NULL AND n.isGlobal = true AND n.parishId = :parishId)
    """)
    List<Notification> findScopedNotifications(@Param("groupIds") Set<Long> groupIds,
                                               @Param("kandaIds") Set<Long> kandaIds,
                                               @Param("parishId") Long parishId,
                                               @Param("userId") Long userId);



    List<Notification> findByParishId(Long parishId);


}
