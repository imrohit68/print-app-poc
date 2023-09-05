package com.example.PrintAppPOC.Controller;

import com.example.PrintAppPOC.Dtos.StoreDto;
import com.example.PrintAppPOC.Services.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    @PostMapping("/create")
    public ResponseEntity<StoreDto> createStore (@RequestBody StoreDto storeDto){
        StoreDto storeDto1 = storeService.createStore(storeDto);
        return new ResponseEntity<>(storeDto1,HttpStatus.CREATED);
    }
    @PutMapping("/update/{storeId}")
    public ResponseEntity<StoreDto> updateStore(@RequestBody StoreDto storeDto,@PathVariable Integer storeId){
        StoreDto storeDto1 = storeService.updateStore(storeDto,storeId);
        return new ResponseEntity<>(storeDto1,HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<StoreDto>> getAllStores(){
        List<StoreDto> storeDto = storeService.getAllStores();
        return ResponseEntity.ok(storeDto);
    }
    @GetMapping("getById/{storeId}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Integer storeId){
        StoreDto storeDto = storeService.findStoreById(storeId);
        return ResponseEntity.ok(storeDto);
    }
    @DeleteMapping("delete/{storeId}")
    public String deleteStoreById(@PathVariable Integer storeId){
        storeService.deleteStore(storeId);
        return "Deleted Successfully";
    }
}
