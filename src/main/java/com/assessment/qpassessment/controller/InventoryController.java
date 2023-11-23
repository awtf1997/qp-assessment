package com.assessment.qpassessment.controller;

import com.assessment.qpassessment.model.AddItemRequest;
import com.assessment.qpassessment.model.InventoryResponse;
import com.assessment.qpassessment.model.Item;
import com.assessment.qpassessment.model.UserRole;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/inventory")
public class InventoryController {

    @GetMapping(path = "/items")
    public ResponseEntity<InventoryResponse<List<Item>>> getAllItems(@RequestHeader("my-number") UserRole role) {
        return null;
    }

    @PostMapping(path = "/items")
    public ResponseEntity<InventoryResponse<Item>> addItem(@RequestBody AddItemRequest newItem,
                                                           @RequestHeader(name = "USER_ROLE") UserRole role) {
        return null;
    }

    @PutMapping(path = "/items/{id}")
    public ResponseEntity<InventoryResponse<Item>> updateItem(@PathVariable(name = "id", required = true) String id,
                                                              @RequestParam(name = "name", required = false) String name,
                                                              @RequestParam(name = "price", required = false) Double price,
                                                              @RequestParam(name = "quantity", required = false) Integer quantity,
                                                              @RequestHeader(name = "USER_ROLE") UserRole role) {
        return null;
    }

    @DeleteMapping(path = "/items/{id}")
    public ResponseEntity<InventoryResponse<Item>> deleteItem(@PathVariable(name = "id", required = true) String id,
                                                              @RequestHeader(name = "USER_ROLE") UserRole role) {
        return null;
    }

}
