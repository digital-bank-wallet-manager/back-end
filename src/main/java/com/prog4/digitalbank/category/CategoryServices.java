package com.prog4.digitalbank.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServices {
    private CategoryRepository categoryRepository;

    public List<Category> findByType (String type){
       return categoryRepository.findByType(type);
    }

    public int findIdSubCategory(String subCategory){
        return categoryRepository.findLoanOrRepayId(subCategory);
    }
}
