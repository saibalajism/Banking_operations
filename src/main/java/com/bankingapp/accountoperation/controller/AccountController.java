package com.bankingapp.accountoperation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingapp.accountoperation.accountdto.AccountDto;
import com.bankingapp.accountoperation.entity.Account;
import com.bankingapp.accountoperation.service.accountImpl.AccountServiceImpl;

@RestController

@RequestMapping("/api/accounts")
public class AccountController {
	@Autowired
	private AccountServiceImpl accountService;
	
	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody AccountDto dto){
		return new ResponseEntity<>(accountService.createAccount(dto), HttpStatus.CREATED);
	}

}
