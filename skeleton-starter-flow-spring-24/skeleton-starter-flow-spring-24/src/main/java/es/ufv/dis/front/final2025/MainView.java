package es.ufv.dis.front.final2025;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private final UsuarioService usuarioService = new UsuarioService();
    private final Grid<Usuario> grid = new Grid<>(Usuario.class, false);

    public MainView() {
        setSizeFull();
        configurarGrid();

        // Botón para nuevo usuario
        Button nuevoUsuarioBtn = new Button("+ Añadir usuario");
        nuevoUsuarioBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        nuevoUsuarioBtn.addClickListener(e -> abrirDialogoUsuario(crearUsuarioEnBlanco(), true));
        add(nuevoUsuarioBtn);

        List<Usuario> usuarios = usuarioService.obtenerUsuarios();
        grid.setItems(usuarios);

        grid.addItemDoubleClickListener(event -> abrirDialogoUsuario(event.getItem(), false));
        add(grid);
    }

    private void configurarGrid() {
        grid.addColumn(Usuario::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Usuario::getApellidos).setHeader("Apellidos").setAutoWidth(true);
        grid.addColumn(Usuario::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(Usuario::getNif).setHeader("NIF").setAutoWidth(true);

        grid.addComponentColumn(usuario -> {
            Button editarBtn = new Button("Editar");
            editarBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            editarBtn.addClickListener(e -> abrirDialogoUsuario(usuario, false));
            return editarBtn;
        }).setHeader("Acciones");

        grid.setAllRowsVisible(true);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private Usuario crearUsuarioEnBlanco() {
        Usuario nuevo = new Usuario();
        nuevo.setDireccion(new Usuario.Direccion());
        nuevo.setMetodoPago(new Usuario.MetodoPago());
        return nuevo;
    }

    private void abrirDialogoUsuario(Usuario usuario) {
        abrirDialogoUsuario(usuario, false);
    }

    private void abrirDialogoUsuario(Usuario usuario, boolean esNuevo) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(esNuevo ? "➕ Nuevo usuario" : "✏️ Editando usuario");

        TextField nombreField = new TextField("Nombre");
        nombreField.setValue(usuario.getNombre() != null ? usuario.getNombre() : "");

        TextField apellidosField = new TextField("Apellidos");
        apellidosField.setValue(usuario.getApellidos() != null ? usuario.getApellidos() : "");

        TextField emailField = new TextField("Email");
        emailField.setValue(usuario.getEmail() != null ? usuario.getEmail() : "");

        TextField nifField = new TextField("NIF");
        nifField.setValue(usuario.getNif() != null ? usuario.getNif() : "");

        TextField calleField = new TextField("Calle");
        calleField.setValue(usuario.getDireccion() != null && usuario.getDireccion().getCalle() != null
                ? usuario.getDireccion().getCalle() : "");

        NumberField numeroField = new NumberField("Número");
        numeroField.setValue(usuario.getDireccion() != null
                ? (double) usuario.getDireccion().getNumero() : 0.0);

        TextField pisoLetraField = new TextField("Piso letra");
        pisoLetraField.setValue(usuario.getDireccion() != null && usuario.getDireccion().getPisoLetra() != null
                ? usuario.getDireccion().getPisoLetra() : "");

        TextField ciudadField = new TextField("Ciudad");
        ciudadField.setValue(usuario.getDireccion() != null && usuario.getDireccion().getCiudad() != null
                ? usuario.getDireccion().getCiudad() : "");

        TextField codigoPostalField = new TextField("Código postal");
        codigoPostalField.setValue(usuario.getDireccion() != null && usuario.getDireccion().getCodigoPostal() != null
                ? usuario.getDireccion().getCodigoPostal() : "");

        TextField tarjetaField = new TextField("Número de tarjeta");
        tarjetaField.setValue((usuario.getMetodoPago() != null && usuario.getMetodoPago().getNumeroTarjeta() > 0)
                ? String.valueOf(usuario.getMetodoPago().getNumeroTarjeta()) : "");

        TextField nombreAsociadoField = new TextField("Nombre asociado");
        nombreAsociadoField.setValue(usuario.getMetodoPago() != null && usuario.getMetodoPago().getNombreAsociado() != null
                ? usuario.getMetodoPago().getNombreAsociado() : "");

        VerticalLayout formulario = new VerticalLayout(
                nombreField, apellidosField, emailField, nifField,
                calleField, numeroField, pisoLetraField, ciudadField, codigoPostalField,
                tarjetaField, nombreAsociadoField
        );

        Button guardarBtn = new Button("Guardar", event -> {
            System.out.println("GUARDAR CLICADO");

            try {
                usuario.setNombre(nombreField.getValue());
                usuario.setApellidos(apellidosField.getValue());
                usuario.setEmail(emailField.getValue());
                usuario.setNif(nifField.getValue());

                var direccion = usuario.getDireccion();
                direccion.setCalle(calleField.getValue());
                direccion.setNumero(numeroField.getValue().intValue());
                direccion.setPisoLetra(pisoLetraField.getValue());
                direccion.setCiudad(ciudadField.getValue());
                direccion.setCodigoPostal(codigoPostalField.getValue());

                var metodoPago = usuario.getMetodoPago();
                String tarjetaValor = tarjetaField.getValue();
                if (tarjetaValor != null && !tarjetaValor.trim().isEmpty()) {
                    metodoPago.setNumeroTarjeta(Long.parseLong(tarjetaValor.trim()));
                } else {
                    Notification.show("El número de tarjeta no puede estar vacío", 3000, Notification.Position.TOP_CENTER);
                    return;
                }

                metodoPago.setNombreAsociado(nombreAsociadoField.getValue());

                if (esNuevo) {
                    usuarioService.crearUsuario(usuario);
                    System.out.println("POST enviado");
                } else {
                    usuarioService.actualizarUsuario(usuario);
                    System.out.println("PUT enviado");
                }

                List<Usuario> usuariosActualizados = usuarioService.obtenerUsuarios();
                grid.setItems(usuariosActualizados);

                Notification.show("Usuario " + (esNuevo ? "creado" : "actualizado"), 3000, Notification.Position.TOP_CENTER);
                dialog.close();

            } catch (Exception e) {
                System.out.println("❌ ERROR EN GUARDAR:");
                e.printStackTrace();
                Notification.show("Error al guardar usuario", 3000, Notification.Position.TOP_CENTER);
            }
        });

        Button cancelarBtn = new Button("Cancelar", e -> dialog.close());
        cancelarBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.getFooter().add(guardarBtn, cancelarBtn);
        dialog.add(formulario);
        dialog.open();
    }
}
