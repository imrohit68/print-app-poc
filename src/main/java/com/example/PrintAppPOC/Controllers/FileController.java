package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.DataTransferObjects.FileDto;
import com.example.PrintAppPOC.Exceptions.InvalidTokenException;
import com.example.PrintAppPOC.Services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileEntityService;

    @PostMapping("create")
    public ResponseEntity<FileDto> create(@RequestBody FileDto fileDto,@RequestHeader("Authorization") String token){
        FileDto fileDto1 = fileEntityService.createFile(fileDto);
        return new ResponseEntity<>(fileDto1, HttpStatus.CREATED);
    }
    @PutMapping("update/{fileId}")
    public ResponseEntity<FileDto> update(@RequestBody FileDto fileDto,@PathVariable String fileId,@RequestHeader("Authorization") String token){
        FileDto fileDto1 = fileEntityService.updateFile(fileDto,fileId);
        return new ResponseEntity<>(fileDto1,HttpStatus.OK);
    }
    @GetMapping("getAll")
    public ResponseEntity<List<FileDto>> getAll(@RequestHeader("Authorization") String token){
        List<FileDto> fileDto = fileEntityService.getAllFiles();
        return new ResponseEntity<>(fileDto,HttpStatus.OK);
    }
    @GetMapping("getById/{fileId}")
    public ResponseEntity<FileDto> getFileById(@PathVariable String fileId,@RequestHeader("Authorization") String token){
        FileDto fileDto = fileEntityService.getById(fileId);
        return new ResponseEntity<>(fileDto,HttpStatus.OK);
    }
    @DeleteMapping("delete/{fileId}")
    public String deleteFile(@PathVariable String fileId,@RequestHeader("Authorization") String token){
        fileEntityService.deleteFile(fileId);
        return "Deleted Successfully";
    }
}
