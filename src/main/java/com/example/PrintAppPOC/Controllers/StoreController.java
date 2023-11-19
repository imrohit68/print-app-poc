package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.DataTransferObjects.StoreDto;
import com.example.PrintAppPOC.Exceptions.InvalidTokenException;
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
    public ResponseEntity<StoreDto> createStore (@RequestBody StoreDto storeDto,@RequestHeader("Authorization") String token){
        StoreDto storeDto1 = storeService.createStore(storeDto);
        return new ResponseEntity<>(storeDto1,HttpStatus.CREATED);
    }
    @PutMapping("/update/{storeId}")
    public ResponseEntity<StoreDto> updateStore(@RequestBody StoreDto storeDto,@PathVariable String storeId,@RequestHeader("Authorization") String token){
        StoreDto storeDto1 = storeService.updateStore(storeDto,storeId);
        return new ResponseEntity<>(storeDto1,HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<StoreDto>> getAllStores(@RequestHeader("Authorization") String token){
        List<StoreDto> storeDto = storeService.getAllStores();
        return ResponseEntity.ok(storeDto);
    }
    @GetMapping("getById/{storeId}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable String storeId,@RequestHeader("Authorization") String token){
        StoreDto storeDto = storeService.findStoreById(storeId);
        return ResponseEntity.ok(storeDto);
    }
    @DeleteMapping("delete/{storeId}")
    public String deleteStoreById(@PathVariable String storeId,@RequestHeader("Authorization") String token){
        storeService.deleteStore(storeId);
        return "Deleted Successfully";
    }
}
