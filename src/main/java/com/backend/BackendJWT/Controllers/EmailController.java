package com.backend.BackendJWT.Controllers;

import com.backend.BackendJWT.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> enviarCorreo(
            @RequestParam String para,
            @RequestParam String codigoRecuperacion // Código opcional
    ) {
        if (codigoRecuperacion != null) {
            // Priorizamos el envío del código de recuperación
            emailService.sendRecoveryCodeEmail(para, codigoRecuperacion);
            return ResponseEntity.ok("Código de recuperación enviado exitosamente");
        } else {
            // Manejo de error si no se proporciona la información necesaria
            return ResponseEntity.badRequest().body("Debe proporcionar una dirección de correo electronico y un código de recuperación");
        }
    }
}
