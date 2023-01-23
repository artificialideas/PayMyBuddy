package com.openclassrooms.PayMyBuddy.dao;

import com.openclassrooms.PayMyBuddy.model.ExternalTransfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalTransferRepository extends CrudRepository<ExternalTransfer, Long> {
}
