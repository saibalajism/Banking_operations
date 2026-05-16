package com.bankingapp.accountoperation.service.accountImpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankingapp.accountoperation.accountdto.AccountDto;
import com.bankingapp.accountoperation.entity.Account;
import com.bankingapp.accountoperation.repository.AccountRepository;
import com.bankingapp.accountoperation.service.AccountService;


@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepo;
	
	@Override
	public Account createAccount(AccountDto dto) {
		Account a=new Account();
		a.setAccountHolderName(dto.getAccountHolderName());
		a.setBalance(dto.getBalance());
		a.setAccountID(dto.getAccountID());
		
		return accountRepo.save(a);
	}

}
