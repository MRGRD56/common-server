package com.mrgrd56.commonserver.controller;

import com.mrgrd56.commonserver.dto.StoredDataOutDto;
import com.mrgrd56.commonserver.service.StoredDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("stored-data")
public class StoredDataController {
    private final StoredDataService storedDataService;

    public StoredDataController(StoredDataService storedDataService) {
        this.storedDataService = storedDataService;
    }

    @GetMapping("page")
    public Page<StoredDataOutDto> getPage(Pageable pageable) {
        return storedDataService.getAll(pageable);
    }

    @GetMapping
    public List<StoredDataOutDto> getAll() {
        return storedDataService.getAll();
    }

    @GetMapping("{id}")
    public StoredDataOutDto get(@PathVariable("id") String id) {
        return storedDataService.getById(id);
    }

    @PostMapping
    public StoredDataOutDto upload(HttpServletRequest request) {
        return storedDataService.upload(request);
    }

    @GetMapping("{id}/download")
    public ResponseEntity<?> download(@PathVariable("id") String id) {
        var storedData = storedDataService.getDownloadable(id);

        return ResponseEntity.ok()
                .headers(headers -> {
                    headers.setContentLength(storedData.getRawData().length);
                    headers.setContentDisposition(ContentDisposition.attachment().build());
                    headers.setContentType(MediaType.parseMediaType(storedData.getMime()));
                })
                .body(storedData.getRawData());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        storedDataService.delete(id);
    }
}
