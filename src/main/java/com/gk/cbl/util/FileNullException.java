package com.gk.cbl.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileNullException implements Converter<String,CommonsMultipartFile> {
    public FileNullException() {
        super();
    }

    @Override
    public CommonsMultipartFile convert(String source) {
        return null;
    }
}
