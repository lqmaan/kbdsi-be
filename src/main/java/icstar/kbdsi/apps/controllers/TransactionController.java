package icstar.kbdsi.apps.controllers;

import icstar.kbdsi.apps.models.Category;
import icstar.kbdsi.apps.models.Transaction;
import icstar.kbdsi.apps.models.User;
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
public class TransactionController {

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions (@RequestParam(required = false) String name){
        try{
            List<Transaction> transactions = new ArrayList<Transaction>();

            if(name == null)
                transactions.addAll(transactionRepository.findAll());
//            else
//                categoryRepository.findAllByName(name, PageRequest.of(0, 10, Sort.by("name").ascending()));
            if(transactions.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction){
        try{
            Transaction newTransaction =  new Transaction(transaction.getName(), transaction.getType(), transaction.getCategoryId(), transaction.getAmount(), transaction.getDescription());
            transactionRepository.save(newTransaction);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<Transaction> updateTransactionById(@PathVariable(required = true) Long id, @RequestBody Transaction newTransaction) {
        Optional<Transaction> optTransaction = transactionRepository.findById(id);
        if(optTransaction.isPresent()) {
            Transaction oldTransaction = optTransaction.get();
            oldTransaction.setName(newTransaction.getName());
            oldTransaction.setAmount(newTransaction.getAmount());
            oldTransaction.setCategoryId(newTransaction.getCategoryId());
            oldTransaction.setDescription(newTransaction.getDescription());
            transactionRepository.save(oldTransaction);
            return new ResponseEntity<>(oldTransaction, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable(required = true) Long id) {
        try {
            transactionRepository.deleteById(id);
            return new ResponseEntity<>("Transaction has been deleted",HttpStatus.OK);
        }catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
