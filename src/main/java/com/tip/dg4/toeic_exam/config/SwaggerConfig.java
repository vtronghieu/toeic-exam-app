package com.tip.dg4.toeic_exam.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI createOpenAPI() {
        OpenAPI openAPI = new OpenAPI();

        openAPI.setInfo(this.getInfo());
        openAPI.setServers(this.getServers());

        return openAPI;
    }

    private Info getInfo() {
        Info info = new Info();

        info.setTitle("TExam API Documentation - DG4 TIP");
        info.setDescription("The TExam API documentation for TOEIC applications built by DG4 TIP members");
        info.setTermsOfService("Terms of service");
        info.setContact(this.getContact());
        info.setLicense(this.getLicense());
        info.setVersion("1.0");

        return info;
    }

    private Contact getContact() {
        Contact contact = new Contact();

        contact.setName("Hieu Vo Trong");
        contact.setEmail("vtronghieu@tma.com.vn");

        return contact;
    }

    private License getLicense() {
        License license = new License();

        license.setName("Undefined");
        license.setUrl("Undefined");

        return license;
    }

    private List<Server> getServers() {
        List<Server> servers = new ArrayList<>();

        Server localServer = new Server();
        localServer.setDescription("Local Server");
        localServer.setUrl("http://localhost:8080");
        servers.add(localServer);

        return servers;
    }
}
