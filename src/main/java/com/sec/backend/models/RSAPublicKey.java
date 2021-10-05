package com.sec.backend.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Service
public class RSAPublicKey {

    private String rsaPublicKey;
}
