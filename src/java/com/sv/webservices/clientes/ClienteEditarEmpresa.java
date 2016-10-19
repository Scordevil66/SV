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
 * Jersey REST client generated for REST resource:EditarEmpresasImpl
 * [/editarEmpresas]<br>
 * USAGE:
 * <pre>
 *        ClienteEditarEmpresa client = new ClienteEditarEmpresa();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author CristianCamilo
 */
public class ClienteEditarEmpresa {
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ruta.consultarRuta();

    public ClienteEditarEmpresa() {
        client = ResteasyClientBuilder.newBuilder().build();
        webTarget = client.target(BASE_URI).path("editarEmpresas");
    }

    public <T> T editarEmpresas(Class<T> responseType, String idEmpresas, String nombre, String direccion, String nit, String telefono, String correo, String urlLogo, String urlBanner, String idUsuario, String comite) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (nombre != null) {
            resource = resource.queryParam("nombre", nombre);
        }
        if (direccion != null) {
            resource = resource.queryParam("direccion", direccion);
        }
        if (idUsuario != null) {
            resource = resource.queryParam("idUsuario", idUsuario);
        }
        if (idEmpresas != null) {
            resource = resource.queryParam("idEmpresas", idEmpresas);
        }
        if (urlBanner != null) {
            resource = resource.queryParam("urlBanner", urlBanner);
        }
        if (nit != null) {
            resource = resource.queryParam("nit", nit);
        }
        if (comite != null) {
            resource = resource.queryParam("comite", comite);
        }
        if (telefono != null) {
            resource = resource.queryParam("telefono", telefono);
        }
        if (correo != null) {
            resource = resource.queryParam("correo", correo);
        }
        if (urlLogo != null) {
            resource = resource.queryParam("urlLogo", urlLogo);
        }
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
