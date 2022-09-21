package Tutorial.misionTIC.seguridad.Repositorios;

import Tutorial.misionTIC.seguridad.Modelos.Permiso;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RepositorioPermiso extends MongoRepository<Permiso,String>{

    List<Permiso> findByUrlAndMetodo(String url, String metodo);
}
