package com.example.first.Util;

import com.csvreader.CsvWriter;

import java.io.IOException;

public class CsvWriteUtil {
    public static void writeRecord(CsvWriter csvWriter, String[] args){
        try {
            csvWriter.writeRecord(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
