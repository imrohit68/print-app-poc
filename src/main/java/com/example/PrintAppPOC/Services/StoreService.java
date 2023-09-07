package com.example.PrintAppPOC.Services;

import com.example.PrintAppPOC.Dtos.StoreDto;

import java.util.List;

public interface StoreService {
    StoreDto createStore (StoreDto storeDto);
    StoreDto updateStore(StoreDto storeDto,String id);
    List<StoreDto> getAllStores();
    StoreDto findStoreById(String id);
    void deleteStore(String id);
}
