package com.luxoft.entity.service;

import com.luxoft.exceptions.admin.NoFindCategoryException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by iivaniv on 03.08.2015.
 */
public interface AdminProductService1 {
    List<String> completeProductCategories(String query) throws DataAccessException;
}
