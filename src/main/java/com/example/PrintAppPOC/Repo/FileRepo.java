package com.example.PrintAppPOC.Repo;

import com.example.PrintAppPOC.Entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<Files,String> {
}
