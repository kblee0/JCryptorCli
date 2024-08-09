package com.home.jcryptor.encryptor.modules;

import com.home.jcryptor.encryptor.Encryptor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
//import java.io.*;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@NoArgsConstructor
public class CsvEncryptor {
    private Encryptor encryptor;

    public void doProc(String srcFileName, String outFileName) throws Exception {
        InputStream inputStream = new BOMInputStream(new FileInputStream(srcFileName));
        CSVParser parser = CSVParser.parse(inputStream, StandardCharsets.UTF_8, CSVFormat.RFC4180);

        Appendable appendable = null;
        if(outFileName == null) new OutputStreamWriter(System.out);
        else new FileWriter(outFileName);

        CSVPrinter csvPrinter = new CSVPrinter(appendable, CSVFormat.RFC4180);
        
        for (CSVRecord record : parser) {
            for (String val : record) {
                if(encryptor != null) {
                    try {
                        val = encryptor.doProc(val);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                csvPrinter.print(val);
            }
            csvPrinter.println();
            csvPrinter.flush();
        }
    }
}
