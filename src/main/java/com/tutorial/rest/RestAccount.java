package com.tutorial.rest;

import com.tutorial.dto.DTO;
import com.tutorial.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RestAccount {

    @Autowired
    private AccountService accountService;

    @GetMapping(
            path = "my-accounts",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<DTO.responseAccount> listAccount() {

        for (int i = 0; i < 100; i++) {

            return accountService.getAccounts(); // strees test api publisher
        }
        return accountService.getAccounts();


//        List<DTO.responseAccount> responseAccounts = accountService.getAccounts();

//        return responseAccounts.stream().map(new Function<DTO.responseAccount, DTO.responseAccount>() {
//            @Override
//            public DTO.responseAccount apply(DTO.responseAccount responseAccount) {
//                return RestResponse.object.<DTO.responseAccount>builder()
//                        .data(responseAccount)
//                        .status_code(HttpStatus.SC_OK)
//                        .message("OK")
//                        .errors("")
//                        .build()
//                        .getData();
//            }
//        }).toList();

    }


}
