package com.lms.api.admin.controller.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCommonCodeRequest {

    @NotNull
    String codeGroup;
    String codeGroupName;

    @NotNull
    String code;
    @NotNull
    String name;
    @NotNull
    int sort;
    @NotNull
    String useYn;

}
