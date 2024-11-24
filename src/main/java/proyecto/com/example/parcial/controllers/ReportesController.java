package proyecto.com.example.parcial.controllers;

import java.io.IOException;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import jakarta.servlet.http.HttpServletResponse;
import proyecto.com.example.parcial.services.ClienteService;
import proyecto.com.example.parcial.services.ServicioService;
import proyecto.com.example.parcial.services.TrabajadorService;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/reportes")
public class ReportesController {

    private final TrabajadorService trabajadorService;
    private final ServicioService servicioService;
    private final ClienteService clienteService;

    public ReportesController(TrabajadorService trabajadorService, ServicioService servicioService, ClienteService clienteService) {
        this.trabajadorService = trabajadorService;
        this.servicioService = servicioService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String mostrarReportes(Model model) {
        model.addAttribute("trabajadores", trabajadorService.findAll());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("servicios", servicioService.findAll());
        return "reportes";
    }

    @GetMapping("/pdf")
        public void generarPdf(HttpServletResponse response) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=reportes.pdf");
    
            try (PdfWriter writer = new PdfWriter(response.getOutputStream());
                    com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
                    Document document = new Document(pdfDoc)) {
    
                // Encabezado del documento
                document.add(new Paragraph("Reporte General")
                        .setBold().setFontSize(16).setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER));
                document.add(new Paragraph("\n"));
    
                // Reporte de trabajadores
                document.add(new Paragraph("Reporte de Trabajadores").setBold());
                var trabajadores = trabajadorService.findAll();
                if (trabajadores.isEmpty()) {
                    document.add(new Paragraph("No se encontraron registros de trabajadores."));
                } else {
                    trabajadores.forEach(trabajador -> document
                            .add(new Paragraph("ID: " + trabajador.getId() + ", Nombre: " + trabajador.getNombre() +
                                    ", Email: " + trabajador.getEmail())));
                }
    
                document.add(new Paragraph("\n"));
    
                // Reporte de clientes
                document.add(new Paragraph("Reporte de Clientes").setBold());
                var clientes = clienteService.findAll();
                if (clientes.isEmpty()) {
                    document.add(new Paragraph("No se encontraron registros de clientes."));
                } else {
                    clientes.forEach(cliente -> document
                            .add(new Paragraph("ID: " + cliente.getId() + ", Nombre: " + cliente.getNombre() +
                                    ", Email: " + cliente.getCorreo())));
                }
    
                document.add(new Paragraph("\n"));
    
                // Reporte de servicios
                document.add(new Paragraph("Reporte de Servicios").setBold());
                var servicios = servicioService.findAll();
                if (servicios.isEmpty()) {
                    document.add(new Paragraph("No se encontraron registros de servicios."));
                } else {
                    servicios.forEach(servicio -> document
                            .add(new Paragraph("ID: " + servicio.getId() + ", Nombre: " + servicio.getNombre() +
                                    ", Descripción: " + servicio.getDescripcion())));
                }
    
            } catch (IOException e) {
                throw new RuntimeException("Error al generar el PDF", e);
            }
        }
    
}
