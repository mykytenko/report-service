package com.tests.reportservice.controller;

import com.tests.reportservice.services.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class FileUploadController {

    private static final String TEST_REPORT_PATH = "src/main/resources/test_report.pdf";

    private final PluginManager pluginManager;

    @Autowired
    public FileUploadController(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/upload";
    }

    @GetMapping("/upload")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public Object singleFileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return new ModelAndView("upload", new ModelMap("error", "File is empty"));
        }

        try {
            byte[] uploadedFile = file.getBytes();

            // functionality of loading plugins is in plugins controller, for demo
            // pluginManager.getPluginByName("")

            byte[] generatedReport = Files.readAllBytes(Paths.get(TEST_REPORT_PATH));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            String filename = "report.pdf";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(generatedReport, headers, HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
