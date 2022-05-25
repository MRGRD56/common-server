package com.mrgrd56.commonserver.service;

import com.mrgrd56.commonserver.domain.StoredData;
import com.mrgrd56.commonserver.dto.StoredDataOutDto;
import com.mrgrd56.commonserver.exception.BadRequestException;
import com.mrgrd56.commonserver.repository.StoredDataRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class StoredDataService {
    private final ModelMapper modelMapper;
    private final StoredDataRepository storedDataRepository;

    public StoredDataService(
            ModelMapper modelMapper,
            StoredDataRepository storedDataRepository) {
        this.modelMapper = modelMapper;
        this.storedDataRepository = storedDataRepository;
    }

    public List<StoredDataOutDto> getAll() {
        return storedDataRepository.findAll().stream()
                .map(data -> modelMapper.map(data, StoredDataOutDto.class))
                .toList();
    }

    public Page<StoredDataOutDto> getAll(Pageable pageable) {
        return storedDataRepository.findAll(pageable)
                .map(data -> modelMapper.map(data, StoredDataOutDto.class));
    }

    public StoredDataOutDto getById(String id) {
        return storedDataRepository.findById(id)
                .map(data -> modelMapper.map(data, StoredDataOutDto.class))
                .orElseThrow(() -> {
                    return new BadRequestException("Stored data not found");
                });
    }

    public StoredDataOutDto upload(HttpServletRequest request) {
        try {
            var mimeType = request.getContentType();
            var mime = MediaType.parseMediaType(mimeType);
            var mimeCharset = mime.getCharset();

            var body =  request.getInputStream().readAllBytes();
            String textData = null;
            byte[] rawData = null;
            if (mime.getType().equals("text/plain")) {
                textData = new String(body, mimeCharset != null ? mimeCharset : StandardCharsets.UTF_8);
            } else {
                rawData = body;
            }

            var storedData = storedDataRepository.save(new StoredData(mimeType, textData, rawData));
            return modelMapper.map(storedData, StoredDataOutDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StoredData getDownloadable(String id) {
        return storedDataRepository.findByIdAndRawDataNotNull(id)
                .orElseThrow(() -> {
                    return new BadRequestException("Stored data not found or cannot be downloaded");
                });
    }

    public void delete(String id) {
        if (!storedDataRepository.existsById(id)) {
            throw new BadRequestException("Stored data not found");
        }

        storedDataRepository.deleteById(id);
    }
}
