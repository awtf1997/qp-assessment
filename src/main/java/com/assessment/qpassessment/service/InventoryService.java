package com.assessment.qpassessment.service;

import com.assessment.qpassessment.entity.Item;
import com.assessment.qpassessment.model.InventoryResponse;
import com.assessment.qpassessment.model.ItemDetails;
import com.assessment.qpassessment.model.UserRole;

import java.util.List;

public interface InventoryService {

    public InventoryResponse<List<Item>> getAllItems(Integer userId);

    public InventoryResponse<Item> addAnItem(Integer userId, ItemDetails itemDetails);

    public InventoryResponse<Item> updateAnItem(Integer userId, ItemDetails itemDetails);

    public InventoryResponse<Item> deleteAnItem(Integer userId, Integer id);

}
