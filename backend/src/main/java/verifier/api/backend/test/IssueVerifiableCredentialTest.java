package verifier.api.backend.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import verifier.api.backend.dto.VerifiableCredential;
import verifier.api.backend.service.JWTService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static verifier.api.backend.mocked_data.TrustedIssuers.trustedIssuers;

@Service
public class IssueVerifiableCredentialTest {
    private final JWTService jwtVerifier;
    @Autowired
    public IssueVerifiableCredentialTest(JWTService jwtVerifier) {
        this.jwtVerifier = jwtVerifier;
    }

    private VerifiableCredential issueVerifiableCredential(String holderDid) throws Exception {
        String issuerDid = trustedIssuers.iterator().next();
        Map<String, Object> claims = Map.of(
                "id", holderDid,
                "degree", Map.of(
                        "name", "Bachelor of Science",
                        "university", "Example University"
                )
        );
        String digitalSignature = jwtVerifier.generateJWT(issuerDid, claims.toString(), 1000*3600*24); // Simulated digital signature
        return new VerifiableCredential(trustedIssuers.iterator().next(), claims, digitalSignature);
    }

    public static void main(String[] args) throws Exception {
        IssueVerifiableCredentialTest issueVerifiableCredentialTest = new IssueVerifiableCredentialTest(new JWTService());
        VerifiableCredential verifiableCredential = issueVerifiableCredentialTest.issueVerifiableCredential("did:dock:5GL3xbkr3vfs4qJ94YUHwpVVsPSSAyvJcaFHzlwNb5zrSPGi");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test_signature.txt"))){
            writer.write(verifiableCredential.getDigitalSignature());
            System.out.println("Content has been written to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
