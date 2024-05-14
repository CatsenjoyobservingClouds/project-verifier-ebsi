package verifier.api.backend.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import verifier.api.backend.dto.VerifiableCredential;
import verifier.api.backend.dto.VerificationResult;
import verifier.api.backend.service.VerifierService;

import java.io.*;

@RestController
@RequestMapping("/api")
public class VerifierController {

    private final VerifierService verifierService;

    @Autowired
    public VerifierController(VerifierService verifierService) {
        this.verifierService = verifierService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/verify-vc")
    public ResponseEntity<?> verifyVerifiableCredential(@RequestBody VerifiableCredential vc) {
        try {
            VerificationResult result = verifierService.verifyVerifiableCredential(vc.getDigitalSignature());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}/signature")
    public ResponseEntity<?> getCorrespondingSignature() {
        try (BufferedReader reader = new BufferedReader(new FileReader("test_signature.txt"))){
            return ResponseEntity.ok(reader.readLine());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }
}

