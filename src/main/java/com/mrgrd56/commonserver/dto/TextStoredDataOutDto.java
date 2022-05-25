package com.mrgrd56.commonserver.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TextStoredDataOutDto extends StoredDataOutDto {
    private String text;

    public TextStoredDataOutDto() {
    }

    public TextStoredDataOutDto(String id, String mime, String text) {
        super(id, mime);
        this.text = text;
    }
}
