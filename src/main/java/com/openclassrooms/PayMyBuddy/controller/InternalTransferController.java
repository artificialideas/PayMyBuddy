package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.service.InternalTransferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internalTransfers")
public class InternalTransferController {
    @Autowired
    private InternalTransferServiceImpl internalTransferServiceImpl;
}
