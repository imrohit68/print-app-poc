package com.example.PrintAppPOC.Repositories;

import com.example.PrintAppPOC.Entities.Files;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;

public interface FileRepo extends JpaRepository<Files,String> {
}
