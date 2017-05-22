package cl.nexo.manager.access.workflow;

import java.util.ArrayList;

import cl.nexo.manager.obj.proyecto.ObjEstudio;

public interface AccessWorkflow15 {

	public ArrayList<ObjEstudio> getListEstudioByUser(int id, String lang);
	
	public ArrayList<ObjEstudio> getListEstudioByUserKick(int id, String lang);
	
	public ArrayList<ObjEstudio> getListEstudioByUserAsig(int id, String lang);
	
	public ArrayList<ObjEstudio> getListEstudioByUserRevFondos(int id, String lang);

	public ArrayList<ObjEstudio> getListEstudioByUserResAsig(int idUser, int User, String lang);
	
	public ArrayList<ObjEstudio> getListEstudioByUserResAsigKick(int idUser, int User, String lang);

	public ArrayList<ObjEstudio> getListEstudioByUserResAsigPersonal(int idUser, int User, String lang);
	
	public ArrayList<ObjEstudio> getListEstudioByUserResRevFondos(int idUser, int User, String lang);

	

	public ArrayList<ObjEstudio> getListEstudioByUserActivacion(int id_user,String lang);

	public ArrayList<ObjEstudio> getListEstudioByUserResActivacion(int idUser,int User, String lang);

}
