package com.bankingapp.accountoperation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingapp.accountoperation.accountdto.AccountDto;
import com.bankingapp.accountoperation.entity.Account;
import com.bankingapp.accountoperation.service.accountImpl.AccountServiceImpl;

@RestController

@RequestMapping("/api/accounts")
public class AccountController {
	@Autowired
	private AccountServiceImpl accountService;
	
	
	// Add account REST API
	@PostMapping("/create")
	public ResponseEntity<Account> createAccount(@RequestBody AccountDto dto){
		return new ResponseEntity<>(accountService.createAccount(dto), HttpStatus.CREATED);
	}
	
	//Get account by ID REST API
	@GetMapping("/{id}")
	public ResponseEntity<Object> getAccountById(@PathVariable("id") Long Id){
		return accountService.getAccountById(Id);
	}
	
	//Get all account list REST API
		@GetMapping
		public List<Account> getAccountList(){
			return accountService.getAccountList();
		}
		
   // Deposit amount
	  @PutMapping("/{id}/deposit")
	  public ResponseEntity<Object> depositAmount(@PathVariable("id") Long Id, @RequestBody Map<String, Object> req) {
		    double amount = Double.parseDouble(req.get("amount").toString()); 
		    Long FromAccId = Long.parseLong(req.get("DebitAccountId").toString()); 		    
		    return accountService.deposit(Id, amount, FromAccId); 
		}

}
