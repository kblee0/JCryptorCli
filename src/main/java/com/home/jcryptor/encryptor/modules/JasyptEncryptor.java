package com.home.jcryptor.encryptor.modules;

import com.home.jcryptor.encryptor.Encryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class JasyptEncryptor extends Encryptor {
    private StandardPBEStringEncryptor encryptor;

    public JasyptEncryptor() {
        init(ENCRYPT_MODE, null);
    }
    public JasyptEncryptor(int procMode, String password) {
        init(procMode, password);
    }

    public void init(int procMode, String password) {
        Security.addProvider(new BouncyCastleProvider());
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setProviderName("BC");
        encryptor.setAlgorithm("PBEWITHSHA256AND128BITAES-CBC-BC");
        if(password != null) {
            this.setPassword(password);
        }
        setProcMode(procMode);
    }

    @Override
    public JasyptEncryptor setPassword(String password) {
        encryptor.setPassword(password);
        return this;
    }

    public String encrypt(String message) {
        return encryptor.encrypt(message);
    }

    public String decrypt(String encryptedMessage) {
        return encryptor.decrypt(encryptedMessage);
    }
}
