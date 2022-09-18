package Tutorial.misionTIC.seguridad.Repositorios;

import Tutorial.misionTIC.seguridad.Modelos.Permiso;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioPermiso extends MongoRepository<Permiso,String>{
}
