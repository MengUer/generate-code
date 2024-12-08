package priv.menguer.velocity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.menguer.velocity.config.GenConfig;
import priv.menguer.velocity.constant.TemplateEnum;
import priv.menguer.velocity.entity.TableInfo;
import priv.menguer.velocity.service.DaMengService;
import priv.menguer.velocity.service.GenerateCodeService;
import priv.menguer.velocity.service.MysqlService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author menguer@126.com
 * @version 1.0.0
 * @date 2019年11月8日 下午3:07:49
 * @description
 */
@Service
public class GenerateCodeServiceImpl implements GenerateCodeService {
    @Autowired
    private MysqlService mysqlService;

    @Autowired
    private DaMengService daMengService;

    @Override
    public void execute() throws Exception {
        List<TableInfo> tableInfos = new ArrayList<>();
        if ("mysql".equalsIgnoreCase(GenConfig.databaseType)) {
            tableInfos = mysqlService.getTableInfos();
        } else if ("dameng".equalsIgnoreCase(GenConfig.databaseType)) {
            tableInfos = daMengService.getTableInfos();
        }
        for (TableInfo tableInfo : tableInfos) {
            if (GenConfig.generateEntity) {
                TemplateEnum.ENTITY.generateFile(tableInfo);
            }
            if (GenConfig.generateDao) {
                TemplateEnum.DAO.generateFile(tableInfo);
            }
            if (GenConfig.generateMapper) {
                TemplateEnum.MAPPER.generateFile(tableInfo);
            }
            if (GenConfig.generateService) {
                TemplateEnum.SERVICE.generateFile(tableInfo);
            }
            if (GenConfig.generateServiceImpl) {
                TemplateEnum.SERVICEIMPL.generateFile(tableInfo);
            }
            if (GenConfig.generateController) {
                TemplateEnum.CONTROLLER.generateFile(tableInfo);
            }
        }
        System.out.println("执行完毕。");
    }
}