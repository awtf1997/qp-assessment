package com.assessment.qpassessment.service.impl;

import com.assessment.qpassessment.entity.Item;
import com.assessment.qpassessment.entity.User;
import com.assessment.qpassessment.model.InventoryResponse;
import com.assessment.qpassessment.model.ItemDetails;
import com.assessment.qpassessment.model.UserResponse;
import com.assessment.qpassessment.model.UserRole;
import com.assessment.qpassessment.repository.InventoryRepository;
import com.assessment.qpassessment.repository.UserRepository;
import com.assessment.qpassessment.service.InventoryService;
import com.assessment.qpassessment.service.UserService;
import com.assessment.qpassessment.utils.InventoryResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private UserService userService;

    @Override
    public InventoryResponse<List<Item>> getAllItems(Integer userId) {
        List<Item> items = null;
        InventoryResponse<List<Item>> resp = new InventoryResponse<>();
        UserResponse<User> userResponse = userService.getUser(userId);
        try {
            UserRole role = null;
            if (userResponse.getSuccess()) {
                role = userResponse.getResponseBody().getRole();
                if (role.equals(UserRole.ADMIN)) {
                    items = inventoryRepository.findAll();
                    resp.setSuccess(true);
                    resp.setResponseMessage(InventoryResponseMessages.FOUND_ALL_ITEMS);
                } else if (role.equals(UserRole.USER)) {
                    items = inventoryRepository.findByQuantityGreaterThan(0);
                    resp.setSuccess(true);
                    resp.setResponseMessage(InventoryResponseMessages.FOUND_ALL_AVAILABLE_ITEMS);
                }
            } else {
                resp.setSuccess(false);
                resp.setResponseMessage(userResponse.getResponseMessage());
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(items);
        return resp;
    }

    @Override
    public InventoryResponse<Item> addAnItem(Integer userId, ItemDetails itemDetails) {
        Item item = null;
        InventoryResponse<Item> resp = new InventoryResponse<>();
        UserResponse<User> userResponse = userService.getUser(userId);
        try {
            UserRole role = null;
            if (userResponse.getSuccess()) {
                role = userResponse.getResponseBody().getRole();
                if (!role.equals(UserRole.ADMIN)) {
                    resp.setSuccess(false);
                    resp.setResponseMessage(InventoryResponseMessages.ADMIN_ONLY_ALLOWED);
                } else if (itemDetails.getId() != null) {
                    resp.setSuccess(false);
                    resp.setResponseMessage(InventoryResponseMessages.ID_PREASSIGNED);
                } else {
                    item = new Item();
                    item.setName(itemDetails.getName());
                    item.setPrice(itemDetails.getPrice());
                    item.setQuantity(itemDetails.getQuantity());
                    inventoryRepository.save(item);
                    resp.setSuccess(true);
                    resp.setResponseMessage(InventoryResponseMessages.ITEM_ADDED);
                }
            } else {
                resp.setSuccess(false);
                resp.setResponseMessage(userResponse.getResponseMessage());
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(item);
        return resp;

    }

    @Override
    public InventoryResponse<Item> updateAnItem(Integer userId, ItemDetails itemDetails) {
        Item item = null;
        InventoryResponse<Item> resp = new InventoryResponse<>();
        UserResponse<User> userResponse = userService.getUser(userId);
        try {
            UserRole role = null;
            if (userResponse.getSuccess()) {
                role = userResponse.getResponseBody().getRole();
                if (!role.equals(UserRole.ADMIN)) {
                    resp.setSuccess(false);
                    resp.setResponseMessage(InventoryResponseMessages.ADMIN_ONLY_ALLOWED);
                } else if (itemDetails.getId() == null) {
                    resp.setSuccess(false);
                    resp.setResponseMessage(InventoryResponseMessages.ID_MISSING);
                } else {
                    Optional<Item> itemOptional = inventoryRepository.findById(itemDetails.getId());
                    if (itemOptional.isEmpty()) {
                        resp.setSuccess(false);
                        resp.setResponseMessage(InventoryResponseMessages.INVALID_ID);
                    } else {
                        item = itemOptional.get();
                        item.setName(itemDetails.getName());
                        item.setPrice(itemDetails.getPrice());
                        item.setQuantity(item.getQuantity());
                        inventoryRepository.save(item);
                        resp.setSuccess(true);
                        resp.setResponseMessage(InventoryResponseMessages.ITEM_UPDATED);
                    }
                }
            } else {
                resp.setSuccess(false);
                resp.setResponseMessage(userResponse.getResponseMessage());
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(item);
        return resp;
    }

    @Override
    public InventoryResponse<Item> deleteAnItem(Integer userId, Integer id) {
        Item item = null;
        InventoryResponse<Item> resp = new InventoryResponse<>();
        UserResponse<User> userResponse = userService.getUser(userId);
        try {
            UserRole role = null;
            if (userResponse.getSuccess()) {
                role = userResponse.getResponseBody().getRole();
                if (!role.equals(UserRole.ADMIN)) {
                    resp.setSuccess(false);
                    resp.setResponseMessage(InventoryResponseMessages.ADMIN_ONLY_ALLOWED);
                } else {
                    Optional<Item> itemOptional = inventoryRepository.findById(id);
                    if (itemOptional.isEmpty()) {
                        resp.setSuccess(false);
                        resp.setResponseMessage(InventoryResponseMessages.INVALID_ID
                        );
                    } else {
                        inventoryRepository.deleteById(id);
                        resp.setSuccess(true);
                        resp.setResponseMessage(InventoryResponseMessages.ITEM_DELETED);
                    }
                }
            } else {
                resp.setSuccess(false);
                resp.setResponseMessage(userResponse.getResponseMessage());
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(item);
        return resp;
    }
}
