package com.mrgrd56.commonserver.repository;

import com.mrgrd56.commonserver.domain.StoredData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StoredDataRepository extends MongoRepository<StoredData, String> {
    Optional<StoredData> findByIdAndRawDataNotNull(String id);
}
