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
 * resource:ConsultarUsuariosEmpresaLikeInicioImpl
 * [/consultarUsuariosEmpresaLikeInicio]<br>
 * USAGE:
 * <pre>
 *        ClienteConsultarUsuarioEmpresaLikeInicio client = new ClienteConsultarUsuarioEmpresaLikeInicio();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Gustavo
 */
public class ClienteConsultarUsuarioEmpresaLikeInicio {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ruta.consultarRuta();

    public ClienteConsultarUsuarioEmpresaLikeInicio() {
        client = ResteasyClientBuilder.newBuilder().build();
        webTarget = client.target(BASE_URI).path("consultarUsuariosEmpresaLikeInicio");
    }

    public <T> T consultarUsuariosEmpresaLike(Class<T> responseType, String idEmpresa, String nombre) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (idEmpresa != null) {
            resource = resource.queryParam("idEmpresa", idEmpresa);
        }
        if (nombre != null) {
            resource = resource.queryParam("nombre", nombre);
        }
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
