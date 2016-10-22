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
import javax.ws.rs.core.Form;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

/**
 * Jersey REST client generated for REST resource:application%20(2)
 * [correoSeleccionJuguete]<br>
 * USAGE:
 * <pre>
 *        ClienteCorreoInicioSeleccionDeJuguete client = new ClienteCorreoInicioSeleccionDeJuguete();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Jose
 */
public class ClienteCorreoInicioSeleccionDeJuguete {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ruta.consultarRuta();

    public ClienteCorreoInicioSeleccionDeJuguete() {
        client = ResteasyClientBuilder.newBuilder().build();
        webTarget = client.target(BASE_URI).path("correoSeleccionJuguete");
    }

    /**
     * @param responseType Class representing the response
     * @param nombreUsuario query parameter
     * @param nombreEmpresa query parameter
     * @param usuario query parameter
     * @param contrasena query parameter
     * @param logo query parameter
     * @param email query parameter
     * @return response object (instance of responseType class)
     */
    public <T> T correoSeleccionJuguete(Class<T> responseType, String nombreUsuario, String nombreEmpresa, String usuario, String contrasena, String logo, String email) throws ClientErrorException {
        String[] queryParamNames = new String[]{"nombreUsuario", "nombreEmpresa", "usuario", "contrasena", "logo", "email"};
        String[] queryParamValues = new String[]{nombreUsuario, nombreEmpresa, usuario, contrasena, logo, email};
        ;
        javax.ws.rs.core.Form form = getQueryOrFormParams(queryParamNames, queryParamValues);
        javax.ws.rs.core.MultivaluedMap<String, String> map = form.asMap();
        for (java.util.Map.Entry<String, java.util.List<String>> entry : map.entrySet()) {
            java.util.List<String> list = entry.getValue();
            String[] values = list.toArray(new String[list.size()]);
            webTarget = webTarget.queryParam(entry.getKey(), (Object[]) values);
        }
        return webTarget.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(responseType);
    }

    private Form getQueryOrFormParams(String[] paramNames, String[] paramValues) {
        Form form = new javax.ws.rs.core.Form();
        for (int i = 0; i < paramNames.length; i++) {
            if (paramValues[i] != null) {
                form = form.param(paramNames[i], paramValues[i]);
            }
        }
        return form;
    }

    public void close() {
        client.close();
    }
    
}
