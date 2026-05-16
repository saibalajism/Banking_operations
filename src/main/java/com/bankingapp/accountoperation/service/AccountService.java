package com.bankingapp.accountoperation.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bankingapp.accountoperation.accountdto.AccountDto;
import com.bankingapp.accountoperation.entity.Account;

public interface AccountService {
	Account createAccount(AccountDto accountDto);
	
	ResponseEntity<Object> getAccountById(Long accId);
	
	List<Account> getAccountList();

}
