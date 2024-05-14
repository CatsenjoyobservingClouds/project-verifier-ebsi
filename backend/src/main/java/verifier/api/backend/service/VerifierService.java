package verifier.api.backend.service;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import verifier.api.backend.dto.VerifiableCredential;
import verifier.api.backend.dto.VerificationResult;


import java.util.Map;

import static verifier.api.backend.mocked_data.TrustedIssuers.trustedIssuers;

@Service
public class VerifierService {

    private final JWTService jwtVerifier;
    @Autowired
    public VerifierService(JWTService jwtVerifier) {
        this.jwtVerifier = jwtVerifier;
    }

    public VerificationResult verifyVerifiableCredential(String signedVc) {
        if (signedVc == null || signedVc.isEmpty()) {
            return new VerificationResult(false, "Required to be signed.");
        }

        try {
            SignedJWT signedJWT = SignedJWT.parse(signedVc);

            String issuer = signedJWT.getJWTClaimsSet().getIssuer();

            if (!trustedIssuers.contains(issuer)) {
                return new VerificationResult(false, "Issuer is not in our database.");
            }

            if (!jwtVerifier.verifyJWT(signedJWT)) {
                return new VerificationResult(false, "Invalid JWT signature.");
            }
            return new VerificationResult(true, "Verified.");
        } catch (Exception e) {
            return new VerificationResult(false, "Unknown error occurred.");
        }
    }
}

