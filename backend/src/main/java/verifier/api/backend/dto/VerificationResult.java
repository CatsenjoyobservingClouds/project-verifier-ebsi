package verifier.api.backend.dto;

public class VerificationResult {
    private boolean verified;
    private String message;

    public VerificationResult(boolean verified, String message) {
        this.verified = verified;
        this.message = message;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "VerificationResult{" +
                "verified=" + verified +
                ", message='" + message + '\'' +
                '}';
    }
}
