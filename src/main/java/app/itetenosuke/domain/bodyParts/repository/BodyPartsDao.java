package app.itetenosuke.domain.bodyParts.repository;

import java.util.List;

import app.itetenosuke.domain.bodyParts.model.BodyParts;

public interface BodyPartsDao {
	public List<BodyParts> getBodyPartsList(Long userId);
	public boolean addBodyParts(BodyParts bodyparts);
	public boolean deleteBodyParts(BodyParts bodyparts);
	public boolean editBodyParts(BodyParts bodyparts);
}
