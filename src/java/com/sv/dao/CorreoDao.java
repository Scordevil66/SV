/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.dao;

import com.sv.modelos.Inventario;
import com.sv.modelos.Pedido;
import com.sv.modelos.Usuario;
import com.sv.webservices.clientes.ClienteCorreoConfirmacionSeleccionJuguete;
import com.sv.webservices.clientes.ClienteCorreoCreacionAdministrador;
import com.sv.webservices.clientes.ClienteCorreoInicioSeleccionDeJuguete;
import com.sv.webservices.clientes.ClienteCorreoVotacion;

/**
 *
 * @author CristianCamilo
 */
public class CorreoDao {

    public int EnviarCorreoCreacionAdministrador(Usuario usuario) {
        ClienteCorreoCreacionAdministrador cliente = new ClienteCorreoCreacionAdministrador();
        return cliente.correoCreacionAdministrador(int.class, usuario.getNombre(), usuario.getIdEmpresa().getNombre(), usuario.getUsuario(), usuario.getContrasena(), usuario.getEmail());
    }

    public int EnviarCorreoVotacion(Usuario usuario) {
        ClienteCorreoVotacion cliente = new ClienteCorreoVotacion();
        return cliente.correoVotacion(int.class, usuario.getNombre(), usuario.getUsuario(), usuario.getContrasena(), usuario.getIdEmpresa().getNombre(), usuario.getEmail());
    }

    public int EnviarConfirmacionSeleccion(Usuario usuario, Pedido pedido, Inventario inventario) {
        ClienteCorreoConfirmacionSeleccionJuguete cliente = new ClienteCorreoConfirmacionSeleccionJuguete();
        return cliente.correoConfirmacionSeleccionJuguete(int.class, usuario.getNombre(), pedido.getNombreHijo(), inventario.getCodigo(), pedido.getTicket(), inventario.getNombre(), usuario.getUsuario(), usuario.getEmail());
    }

    public int EnviarCorreoInicioSeleccionDeJuguete(Usuario usuario) {
        ClienteCorreoInicioSeleccionDeJuguete cliente = new ClienteCorreoInicioSeleccionDeJuguete();
        return cliente.correoSeleccionJuguete(int.class, usuario.getNombre(), usuario.getIdEmpresa().getNombre(), usuario.getUsuario(), usuario.getContrasena(), "", usuario.getEmail());
    }

}
