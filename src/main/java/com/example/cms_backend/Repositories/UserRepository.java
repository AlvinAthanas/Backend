package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.Roles;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContaining(String name);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles LEFT JOIN FETCH u.authorities WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("""
    SELECT u FROM User u
    JOIN u.groups g
    WHERE (:description IS NULL OR g.description = :description)
      AND (:name IS NULL OR g.name = :name)
""")
    List<User> findUsersByGroupDescriptionAndName(
            @Param("description") String description,
            @Param("name") String name
    );

    @Query("""
    SELECT COUNT(u) FROM User u
    JOIN u.groups g
    WHERE (:description IS NULL OR g.description = :description)
      AND (:name IS NULL OR g.name = :name)
""")
    Long countUsersByGroupDescriptionAndName(
            @Param("description") String description,
            @Param("name") String name
    );

    List<User> findAllByParishId(Long parishId);


    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.parishId = :parishId AND r.name IN :roleNames")
    List<User> findByParishIdAndRoleNames(@Param("parishId") Long parishId, @Param("roleNames") Set<String> roleNames);

    List<User> findByParishIdAndRolesIn(Long parishId, Set<Role> roles);


    @Query("SELECT COUNT(u) FROM User u JOIN u.groups g WHERE g.id = :groupId")
    Long countUsersInGroup(@Param("groupId") Long groupId);




}
