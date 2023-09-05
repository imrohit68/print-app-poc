package com.example.PrintAppPOC.Services;

import com.example.PrintAppPOC.Dtos.StoreDto;

import java.util.List;

public interface StoreService {
    StoreDto createStore (StoreDto storeDto);
    StoreDto updateStore(StoreDto storeDto,Integer id);
    List<StoreDto> getAllStores();
    StoreDto findStoreById(Integer id);
    void deleteStore(Integer id);
}
