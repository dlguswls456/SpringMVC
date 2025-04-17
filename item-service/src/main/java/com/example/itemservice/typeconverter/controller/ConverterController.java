package com.example.itemservice.typeconverter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.itemservice.typeconverter.type.IpPort;

import lombok.Data;


@Controller
@RequestMapping("/converter")
public class ConverterController {

    @GetMapping("/converter-view")
    public String converterView(Model model) {
        model.addAttribute("number", 10000);
        model.addAttribute("ipPort", new IpPort("127.0.0.11", 8080));
        return "converter/converter-view";
    }

    @GetMapping("/converter/edit")
    public String converterForm(Model model) {
        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        Form form = new Form(ipPort);

        model.addAttribute("form", form);
        return "converter/converter-form";
    }
    
    @PostMapping("/converter/edit")
    public String converterEdit(@ModelAttribute("form") Form form, Model model) {
        IpPort ipPort = form.getIpPort();
        model.addAttribute("ipPort", ipPort);
        return "converter/converter-view";
    }

    @Data
    static class Form {

        private IpPort ipPort;
        
        public Form() {}

        public Form(IpPort ipPort) {
            this.ipPort = ipPort;
        }

    }

}
