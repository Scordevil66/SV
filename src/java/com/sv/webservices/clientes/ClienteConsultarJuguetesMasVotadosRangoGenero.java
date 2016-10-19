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
 * Jersey REST client generated for REST resource:application%20(17)
 * [consultarJuguetesMasVotadosRangoGenero]<br>
 * USAGE:
 * <pre>
 *        ClienteConsultarJuguetesMasVotadosRangoGenero client = new ClienteConsultarJuguetesMasVotadosRangoGenero();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Jose
 */
public class ClienteConsultarJuguetesMasVotadosRangoGenero {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ruta.consultarRuta();

    public ClienteConsultarJuguetesMasVotadosRangoGenero() {
        client = ResteasyClientBuilder.newBuilder().build();
        webTarget = client.target(BASE_URI).path("consultarJuguetesMasVotadosRangoGenero");
    }

    /**
     * @param responseType Class representing the response
     * @param idEmpresa query parameter
     * @param genero query parameter
     * @param desde query parameter
     * @param hasta query parameter
     * @return response object (instance of responseType class)
     */
    public <T> T consultarJuguetesMasVotadosGenero(Class<T> responseType, String idEmpresa, String genero, String desde, String hasta) throws ClientErrorException {
        String[] queryParamNames = new String[]{"idEmpresa", "genero", "desde", "hasta"};
        String[] queryParamValues = new String[]{idEmpresa, genero, desde, hasta};
        ;
        javax.ws.rs.core.Form form = getQueryOrFormParams(queryParamNames, queryParamValues);
        javax.ws.rs.core.MultivaluedMap<String, String> map = form.asMap();
        for (java.util.Map.Entry<String, java.util.List<String>> entry : map.entrySet()) {
            java.util.List<String> list = entry.getValue();
            String[] values = list.toArray(new String[list.size()]);
            webTarget = webTarget.queryParam(entry.getKey(), (Object[]) values);
        }
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
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
