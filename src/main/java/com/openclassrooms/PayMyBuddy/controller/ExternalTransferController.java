package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.service.ExternalTransferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/externalTransfers")
public class ExternalTransferController {
    @Autowired
    private ExternalTransferServiceImpl externalTransferServiceImpl;
}
