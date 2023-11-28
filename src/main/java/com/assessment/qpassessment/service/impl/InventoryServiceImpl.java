package com.assessment.qpassessment.service.impl;

import com.assessment.qpassessment.entity.Item;
import com.assessment.qpassessment.model.InventoryResponse;
import com.assessment.qpassessment.model.ItemDetails;
import com.assessment.qpassessment.model.UserRole;
import com.assessment.qpassessment.repository.InventoryRepository;
import com.assessment.qpassessment.service.InventoryService;
import com.assessment.qpassessment.utils.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public InventoryResponse<List<Item>> getAllItems(UserRole role) {
        List<Item> items = null;
        InventoryResponse<List<Item>> resp = new InventoryResponse<>();
        try {
            if (role.equals(UserRole.ADMIN)) {
                items = inventoryRepository.findAll();
                resp.setSuccess(true);
                resp.setResponseMessage(ResponseMessages.FOUND_ALL_ITEMS);
            }
            else if (role.equals(UserRole.ADMIN)) {
                items = inventoryRepository.findByQuantityGreaterThan(0);
                resp.setSuccess(true);
                resp.setResponseMessage(ResponseMessages.FOUND_ALL_AVAILABLE_ITEMS);
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(items);
        return resp;
    }

    @Override
    public InventoryResponse<Item> addAnItem(UserRole role, ItemDetails itemDetails) {
        Item item = null;
        InventoryResponse<Item> resp = new InventoryResponse<>();
        try {
            if (!role.equals(UserRole.ADMIN)) {
                resp.setSuccess(false);
                resp.setResponseMessage(ResponseMessages.ADMIN_ONLY_ALLOWED);
            } else if (itemDetails.getId() != null) {
                resp.setSuccess(false);
                resp.setResponseMessage(ResponseMessages.ID_PREASSIGNED);
            } else {
                item = new Item();
                item.setName(itemDetails.getName());
                item.setPrice(itemDetails.getPrice());
                item.setQuantity(itemDetails.getQuantity());
                inventoryRepository.save(item);
                resp.setSuccess(true);
                resp.setResponseMessage(ResponseMessages.ITEM_ADDED);
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(item);
        return resp;

    }

    @Override
    public InventoryResponse<Item> updateAnItem(UserRole role, ItemDetails itemDetails) {
        Item item = null;
        InventoryResponse<Item> resp = new InventoryResponse<>();
        try {
            if (!role.equals(UserRole.ADMIN)) {
                resp.setSuccess(false);
                resp.setResponseMessage(ResponseMessages.ADMIN_ONLY_ALLOWED);
            } else if (itemDetails.getId() == null) {
                resp.setSuccess(false);
                resp.setResponseMessage(ResponseMessages.ID_MISSING);
            } else {
                Optional<Item> itemOptional = inventoryRepository.findById(itemDetails.getId());
                if (itemOptional.isEmpty()) {
                    resp.setSuccess(false);
                    resp.setResponseMessage(ResponseMessages.INVALID_ID);
                } else {
                    item = itemOptional.get();
                    item.setName(itemDetails.getName());
                    item.setPrice(itemDetails.getPrice());
                    item.setQuantity(item.getQuantity());
                    inventoryRepository.save(item);
                    resp.setSuccess(true);
                    resp.setResponseMessage(ResponseMessages.ITEM_UPDATED);
                }
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(item);
        return resp;
    }

    @Override
    public InventoryResponse<Item> deleteAnItem(UserRole role, Integer id) {
        Item item = null;
        InventoryResponse<Item> resp = new InventoryResponse<>();
        try {
            if (!role.equals(UserRole.ADMIN)) {
                resp.setSuccess(false);
                resp.setResponseMessage(ResponseMessages.ADMIN_ONLY_ALLOWED);
            } else {
                Optional<Item> itemOptional = inventoryRepository.findById(id);
                if (itemOptional.isEmpty()) {
                    resp.setSuccess(false);
                    resp.setResponseMessage(ResponseMessages.INVALID_ID
                    );
                } else {
                    inventoryRepository.deleteById(id);
                    resp.setSuccess(true);
                    resp.setResponseMessage(ResponseMessages.ITEM_DELETED);
                }
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(item);
        return resp;
    }
}
