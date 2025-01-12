package org.uy.sdm.pasman.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.uy.sdm.pasman.dto.accountTypes.AccountTypeDto;
import org.uy.sdm.pasman.model.AccountType;
import org.uy.sdm.pasman.repos.AccountTypeRepo;
import org.uy.sdm.pasman.services.AccountTypeService;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {

	private final AccountTypeRepo accountTypeRepo;

	@Override
	public void addAccountType(AccountTypeDto accountTypeDto) {
		AccountType accountType = new AccountType();
		accountType.setName(accountTypeDto.name());
		accountType.setUrl(accountTypeDto.url());
		accountType.setLogo(accountTypeDto.logo());
		accountTypeRepo.save(accountType);
	}

	@Override
	public Collection<AccountTypeDto> getAccountTypes() {
		final Collection<AccountType> accountTypeList = accountTypeRepo.findAll();
		return accountTypeList.stream().map(accountType -> new AccountTypeDto(
			accountType.getName(),
			accountType.getUrl(),
			accountType.getLogo()
		)).toList();
	}

}
