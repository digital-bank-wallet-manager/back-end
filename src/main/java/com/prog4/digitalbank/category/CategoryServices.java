package com.prog4.digitalbank.category;

import com.prog4.digitalbank.CrudOperations.FindAll;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServices {
    private CategoryRepository categoryRepository;
    private FindAll<Category> categoryFindAll;

    public List<SubCategory> findByType (String type){
       return categoryRepository.findByType(type);
    }

    public int findIdSubCategory(String subCategory){
        return categoryRepository.findLoanOrRepayId(subCategory);
    }

    public List<Category> findAllCategory() throws SQLException {
        return categoryFindAll.findAll(Category.class , "");
    }

    public List<Category> findCategoryByType(String type) throws SQLException{
        return categoryRepository.findCategoryByType(type);
    }
}
