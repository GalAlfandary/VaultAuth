package com.example.mobileseminar;

import com.example.vaultauth.VaultAuthSDK;

public class VaultAuthWrapper {
    private static VaultAuthWrapper instance;
    private final VaultAuthSDK sdk;

    private VaultAuthWrapper() {
        sdk = new VaultAuthSDK();
    }

    public static VaultAuthWrapper getInstance() {
        if (instance == null) {
            instance = new VaultAuthWrapper();
        }
        return instance;
    }

    public VaultAuthSDK getSdk() {
        return sdk;
    }
}
