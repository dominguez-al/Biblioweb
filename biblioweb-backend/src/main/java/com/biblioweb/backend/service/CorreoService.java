package com.biblioweb.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Servicio para el envío de correos electrónicos.
 * Utiliza JavaMailSender y se ejecuta de forma asíncrona para no bloquear el hilo principal.
 */
@Service // Componente de servicio gestionado por Spring
public class CorreoService {

    @Autowired
    private JavaMailSender mailSender; // Cliente de envío de correos configurado en application.properties

    /**
     * Envía un correo electrónico simple (sin HTML) de manera asíncrona.
     *
     * @param para    Dirección de correo del destinatario
     * @param asunto  Asunto del mensaje
     * @param cuerpo  Texto del mensaje
     */
    @Async // Permite que el envío se ejecute en segundo plano sin bloquear el flujo principal
    public void enviarCorreo(String para, String asunto, String cuerpo) {
        // Crear mensaje de texto plano
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(para);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        // Enviar el mensaje
        mailSender.send(mensaje);
    }
}
