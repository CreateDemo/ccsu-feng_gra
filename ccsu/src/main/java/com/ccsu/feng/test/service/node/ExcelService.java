package com.ccsu.feng.test.service.node;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {

    boolean personNodeRelationshipExcel(MultipartFile file);
}
