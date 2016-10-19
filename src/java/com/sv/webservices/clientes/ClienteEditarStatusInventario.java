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
 * Jersey REST client generated for REST resource:EditarStatusJugueteImpl
 * [/editarStatusJuguete]<br>
 * USAGE:
 * <pre>
 *        ClienteEditarStatusInventario client = new ClienteEditarStatusInventario();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Jose
 */
public class ClienteEditarStatusInventario {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ruta.consultarRuta();

    public ClienteEditarStatusInventario() {
        client = ResteasyClientBuilder.newBuilder().build();
        webTarget = client.target(BASE_URI).path("editarStatusJuguete");
    }

    public <T> T editarStatusJuguete(Class<T> responseType, String idinventario) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (idinventario != null) {
            resource = resource.queryParam("idinventario", idinventario);
        }
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
