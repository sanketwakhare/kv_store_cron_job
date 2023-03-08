package com.sanket.kv_store_cron_job.repositories;

import com.sanket.kv_store_cron_job.models.StoreEntry;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntry, UUID> {

    @Query("select e from StoreEntry e where e.ttl!=-1 and e.ttl<=:currentUnixTimeInMillis")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<StoreEntry> findAllByTTL(@Param("currentUnixTimeInMillis") long currentUnixTimeInMillis);

    @Query(value = "select count(*) from store s where s.ttl!=-1 and s.ttl<=:currentUnixTimeInMillis limit :limit for share skip locked", nativeQuery = true)
    int countAllBytTTLNative(@Param("currentUnixTimeInMillis") long currentUnixTimeInMillis,
                            @Param("limit") int limit);

    @Query(value = "select * from store s where s.ttl!=-1 and s.ttl<=:currentUnixTimeInMillis limit :limit for update skip locked", nativeQuery = true)
    List<StoreEntry> findAllByTTLNative(@Param("currentUnixTimeInMillis") long currentUnixTimeInMillis,
                                        @Param("limit") int limit);

}
