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

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public Account createAccount(AccountDto dto) {
		Account a = new Account();
		a.setAccountHolderName(dto.getAccountHolderName());
		a.setBalance(dto.getBalance());
		a.setAccountID(dto.getAccountID());

		return accountRepo.save(a);
	}

	@Override
	public ResponseEntity<Object> getAccountById(Long accId) {
		Account a = accountRepo.findById(accId).orElse(null);
		if (a == null) {
			tools.jackson.databind.node.ObjectNode errorResponse = mapper.createObjectNode();
			errorResponse.put("message", "Account does not exsist.");
			errorResponse.put("httpStatusCode", 404);
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(accountRepo.findById(accId).get(), HttpStatus.OK);
		}
	}

	@Override
	public List<Account> getAccountList() {
		return accountRepo.findAll();
	}

	@Override
	public ResponseEntity<Object> deposit(Long Id, double amount, Long FromAccId) {
		Account account = accountRepo.findById(Id).orElse(null);
		Account fromAccount = accountRepo.findById(FromAccId).orElse(null);
		if (fromAccount == null) {
			tools.jackson.databind.node.ObjectNode errorResponse = mapper.createObjectNode();
			errorResponse.put("message", "Invalid from account.");
			errorResponse.put("httpStatusCode", 404);
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		} else if (account == null) {
			tools.jackson.databind.node.ObjectNode errorResponse = mapper.createObjectNode();
			errorResponse.put("message", "Account does not exsist.");
			errorResponse.put("httpStatusCode", 404);
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		} else {
			if (fromAccount.getBalance() > amount) {
				tools.jackson.databind.node.ObjectNode successResponse = mapper.createObjectNode();
				double totalAmount = fromAccount.getBalance() + amount;
				account.setBalance(totalAmount);
				fromAccount.setBalance(fromAccount.getBalance() - amount);
				JsonNode accountToNode = mapper.valueToTree(accountRepo.save(account));
				successResponse.set("deposit details", accountToNode);
				JsonNode accountFromNode = mapper.valueToTree(accountRepo.save(fromAccount));
				successResponse.set("debit details", accountFromNode);
				successResponse.put("httpStatusCode", 200);
				return new ResponseEntity<>(successResponse, HttpStatus.OK);
			} else {
				tools.jackson.databind.node.ObjectNode errorResponse = mapper.createObjectNode();
				errorResponse.put("message", "Insufficient Balance");
				errorResponse.put("httpStatusCode", 404);
				return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
			}
		}

	}

}
