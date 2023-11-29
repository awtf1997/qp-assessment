package com.assessment.qpassessment.controller;

import com.assessment.qpassessment.entity.Item;
import com.assessment.qpassessment.model.InventoryResponse;
import com.assessment.qpassessment.model.ItemDetails;
import com.assessment.qpassessment.model.UserRole;
import com.assessment.qpassessment.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping(path = "/items")
    public ResponseEntity<InventoryResponse<List<Item>>> getAllItems(@RequestHeader(name = "USER_ROLE") UserRole role) {
        InventoryResponse<List<Item>> resp = null;
        try {
            resp = inventoryService.getAllItems(role);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new InventoryResponse<List<Item>>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @PostMapping(path = "/items")
    public ResponseEntity<InventoryResponse<Item>> addItem(@RequestBody ItemDetails itemDetails,
                                                           @RequestHeader(name = "USER_ROLE") UserRole role) {
        InventoryResponse<Item> resp = null;
        try {
            resp = inventoryService.addAnItem(role, itemDetails);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new InventoryResponse<Item>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @PutMapping(path = "/items")
    public ResponseEntity<InventoryResponse<Item>> updateItem(@RequestBody ItemDetails itemDetails,
                                                              @RequestHeader(name = "USER_ROLE") UserRole role) {
        InventoryResponse<Item> resp = null;
        try {
            resp = inventoryService.updateAnItem(role, itemDetails);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new InventoryResponse<Item>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @DeleteMapping(path = "/items/{id}")
    public ResponseEntity<InventoryResponse<Item>> deleteItem(@PathVariable(name = "id", required = true) Integer id,
                                                              @RequestHeader(name = "USER_ROLE") UserRole role) {
        InventoryResponse<Item> resp = null;
        try {
            resp = inventoryService.deleteAnItem(role, id);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new InventoryResponse<Item>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

}
