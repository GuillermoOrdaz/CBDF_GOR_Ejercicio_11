package com.upiiz.securitydb.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/facturas")
public class FacturaController {

    @GetMapping("/listar")
    public String listarFacturas(){
        return "Listado de Facturas";
    }

    @GetMapping("/listar/{id}")
    public String listarFacturasId(@PathVariable int id){
        return "Listado de Facturas con id"+id;
    }

    @PostMapping("/crear")
    public String crearFactura(){
        return "Creando Factura";
    }

    @PutMapping("/actualizar/{id}")
    public String actualizarFactura(@PathVariable int id){
        return "Actualizando factura con id: "+id;
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarFactura(@PathVariable int id){
        return "Eliminando una Factura con id "+id;
    }

}
