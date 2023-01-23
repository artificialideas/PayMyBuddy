package com.openclassrooms.PayMyBuddy.dao;

import com.openclassrooms.PayMyBuddy.model.InternalTransfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalTransferRepository extends CrudRepository<InternalTransfer, Long> {
}
