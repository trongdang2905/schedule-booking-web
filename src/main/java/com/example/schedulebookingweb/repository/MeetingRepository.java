package com.example.schedulebookingweb.repository;

import com.example.schedulebookingweb.model.Meeting;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT m
        FROM Meeting m
        WHERE m.status = 'PENDING'
        AND m.remindAt <= :now
    """)
    List<Meeting> findDueAndLock(
            @Param("now") LocalDateTime now
    );
}
