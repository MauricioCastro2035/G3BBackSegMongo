package Tutorial.misionTIC.seguridad.Repositorios;


import Tutorial.misionTIC.seguridad.Modelos.Permiso;
import Tutorial.misionTIC.seguridad.Modelos.PermisosRoles;
import Tutorial.misionTIC.seguridad.Modelos.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RepositorioPermisosRoles extends MongoRepository<PermisosRoles,String> {

    List<PermisosRoles> findByRolAndPermiso(Rol rol, Permiso permiso);
}
