package com.microserviceexample.inventoryservice.Service;

import com.microserviceexample.inventoryservice.DTO.InventoryResponse;
import com.microserviceexample.inventoryservice.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepo;

    @Transactional(readOnly=true)
    public  List<InventoryResponse> isInStock(List<String> skuCode){
        return  inventoryRepo.findBySkuCodeIn(skuCode).stream()
                .map(inventory->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0)
                            .build()
                ).toList();
    }
}
