package verifier.api.backend.dto;

import java.util.Map;

public class VerifiableCredential {
    private String issuer;
    private Map<String, Object> subject;
    private String digitalSignature;

    public VerifiableCredential(String issuer, Map<String, Object> subject, String digitalSignature) {
        this.issuer = issuer;
        this.subject = subject;
        this.digitalSignature = digitalSignature;
    }

    // Getters and setters
    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Map<String, Object> getSubject() {
        return subject;
    }

    public void setSubject(Map<String, Object> subject) {
        this.subject = subject;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    @Override
    public String toString() {
        return "VerifiableCredential{" +
                "issuer='" + issuer + '\'' +
                ", claims=" + subject +
                ", digitalSignature='" + digitalSignature + '\'' +
                '}';
    }
}
