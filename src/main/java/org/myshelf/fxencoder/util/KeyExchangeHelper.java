package org.myshelf.fxencoder.util;

import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.concurrent.atomic.AtomicReference;

public class KeyExchangeHelper {
    private AtomicReference<KeyPair> generated;

    public KeyExchangeHelper() {
        this.generated = new AtomicReference<>(null);
    }

    public synchronized KeyExchangeHelper generateKeypair() {
        KeyPair keyPair = null;
        try {
            // Generate DH KeyPair
            KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDH", "BC");
            generator.initialize(new ECGenParameterSpec("prime256v1"), new SecureRandom());
            keyPair = generator.generateKeyPair();

            // Initialize DH Agreement object
            KeyAgreement agreement = KeyAgreement.getInstance("ECDH", "BC");
            agreement.init(keyPair.getPrivate());
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException e) {
            // There should occur one of these as the functions and algorithms are properly chosen
            e.printStackTrace();
        }
        assert keyPair != null;

        // Wait to receive a input pin

        this.generated.set(keyPair);
        return this;
    }

    public PublicKey getPublicKey() {
        PublicKey result;

        if (this.generated.get() == null) {
            result = generateKeypair().getPublicKey();
        } else {
            result = generated.get().getPublic();
        }

        return result;
    }
}
