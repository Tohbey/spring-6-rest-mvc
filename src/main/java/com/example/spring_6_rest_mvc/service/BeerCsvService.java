package com.example.spring_6_rest_mvc.service;

import com.example.spring_6_rest_mvc.dto.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File csvFile);
}
