package com.mrgrd56.commonserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class StoredDataOutDto {
    private String id;
    private String mime;
}
