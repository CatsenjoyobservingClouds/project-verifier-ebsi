package verifier.api.backend.service;

import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.interfaces.RSAPublicKey;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;

@Service
public class JWTService {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JWTService() throws Exception {
        this.privateKey = (RSAPrivateKey) generateRSAKeyPair().getPrivate();
        this.publicKey = (RSAPublicKey) generateRSAKeyPair().getPublic();
    }

    public String generateJWT(String issuer, String subject, long expirationTimeMillis) throws JOSEException {
        // Create JWT claims
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(subject)
                .expirationTime(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .build();

        // Create JWS header
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .build();

        // Create signed JWT
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        // Sign JWT
        JWSSigner signer = new RSASSASigner(privateKey);
        signedJWT.sign(signer);

        // Serialize JWT to compact form
        return signedJWT.serialize();
    }

    public boolean verifyJWT(SignedJWT signedJWT) throws Exception {
        JWSVerifier verifier = new RSASSAVerifier(publicKey);
        return signedJWT.verify(verifier);
    }

    private KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
}

