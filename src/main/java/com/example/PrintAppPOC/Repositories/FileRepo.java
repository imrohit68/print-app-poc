package com.example.PrintAppPOC.Repositories;

import com.example.PrintAppPOC.Entities.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<Files,String> {
}
