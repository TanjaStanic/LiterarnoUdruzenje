package com.example.bankms.utils;

import com.example.bankms.BankMsApplication;
import com.example.bankms.domain.Crypto;
import com.example.bankms.service.CryptoService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

@Component
@Log4j2
@Configuration
public class AttributeEncryptor implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private Key key;
    private final Cipher cipher;
    private final CryptoService cryptoService;
    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;
    @Value("${server.ssl.key-password}")
    private String keyPassword;
    @Value("${server.ssl.keyAlias}")
    private String keyAlias;

    public AttributeEncryptor(CryptoService cryptoService) throws Exception {
        cipher = Cipher.getInstance(ALGORITHM);
        this.cryptoService = cryptoService;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        String generatedString = RandomStringUtils.randomAlphanumeric(16);
        byte[] IvParameterVector = generatedString.getBytes();

        KeyStore keystore;
        InputStream inputStream = BankMsApplication.class.getResourceAsStream("/database.jceks");

        try {
            keystore = KeyStore.getInstance("JCEKS");
            keystore.load(inputStream, keyStorePassword.toCharArray());
            key = keystore.getKey(keyAlias, keyPassword.toCharArray());

        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException
                | UnrecoverableKeyException e) {
            log.error("Error while reading key");
        }

        if (attribute == null || attribute.equals("")) {
            return "";
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IvParameterVector));
            Crypto crypto = new Crypto(Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes())), generatedString);
            cryptoService.save(crypto);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            log.error("Failed to encrypt attribute.");
            log.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {

        byte[] IvParameterVector = (cryptoService.findByText(dbData).getIv()).getBytes();

        KeyStore keystore;
        InputStream inputStream = BankMsApplication.class.getResourceAsStream("/database.jceks");

        try {
            keystore = KeyStore.getInstance("JCEKS");
            keystore.load(inputStream, keyStorePassword.toCharArray());
            key = keystore.getKey(keyAlias, keyPassword.toCharArray());

        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException
                | UnrecoverableKeyException e) {
            log.error("Error while reading key");
            log.error(e.getMessage());
        }

        if (dbData.equals("")) {
            return "";
        }

        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IvParameterVector));
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            log.error("Failed to decrypt attribute.");
            log.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
