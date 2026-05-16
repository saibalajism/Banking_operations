package com.bankingapp.accountoperation.service.accountImpl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bankingapp.accountoperation.accountdto.AccountDto;
import com.bankingapp.accountoperation.entity.Account;
import com.bankingapp.accountoperation.repository.AccountRepository;
import com.bankingapp.accountoperation.service.AccountService;

import tools.jackson.databind.ObjectMapper;


@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Override
	public Account createAccount(AccountDto dto) {
		Account a=new Account();
		a.setAccountHolderName(dto.getAccountHolderName());
		a.setBalance(dto.getBalance());
		a.setAccountID(dto.getAccountID());
		
		return accountRepo.save(a);
	}
	
	
	@Override
	public ResponseEntity<Object> getAccountById(Long accId) {
		Account a=accountRepo.findById(accId).orElse(null);		
		if(a==null) {
			tools.jackson.databind.node.ObjectNode errorResponse = mapper.createObjectNode();
			errorResponse.put("message","Account does not exsist.");
			errorResponse.put("httpStatusCode", 404);
			return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);	
		}else {
			return new ResponseEntity<>(accountRepo.findById(accId).get(),HttpStatus.OK);
		}
	}
	
	@Override
	public List<Account> getAccountList(){
		return accountRepo.findAll();
	}

}
