package py.com.una.pol.rest.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import py.com.una.pol.rest.domain.Llamada;

@Repository
@Transactional
public class LlamadaDAO extends BaseDAOImp<Llamada> {

}
