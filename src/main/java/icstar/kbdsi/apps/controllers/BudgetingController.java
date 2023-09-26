package icstar.kbdsi.apps.controllers;

import icstar.kbdsi.apps.models.Budgeting;
import icstar.kbdsi.apps.models.Transaction;
import icstar.kbdsi.apps.repository.BudgettingRepository;
import icstar.kbdsi.apps.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BudgetingController {

    @Autowired
    BudgettingRepository budgettingRepository;

    @GetMapping("/budget")
    public ResponseEntity<List<Budgeting>> getAllBudgetting (@RequestParam(required = false) String name){
        try{
            List<Budgeting> budgetings = new ArrayList<Budgeting>();

            if(name == null)
                budgetings.addAll(budgettingRepository.findAll());
//            else
//                categoryRepository.findAllByName(name, PageRequest.of(0, 10, Sort.by("name").ascending()));
            if(budgetings.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(budgetings, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/budget")
    public ResponseEntity<Budgeting> createBudgeting(@RequestBody Budgeting budgeting){
        try{
            Budgeting newBudgeting =  new Budgeting(budgeting.getName(), budgeting.getType(), budgeting.getCategoryId(), budgeting.getAmount(), budgeting.getDescription());
            budgettingRepository.save(newBudgeting);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/budget/{id}")
    public ResponseEntity<Budgeting> updateBudgetById(@PathVariable(required = true) Long id, @RequestBody Budgeting newBudgeting) {
        Optional<Budgeting> optBudgeting = budgettingRepository.findById(id);
        if(optBudgeting.isPresent()) {
            Budgeting oldBudgeting = optBudgeting.get();
            oldBudgeting.setName(newBudgeting.getName());
            oldBudgeting.setAmount(newBudgeting.getAmount());
            oldBudgeting.setCategoryId(newBudgeting.getCategoryId());
            oldBudgeting.setDescription(newBudgeting.getDescription());
            budgettingRepository.save(oldBudgeting);
            return new ResponseEntity<>(oldBudgeting, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/budget/{id}")
    public ResponseEntity<String> deleteBudgeting(@PathVariable(required = true) Long id) {
        try {
            budgettingRepository.deleteById(id);
            return new ResponseEntity<>("Budget has been deleted",HttpStatus.OK);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
