package com.gfg.paymentappproject.WalletService.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WalletResponse {
    private String txId;
    private WalletStatus success;
}
