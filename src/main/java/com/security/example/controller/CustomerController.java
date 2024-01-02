package com.security.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CustomerController {
    // El sessionRegistry es un objeto que permite obtener información de las sesiones activas
    // esto lo declaramos en la clase SecurityConfig.java
    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/index")
    public String index(){
        return "Hello World";
    }

    @GetMapping("/index2")
    public String index2(){
        return "Hello World Not Secured";
    }

    @GetMapping("/session")
    public ResponseEntity<?> getDetailsSession(){
        String sessionId = "";
        User userObj = null;
        // Obtenemos la lista de sesiones activas, siempre es una lista de objetos
        List<Object> sessions = sessionRegistry.getAllPrincipals();
        // Recorremos la lista de sesiones activas
        for(Object session: sessions) {
            // si el objeto es de tipo User, entonces obtenemos el id de la sesión y el objeto User
            if(session instanceof User){
                userObj = (User) session;
            }
            // Obtenemos la lista de sesiones activas del usuario
            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(userObj, false);
            // Recorremos la lista de sesiones activas del usuario
            for(SessionInformation sessionInformation: sessionInformations) {
                // Obtenemos el id de la sesión
                sessionId = sessionInformation.getSessionId();
            }
        }

        HashMap<String, Object> response = new HashMap<>();
        response.put("sessionId", sessionId);
        response.put("user", userObj);

        return ResponseEntity.ok(response);
    }
}
