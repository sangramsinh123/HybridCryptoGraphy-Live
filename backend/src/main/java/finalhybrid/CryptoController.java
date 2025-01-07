package finalhybrid;

import org.springframework.web.bind.annotation.*;

import finalhybrid.AES;
import finalhybrid.RSA;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@RestController
public class CryptoController {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    public CryptoController() throws Exception {
        KeyPair keyPair = RSA.buildKeyPair();
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
    @PostMapping("/encrypt")
    public Map<String, String> encrypt(@RequestBody Map<String, String> request) throws Exception {
        String message = request.get("message");
        String key = request.get("key");
        String algorithm = request.get("algorithm");

        String encryptedMessage;
        if ("AES".equalsIgnoreCase(algorithm)) {
            encryptedMessage = AES.encrypt(message, key);
        } else {
            byte[] encrypted = RSA.encrypt(publicKey, message);
            encryptedMessage = Base64.getEncoder().encodeToString(encrypted);
        }

        return Collections.singletonMap("encryptedMessage", encryptedMessage);
    }

    @PostMapping("/decrypt")
    public Map<String, String> decrypt(@RequestBody Map<String, String> request) throws Exception {
        String encryptedMessage = request.get("encryptedMessage");
        String key = request.get("key");
        String algorithm = request.get("algorithm");

        String decryptedMessage;
        if ("AES".equalsIgnoreCase(algorithm)) {
            decryptedMessage = AES.decrypt(encryptedMessage, key);
        } else {
            byte[] encrypted = Base64.getDecoder().decode(encryptedMessage);
            byte[] decrypted = RSA.decrypt(privateKey, encrypted);
            decryptedMessage = new String(decrypted);
        }

        return Collections.singletonMap("decryptedMessage", decryptedMessage);
    }
}