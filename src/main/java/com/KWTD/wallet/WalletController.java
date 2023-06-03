package com.KWTD.wallet;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    public WalletServices walletService;

    public WalletController(WalletServices walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/addwallet")
    public String addWallet(@RequestBody Wallet wallet) throws InterruptedException, Exception {
        return walletService.createWallet(wallet, wallet.getMentorNumber());
    }

    @PutMapping("/updatewallet")
    public String updateWallet(@RequestBody Wallet wallet) throws InterruptedException, Exception {
        return walletService.updateWallet(wallet, wallet.getMentorNumber());
    }

    @DeleteMapping("/deletewallet")
    public String deleteWallet(String mentorNumber) throws InterruptedException, Exception {
        return walletService.deleteWallet(mentorNumber);
    }

    @GetMapping("/getwallet")
    public Wallet getWallet(String mentorNumber) throws InterruptedException, Exception {
        return walletService.getWallet(mentorNumber);
    }

}
