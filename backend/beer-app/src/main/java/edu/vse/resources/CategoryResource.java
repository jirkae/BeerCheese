package edu.vse.resources;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.vse.dtos.Categories;
import edu.vse.dtos.Category;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.CategoryService;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryResource {

    private final CategoryService categoryService;

    @Autowired
    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Category get(@PathVariable int id) {
        return categoryService.getCategory(id).orElseThrow(() -> new NotFoundException("Category not found"));
    }

    @RequestMapping(method = GET)
    public Categories list(@RequestParam(required = false) Optional<Integer> mainCategory) {
        if (mainCategory.isPresent()) {
            return categoryService.listByMainCategory(mainCategory.get());
        } else {
            return categoryService.list();
        }
    }
}
