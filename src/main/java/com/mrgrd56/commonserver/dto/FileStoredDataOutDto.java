package com.mrgrd56.commonserver.dto;

import com.mrgrd56.commonserver.utils.NumberUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileStoredDataOutDto extends StoredDataOutDto {
    private String downloadUrl;
    private long size;

    public FileStoredDataOutDto() {
    }

    public FileStoredDataOutDto(String id, String mime, long size, String downloadUrl) {
        super(id, mime);
        this.size = size;
        this.downloadUrl = downloadUrl;
    }

    public String getSizeHumanReadable() {
        return NumberUtils.humanReadableByteCount(size);
    }
}
