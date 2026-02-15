package edu.comillas.icai.pista_padel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorSSR {
    @GetMapping("/saludo") public String saludo(
            @RequestParam(name ="nombre", required = false, defaultValue = "Hola mundo") String nombre, Model model)
    {
        model.addAttribute("nombre", nombre);
        return "saludo";

    }
}
