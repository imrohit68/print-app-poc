package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.DataTransferObjects.FileDto;
import com.example.PrintAppPOC.Exceptions.InvalidTokenException;
import com.example.PrintAppPOC.Requests.CommonRequest;
import com.example.PrintAppPOC.Responses.FileResponse;
import com.example.PrintAppPOC.Responses.StatusResponse;
import com.example.PrintAppPOC.Services.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileEntityService;

    @PostMapping("/create")
    public ResponseEntity<StatusResponse> create(@RequestBody FileDto fileDto, @RequestHeader("Authorization") String token){
        fileEntityService.createFile(fileDto);
        return new ResponseEntity<>(new StatusResponse("File Created Successfully",true), HttpStatus.CREATED);
    }
    @PutMapping("/update/{fileId}")
    public ResponseEntity<FileDto> update(@RequestBody FileDto fileDto,@PathVariable String fileId,@RequestHeader("Authorization") String token){
        FileDto fileDto1 = fileEntityService.updateFile(fileDto,fileId);
        return new ResponseEntity<>(fileDto1,HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<FileDto>> getAll(@RequestHeader("Authorization") String token){
        List<FileDto> fileDto = fileEntityService.getAllFiles();
        return new ResponseEntity<>(fileDto,HttpStatus.OK);
    }
    @PostMapping("/getById")
    public ResponseEntity<FileResponse> getFileById(@RequestBody @Valid CommonRequest commonRequest, @RequestHeader("Authorization") String token){
        FileResponse response = fileEntityService.getById(commonRequest.getId());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable String fileId,@RequestHeader("Authorization") String token){
        fileEntityService.deleteFile(fileId);
        return "Deleted Successfully";
    }
}
