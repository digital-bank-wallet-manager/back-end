package com.prog4.digitalbank.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryController {
    private CategoryServices categoryServices;
    @GetMapping("/category/{type}")
    public List<Category> findByType (@PathVariable String type){
        return categoryServices.findByType(type);
    }

}
