package com.example.PrintAppPOC.Services.ServiceImpl;

import com.example.PrintAppPOC.Dtos.FileDto;
import com.example.PrintAppPOC.Entity.Files;
import com.example.PrintAppPOC.Repo.FileRepo;
import com.example.PrintAppPOC.Services.FileEntityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileEntityImpl implements FileEntityService {
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
        Files files = fileRepo.findById(id).orElseThrow();
        files.setColor(fileDto.getColor());
        files.setEndPage(fileDto.getEndPage());
        files.setStartPage(fileDto.getStartPage());
        files.setNumberOfCopies(fileDto.getNumberOfCopies());
        Files files1 = fileRepo.save(files);
        return modelMapper.map(files1,FileDto.class);
    }

    @Override
    public FileDto getById(String fileId) {
        Files files = fileRepo.findById(fileId).orElseThrow();
        return modelMapper.map(files,FileDto.class);
    }
    @Override
    public List<FileDto> getAllFiles() {
        List<Files> files = fileRepo.findAll();
        List<FileDto> fileDto = files.stream().map(files1 -> modelMapper.map(files1,FileDto.class)).collect(Collectors.toList());
        return fileDto;
    }

    @Override
    public void deleteFile(String fileId) {
        Files files = fileRepo.findById(fileId).orElseThrow();
        fileRepo.delete(files);
    }
}
