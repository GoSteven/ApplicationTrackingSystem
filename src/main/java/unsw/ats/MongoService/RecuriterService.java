package unsw.ats.MongoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.ats.entities.Recuriter;
import unsw.ats.repository.RecuriterRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: yousilin
 * Date: 14/05/12
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RecuriterService {
    @Autowired
    private RecuriterRepository recuriterRepository;

    public Recuriter create(Recuriter recuriter) {
        recuriter.setUserId(UUID.randomUUID().toString());
        return recuriterRepository.save(recuriter);
    }

    public List<Recuriter> readAll() {
        return recuriterRepository.findAll();
    }
}
