package Tutorial.misionTIC.seguridad.Modelos;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@Document()
public class Permiso {
    @Id
    private String _id;
    @NotEmpty(message = "La URL no debe ser nula")
    private String url;
    @NotEmpty(message = "El metodo no debe ser nulo")
    private String metodo;

    public static final String[] allowedMethods = {"POST", "GET", "PUT", "DELETE", "PATCH"};

    public Permiso(String url, String metodo) {
        this.url = url;
        this.metodo = metodo;
    }

    public String get_id() {
        return _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}