package com.prog4.digitalbank.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class CategoryRepository {
    private Connection connection;

    public List<SubCategory> findByType (String type){
        String sql =  "select sub_category.id as sub_category_id ,category.name as category, sub_category.name as sub_category  from sub_category inner join category on category.id = sub_category.category_id where category.type = ?";
        List<SubCategory> categories = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,type);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int subCategoryId = resultSet.getInt("sub_category_id");
                String category = resultSet.getString("category");
                String subCategory = resultSet.getString("sub_category");
                categories.add(new SubCategory(subCategoryId , category,subCategory));

            }
            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findLoanOrRepayId(String type){
        int id = 0;
        String sql = "select id from sub_category where name ilike ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,type);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
               id = resultSet.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
