package com.bankingapp.accountoperation.service;

import com.bankingapp.accountoperation.accountdto.AccountDto;
import com.bankingapp.accountoperation.entity.Account;

public interface AccountService {
	Account createAccount(AccountDto accountDto);

}
