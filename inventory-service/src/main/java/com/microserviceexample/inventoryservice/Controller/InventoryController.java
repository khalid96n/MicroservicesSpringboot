package com.microserviceexample.inventoryservice.Controller;

import com.microserviceexample.inventoryservice.DTO.InventoryResponse;
import com.microserviceexample.inventoryservice.Service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {


    @Autowired
    private InventoryService inventoryServ;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return inventoryServ.isInStock(skuCode);
    }

}
