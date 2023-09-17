package com.example.PrintAppPOC.Services.ServiceImpl;

import com.example.PrintAppPOC.DataTransferObjects.FileDto;
import com.example.PrintAppPOC.Entities.Files;
import com.example.PrintAppPOC.Exceptions.ResourceNotFoundException;
import com.example.PrintAppPOC.Repositories.FileRepo;
import com.example.PrintAppPOC.Services.FileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileImpl implements FileService {
    private final FileRepo fileRepo;
    private final ModelMapper modelMapper;

    @Override
    public FileDto createFile(FileDto fileDto) {
        Files file = modelMapper.map(fileDto,Files.class);
        Files files = fileRepo.save(file);
        return modelMapper.map(files,FileDto.class);
    }

    @Override
    public FileDto updateFile(FileDto fileDto, String id) {
        Files files = fileRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("File","fileId",id));
        files.setColor(fileDto.getColor());
        files.setEndPage(fileDto.getEndPage());
        files.setStartPage(fileDto.getStartPage());
        files.setNumberOfCopies(fileDto.getNumberOfCopies());
        Files files1 = fileRepo.save(files);
        return modelMapper.map(files1,FileDto.class);
    }

    @Override
    public FileDto getById(String fileId) {
        Files files = fileRepo.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("File","fileId",fileId));
        return modelMapper.map(files,FileDto.class);
    }
    @Override
    public List<FileDto> getAllFiles() {
        List<Files> files = fileRepo.findAll();
        List<FileDto> fileDto = files
                .stream().map(files1 -> modelMapper.map(files1,FileDto.class)).collect(Collectors.toList());
        return fileDto;
    }

    @Override
    public void deleteFile(String fileId) {
        Files files = fileRepo.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("File","fileId",fileId));
        fileRepo.delete(files);
    }
}
