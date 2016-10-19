/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.dao;

import com.sv.modelos.Ciudad;
import com.sv.modelos.Comite;
import com.sv.modelos.Departamento;
import com.sv.modelos.Empresa;
import com.sv.modelos.Tipousuario;
import com.sv.modelos.Usuario;
import com.sv.modelos.Votacion;
import com.sv.webservices.clientes.ClienteConsultarQuienNoVoto;
import com.sv.webservices.clientes.ClienteConsultarUsuariosEmpresa;
import com.sv.webservices.clientes.ClientePersonasDelComite;
import com.sv.webservices.clientes.ClienteRegistrarVotacion;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author CristianCamilo
 */
public class VotacionDao {

    public int registrarVotacion(Votacion votacion) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ClienteRegistrarVotacion cliente = new ClienteRegistrarVotacion();
        return cliente.crearVotacion(int.class,
                "" + votacion.getIdUsuario().getIdUsuario(),
                "" + votacion.getIdInventario().getIdInventario(),
                "" + votacion.getIdComite().getIdComite(),
                sdf.format(votacion.getFechaCalificacion()),
                votacion.getCalificacion());
    }

    public List<Usuario> ConsultarQuienNoVoto(int idEmpresa) {
        ClienteConsultarQuienNoVoto cliente = new ClienteConsultarQuienNoVoto();
        List<HashMap> datos = cliente.validarQuienNoVoto(List.class, "" + idEmpresa);
        List<Usuario> usuarios = new ArrayList<>();

        for (int i = 0; i < datos.size(); i++) {
            usuarios.add(new Usuario((int) datos.get(i).get("idUsuario"),
                    (String) datos.get(i).get("nombre"),
                    (String) datos.get(i).get("email")));
        }

        return usuarios;
    }

    public int ConsultarQuienVoto(int idEmpresa) {
        ClientePersonasDelComite cliente = new ClientePersonasDelComite();
        int datos = cliente.validarPersonasComite(int.class, "" + idEmpresa);
               
        return datos;
    }

}
