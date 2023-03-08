package com.sanket.kv_store_cron_job.services;

import com.sanket.kv_store_cron_job.models.StoreEntry;
import com.sanket.kv_store_cron_job.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

// TODO: CRON job to clear/soft delete the entries

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteExpiredEntries() {
        Date now = Calendar.getInstance().getTime();
        long currentUnixTimeInMillis = now.toInstant().toEpochMilli();

        int count = storeRepository.countAllBytTTLNative(currentUnixTimeInMillis, 10000);
        int limit = 100;
        int pages = (int) (count / limit) + (count % limit != 0 ? 1 : 0);

        for (int page = 1; page <= pages; page++) {
            List<StoreEntry> expiredEntries = storeRepository.findAllByTTLNative(currentUnixTimeInMillis, limit);
            expiredEntries.forEach(entry -> entry.setTtl(-1L));
            storeRepository.saveAllAndFlush(expiredEntries);
        }

//        JPA Query
//        List<StoreEntry> expiredEntries = storeRepository.findAllByTTL(currentUnixTimeInMillis);

//        Native Query
//        List<StoreEntry> expiredEntries = storeRepository.findAllByTTLNative(currentUnixTimeInMillis);
//        expiredEntries.forEach(entry -> entry.setTtl(-1L));
//        storeRepository.saveAllAndFlush(expiredEntries);
    }

}
