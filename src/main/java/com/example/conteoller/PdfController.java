package com.example.conteoller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.PdfService;

@RestController
@RequestMapping("/api")
public class PdfController {


    private final PdfService pdfService;
    
    public PdfController(PdfService pdfService) {
    	this.pdfService = pdfService;	
    }

    @GetMapping("/exportPdf")
    public ResponseEntity<ByteArrayResource> exportPdf() throws IOException {
        ByteArrayResource resource = pdfService.generatePdf();

        // set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.pdf");

        // return response entity
        return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
    }
}
