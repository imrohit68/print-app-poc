package com.example.PrintAppPOC.Requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonRequest {
    @NotEmpty(message = "Ids Cannot be Empty")
    private List<String> id;
}
