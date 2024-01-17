package com.example.PrintAppPOC.Services;

import com.example.PrintAppPOC.DataTransferObjects.StoreDto;

import java.util.List;

public interface StoreService {
    StoreDto createStore (StoreDto storeDto);
    StoreDto updateStore(StoreDto storeDto,String id);
    List<StoreDto> getAllStores();
    StoreDto findStoreById(String id);
    void deleteStore(String id);
    String getByToken(String token);
}
