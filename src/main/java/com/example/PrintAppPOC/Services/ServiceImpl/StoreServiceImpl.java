package com.example.PrintAppPOC.Services.ServiceImpl;

import com.example.PrintAppPOC.Dtos.StoreDto;
import com.example.PrintAppPOC.Entity.Store;
import com.example.PrintAppPOC.Exception.ResourceNotFoundException;
import com.example.PrintAppPOC.Repo.StoreRepo;
import com.example.PrintAppPOC.Services.StoreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepo storeRepo;
    private final ModelMapper modelMapper;
    @Override
    public StoreDto createStore(StoreDto storeDto) {
        Store store = modelMapper.map(storeDto, Store.class);
        Store savedStore = storeRepo.save(store);
        return modelMapper.map(savedStore,StoreDto.class);
    }

    @Override
    public StoreDto updateStore(StoreDto storeDto, String id) {
        Store store = storeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Store","storeId",id));
        store.setStoreName(storeDto.getStoreName());
        store.setLatitude(storeDto.getLatitude());
        store.setLongitude(storeDto.getLongitude());
        Store store1 = storeRepo.save(store);
        return modelMapper.map(store1,StoreDto.class);
    }

    @Override
    public List<StoreDto> getAllStores() {
        List<Store> stores = storeRepo.findAll();
        return stores.stream()
                .map(store -> modelMapper.map(store,StoreDto.class)).toList();
    }

    @Override
    public StoreDto findStoreById(String id) {
        Store store = storeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Store","storeId",id));
        return modelMapper.map(store,StoreDto.class);
    }

    @Override
    public void deleteStore(String id) {
        Store store = storeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Store","storeId",id));
        storeRepo.delete(store);
    }
}
