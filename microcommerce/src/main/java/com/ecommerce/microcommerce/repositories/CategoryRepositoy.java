package com.ecommerce.microcommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.microcommerce.entities.Category;

@Repository
public interface CategoryRepositoy extends JpaRepository<Category, String>{

}
