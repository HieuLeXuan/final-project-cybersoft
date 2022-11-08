package com.example.jiraproject.user.repository;

import com.example.jiraproject.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "select u from User u left join fetch u.roles left join fetch u.projects where u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query(value = "select u from User u left join fetch u.roles left join fetch u.projects where u.id = ?1")
    Optional<User> findByIdWithInfo(UUID userId);

    @Query(value = "select u from User u left join fetch u.roles left join fetch u.projects order by u.createdAt")
    Set<User> findAllWithInfo();

    @Query(value = "select u from User u left join fetch u.roles left join fetch u.projects",
    countQuery = "select count(u) from User u left join u.roles left join u.projects")
    Page<User> findAllWithInfoWithPaging(Pageable pageable);

    @Query(value = "select u from User u left join fetch u.roles left join fetch u.projects " +
            "where u.id in (select u.id from User u inner join u.projects p where p.id = ?1)")
    Set<User> findAllInsideProject(UUID projectId); //sai

    //SELECT STAFF FROM A PROJECT, STATUS = ACTIVE AND ROLE = EMPLOYEE
    @Query(value = "select u from User u left join fetch u.roles r left join fetch u.projects p " +
            "where u.accountStatus = 'ACTIVE' " +
            "and " +
            "r.id is not null " +
            "and " +
            "u.id not in (select u.id from User u inner join u.roles r where r.code <> 'EMP') " +
            "and " +
            "u.id not in (select u.id from User u inner join u.projects p where p.id = ?1)")
    Set<User> findAllOutsideProject(UUID projectId);
}
