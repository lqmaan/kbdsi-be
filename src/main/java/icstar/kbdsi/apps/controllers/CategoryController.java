package icstar.kbdsi.apps.controllers;

import icstar.kbdsi.apps.models.Category;
import icstar.kbdsi.apps.models.User;
import icstar.kbdsi.apps.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepo;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategory (@RequestParam(required = false) String name){
        try{
            List<Category> categories = new ArrayList<>();

            if(name == null)
                categories.addAll(categoryRepo.findAll());
//            else
//                categoryRepository.findAllByName(name, PageRequest.of(0, 10, Sort.by("name").ascending()));
            if(categories.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        try{
            Category newCategory =  new Category(category.getCategoryName());
            categoryRepo.save(newCategory);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(required = true) Long id) {
        try {
            categoryRepo.deleteById(id);
            return new ResponseEntity<>("Category has been deleted",HttpStatus.OK);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> editCategory(@PathVariable(required = true) Long id, @RequestBody Category newCategory) {
        Optional<Category> optCategory = categoryRepo.findById(id);
        if(optCategory.isPresent()) {
            Category oldCategory = optCategory.get();
            oldCategory.setCategoryName(newCategory.getCategoryName());
            return new ResponseEntity<>(categoryRepo.save(oldCategory),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
