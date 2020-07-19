package app.itetenosuke.domain.bodyParts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.itetenosuke.domain.bodyParts.model.BodyParts;
import app.itetenosuke.domain.bodyParts.repository.BodyPartsDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class BodyPartsService {
  @Autowired
  @Qualifier("BodyPartsDaoNamedJdbcImpl")
  private BodyPartsDao bodyPartsDao;

  public List<BodyParts> getBodyPartsList(Long userId) {
    return bodyPartsDao.getBodyPartsList(userId);
  }

  public boolean addBodyParts(BodyParts bodyParts) {
    return bodyPartsDao.addBodyParts(bodyParts);
  }

  public boolean deleteBodyParts(BodyParts bodyParts) {
    return bodyPartsDao.deleteBodyParts(bodyParts);
  }

  public boolean editBodyParts(BodyParts bodyParts) {
    return bodyPartsDao.editBodyParts(bodyParts);
  }
}
