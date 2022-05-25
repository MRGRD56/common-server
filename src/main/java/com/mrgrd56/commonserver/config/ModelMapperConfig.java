package com.mrgrd56.commonserver.config;

import com.mrgrd56.commonserver.domain.StoredData;
import com.mrgrd56.commonserver.dto.FileStoredDataOutDto;
import com.mrgrd56.commonserver.dto.StoredDataOutDto;
import com.mrgrd56.commonserver.dto.TextStoredDataOutDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.typeMap(StoredData.class, StoredDataOutDto.class)
                .setConverter(context -> {
                    var entity = context.getSource();

                    if (entity.getRawData() != null) {
                        return new FileStoredDataOutDto(
                            entity.getId(),
                                entity.getMime(),
                                entity.getRawData().length,
                                getBaseUri() + "/stored-data/" + entity.getId() + "/download");
                    }

                    return new TextStoredDataOutDto(
                            entity.getId(),
                            entity.getMime(),
                            entity.getTextData());
                });

        return modelMapper;
    }

    private String getBaseUri() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
}
