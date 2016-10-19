/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.webservices.clientes;

import com.sv.clases.ruta;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

/**
 * Jersey REST client generated for REST
 * resource:ConsultarTipoUsuariosRestringidosImpl
 * [/consultarTipoUsuariosRestringidos]<br>
 * USAGE:
 * <pre>
 *        ClienteConsultarTipoUsuariosRestringidos client = new ClienteConsultarTipoUsuariosRestringidos();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Jose
 */
public class ClienteConsultarTipoUsuariosRestringidos {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ruta.consultarRuta();

    public ClienteConsultarTipoUsuariosRestringidos() {
        client = ResteasyClientBuilder.newBuilder().build();
        webTarget = client.target(BASE_URI).path("consultarTipoUsuariosRestringidos");
    }

    public <T> T consultaTipoUsuarios(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
