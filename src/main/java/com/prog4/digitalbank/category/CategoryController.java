package com.prog4.digitalbank.category;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryController {
    private CategoryServices categoryServices;
    @GetMapping("/category/{type}")
    public List<SubCategory> findByType (@PathVariable String type){
        return categoryServices.findByType(type);
    }

    @GetMapping("/allCategory")
    public List<Category> findAllCategory() throws SQLException{
        return categoryServices.findAllCategory();
    }
}
