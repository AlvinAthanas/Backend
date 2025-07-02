package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.DTO.SacramentSessionInfo;
import com.example.cms_backend.Model.Entities.SacramentRegistration;
import com.example.cms_backend.Model.Enums.SacramentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SacramentRegistrationRepository extends JpaRepository<SacramentRegistration, Long> {
    List<SacramentRegistration> findByParishIdAndSacramentTypeAndStartDateAndCompletionDate(
            Long parishId,
            SacramentType sacramentType,
            LocalDate startDate,
            LocalDate completionDate
    );

    @Query("SELECT DISTINCT new com.example.cms_backend.Model.DTO.SacramentSessionInfo(sr.startDate, sr.completionDate) " +
            "FROM SacramentRegistration sr " +
            "WHERE sr.parishId = :parishId AND sr.sacramentType = :type " +
            "AND sr.startDate IS NOT NULL AND sr.completionDate IS NOT NULL")
    List<SacramentSessionInfo> findDistinctSessionsByParishAndType(@Param("parishId") Long parishId,
                                                                   @Param("type") SacramentType type);


    Optional<SacramentRegistration> findByCandidateIdAndSacramentType(Long candidateId, SacramentType sacramentType);

}
