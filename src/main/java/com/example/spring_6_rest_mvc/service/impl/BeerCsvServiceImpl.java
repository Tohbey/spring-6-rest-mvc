package com.example.spring_6_rest_mvc.service.impl;

import com.example.spring_6_rest_mvc.dto.BeerCSVRecord;
import com.example.spring_6_rest_mvc.service.BeerCsvService;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class BeerCsvServiceImpl implements BeerCsvService {

    @Override
    public List<BeerCSVRecord> convertCSV(File csvFile) {
        try{
            return new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
                    .withType(BeerCSVRecord.class)
                    .build().parse();
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
