package com.ccsu.feng.test.service.node.impl;

import com.ccsu.feng.test.entity.vo.PersonNodeRelationshipExcelVO;
import com.ccsu.feng.test.exception.BaseException;
import com.ccsu.feng.test.service.node.ExcelService;
import com.ccsu.feng.test.service.node.IPersonNodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ExcelServiceImp  excel导入，测试接口
 * @Description TODO
 * @Author xiaofeng
 * @Date 2020/4/4 21:46
 * @Version 1.0
 **/
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    IPersonNodeService iPersonNodeService;

    @Override
    public boolean personNodeRelationshipExcel(MultipartFile file) {
        List<PersonNodeRelationshipExcelVO> list = new ArrayList<>();
        //1.得到上传的表
        try {
            Workbook workbook2 = WorkbookFactory.create(file.getInputStream());
            //2、获取test工作表
            Sheet sheet2 = workbook2.getSheet("triples");
            //获取表的总行数
            int num = sheet2.getLastRowNum();
            //总列数
            int col = sheet2.getRow(0).getLastCellNum();

            for (int j = 1; j <num ; j++) {
                Row row1 = sheet2.getRow(j);
                //如果单元格中有数字或者其他格式的数据，则调用setCellType()转换为string类型
                Cell cell1 = row1.getCell(0);
                cell1.setCellType(CellType.STRING);
                //获取表中第i行，第2列的单元格
                Cell cell2 = row1.getCell(1);
                cell2.setCellType(CellType.STRING);
                //excel表的第i行，第3列的单元格
                Cell cell4 = row1.getCell(3);
                cell4.setCellType(CellType.STRING);
                PersonNodeRelationshipExcelVO personNodeRelationshipExcelVO =new PersonNodeRelationshipExcelVO();
                personNodeRelationshipExcelVO.setHead(cell1.getStringCellValue());
                personNodeRelationshipExcelVO.setTail(cell2.getStringCellValue());
                personNodeRelationshipExcelVO.setRelation(cell4.getStringCellValue());
                list.add(personNodeRelationshipExcelVO);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw  new BaseException("文件读取错误");
        } catch (InvalidFormatException e) {
            log.error(e.getMessage());
            throw  new BaseException("create fail!");

        }
        list.forEach(item->{
            iPersonNodeService.addPersonNodeRelationship(item.getRelation(),item.getTail(),item.getHead(),"红楼梦");
        });
        return true;
    }
}
