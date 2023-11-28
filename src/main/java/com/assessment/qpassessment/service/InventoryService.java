package com.assessment.qpassessment.service;

import com.assessment.qpassessment.entity.Item;
import com.assessment.qpassessment.model.InventoryResponse;
import com.assessment.qpassessment.model.ItemDetails;
import com.assessment.qpassessment.model.UserRole;

import java.util.List;

public interface InventoryService {

    public InventoryResponse<List<Item>> getAllItems(UserRole role);

    public InventoryResponse<Item> addAnItem(UserRole role, ItemDetails itemDetails);

    public InventoryResponse<Item> updateAnItem(UserRole role, ItemDetails itemDetails);

    public InventoryResponse<Item> deleteAnItem(UserRole role, Integer id);

}
