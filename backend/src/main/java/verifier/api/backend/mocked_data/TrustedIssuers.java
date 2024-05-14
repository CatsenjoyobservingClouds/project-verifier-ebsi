package verifier.api.backend.mocked_data;

import java.util.HashSet;
import java.util.Set;

public final class TrustedIssuers {

    private TrustedIssuers() {
        // Private constructor to prevent instantiation
    }

    public static final Set<String> trustedIssuers = new HashSet<>() {{
        add("did:ebsi:1234567890abcdef"); // Trusted issuer DID
    }};
}
