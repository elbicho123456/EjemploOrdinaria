package es.ufv.dis.back.final2025.MEG;

public class Usuario {
    private String id;
    private String nombre;
    private String apellidos;
    private String email;
    private Direccion direccion;
    private String nif;
    private MetodoPago metodoPago;

    public static class Direccion {
        private String calle;
        private int numero;
        private String codigoPostal;
        private String ciudad;
        private String pisoLetra;

        public String getCalle() {
            return calle;
        }

        public void setCalle(String calle) {
            this.calle = calle;
        }

        public int getNumero() {
            return numero;
        }

        public void setNumero(int numero) {
            this.numero = numero;
        }

        public String getCodigoPostal() {
            return codigoPostal;
        }

        public void setCodigoPostal(String codigoPostal) {
            this.codigoPostal = codigoPostal;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public String getPisoLetra() {
            return pisoLetra;
        }

        public void setPisoLetra(String pisoLetra) {
            this.pisoLetra = pisoLetra;
        }
        public Direccion() {
            // Constructor por defecto
        }
        public Direccion(String calle, int numero, String codigoPostal, String ciudad, String pisoLetra) {
            this.calle = calle;
            this.numero = numero;
            this.codigoPostal = codigoPostal;
            this.ciudad = ciudad;
            this.pisoLetra = pisoLetra;
        }
    }

    public static class MetodoPago {
        private long numeroTarjeta;
        private String nombreAsociado;

        public long getNumeroTarjeta() {
            return numeroTarjeta;
        }

        public void setNumeroTarjeta(long numeroTarjeta) {
            this.numeroTarjeta = numeroTarjeta;
        }

        public String getNombreAsociado() {
            return nombreAsociado;
        }

        public void setNombreAsociado(String nombreAsociado) {
            this.nombreAsociado = nombreAsociado;
        }
        public MetodoPago() {
            // Constructor por defecto
        }
        public MetodoPago(long numeroTarjeta, String nombreAsociado) {
            this.numeroTarjeta = numeroTarjeta;
            this.nombreAsociado = nombreAsociado;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
    public Usuario() {
        // Constructor por defecto
    }
    public Usuario(String nombre, String id, String apellidos, String email, Direccion direccion, String nif, MetodoPago metodoPago) {
        this.nombre = nombre;
        this.id = id;
        this.apellidos = apellidos;
        this.email = email;
        this.direccion = direccion;
        this.nif = nif;
        this.metodoPago = metodoPago;
    }
}
