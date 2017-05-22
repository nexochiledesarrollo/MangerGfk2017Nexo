package cl.nexo.manager.imp.leer.excel;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import cl.nexo.manager.access.LeerCargaSam.AccessLeerCargaSam;
import cl.nexo.manager.access.cliente.AccessCliente;
import cl.nexo.manager.access.combo.box.AccessComboBox;
import cl.nexo.manager.access.general.tools.AccessGeneralTools;
import cl.nexo.manager.access.login.AccessPerfil;
import cl.nexo.manager.access.login.LoginAccess;
import cl.nexo.manager.access.menu.AccessMenu;
import cl.nexo.manager.access.proyecto.AccessCotizacion;
import cl.nexo.manager.access.proyecto.AccessEstudio;
import cl.nexo.manager.obj.login.ObjLoginUser;
import cl.nexo.manager.obj.login.ObjPerfilLogin;
import cl.nexo.manager.obj.menu.ObjMenuManager;
import cl.nexo.manager.obj.proyecto.ObjEstudio;
import cl.nexo.manager.obj.proyecto.ObjEstudioDetalle;
import cl.nexo.manager.obj.proyecto.ObjEstudioProducto;
import cl.nexo.manager.obj.proyecto.ObjResultCreaCotOp;


public class LeerCargaSam implements AccessLeerCargaSam {
	private static final Logger logger = Logger.getLogger(LeerCargaSam.class);
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
	DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
	DateFormat format3 = new SimpleDateFormat("dd-MM-yyyy");
	
	
	boolean antiguo = false;
	
	
	@Override
	public void leerArchivoRespuestas(String archivo){
		ArrayList<String[]> Lista_Datos_Celda = new ArrayList<>();
		
		
		 logger.info("1/////////////, "+ archivo);
		 logger.info("2/////////////, "+ Lista_Datos_Celda);
		 
		if (archivo.contains(".xlsx")) {
			 
			   GENERAR_XLSX(archivo, Lista_Datos_Celda);
			 
			   logger.info("Ingresa excel .xlsx, "+ archivo);
			   
			   antiguo = false;
		 
		  } else if (archivo.contains(".xls")) {
		 
			   GENERAR_XLS(archivo, Lista_Datos_Celda);
			   
			   logger.info("Ingresa excel .xls, "+ archivo);
			   
			   antiguo = true;
		 
		  }

		logger.info("Ingresa excel .xls LUIS, "+ Lista_Datos_Celda);
		
		//this.setSamEstudios(Lista_Datos_Celda);
	}
	
	private void GENERAR_XLSX(String Nombre_Archivo, List Lista_Datos_Celda) {
		 
		  try {
		 
		   /**
		 
		    * Crea una nueva instancia de la clase FileInputStream
		 
		    */
		 
		   FileInputStream fileInputStream = new FileInputStream(Nombre_Archivo);
		 
		   /**
		 
		    * Crea una nueva instancia de la clase XSSFWorkBook
		 
		    */
		 
		   XSSFWorkbook Libro_trabajo = new XSSFWorkbook(fileInputStream);
		 
		   XSSFSheet Hoja_hssf = Libro_trabajo.getSheetAt(0);
		 
		   /**
		 
		    * Iterar las filas y las celdas de la hoja de cálculo para obtener
		 
		    * toda la data.
		 
		    */
		 
		   Iterator Iterador_de_Fila = Hoja_hssf.rowIterator();
		   ArrayList<ArrayList<String>> Lista_Estudios = new ArrayList<>();
		   while (Iterador_de_Fila.hasNext()) {
		 
			    XSSFRow Fila_hssf = (XSSFRow) Iterador_de_Fila.next();
			    Iterator iterador = Fila_hssf.cellIterator();
			    List Lista_celda_temporal = new ArrayList();
			    
			    ArrayList<String> Lista_Datos = new ArrayList<>();
			   
			    
			    int i=0;
			 
				    while (iterador.hasNext()) {	    	
				         XSSFCell Celda_hssf = (XSSFCell) iterador.next();
					     Lista_celda_temporal.add(Celda_hssf);
					     Lista_Datos.add(Celda_hssf.toString());  
				    }
				    
				    Lista_Estudios.add(Lista_Datos);

			    
			    logger.info("Ingresa setSamEstudios array ---- /////: " + Lista_Datos );
			    Lista_Datos_Celda.add(Lista_celda_temporal);
		 
		   }
		   
		   
		   this.setSamEstudios(Lista_Estudios);
		   
		   
		 
		  } catch (Exception e) {
		 
		   e.printStackTrace();
		 
		  }
		 
		 }
		 
		 private void GENERAR_XLS(String Nombre_Archivo, List Lista_Datos_Celda) {
		 
		  try {
		 
		   /**
		 
		    * Crea una nueva instancia de la clase FileInputStream
		 
		    */
		 
		   FileInputStream fileInputStream = new FileInputStream(
		 
		     Nombre_Archivo);
		 
		   /**
		 	
		    * Crea una nueva instancia de la clase POIFSFileSystem
		 
		    */
		 
		   POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
		 
		   /**
		 
		    * Crea una nueva instancia de la clase HSSFWorkBook
		 
		    */
		 
		   HSSFWorkbook Libro_trabajo = new HSSFWorkbook(fsFileSystem);
		 
		   HSSFSheet Hoja_hssf = Libro_trabajo.getSheetAt(0);
		 
		   /**
		 
		    * Iterar las filas y las celdas de la hoja de cálculo para obtener
		 
		    * toda la data.
		 
		    */
		 
		   Iterator Iterador_de_Fila = Hoja_hssf.rowIterator();
		 
		   while (Iterador_de_Fila.hasNext()) {
		 
		    HSSFRow Fila_hssf = (HSSFRow) Iterador_de_Fila.next();
		 
		    Iterator iterador = Fila_hssf.cellIterator();
		 
		    List Lista_celda_temporal = new ArrayList();
		 
		    while (iterador.hasNext()) {
		 
		     HSSFCell Celda_hssf = (HSSFCell) iterador.next();
		 
		     Lista_celda_temporal.add(Celda_hssf);
		 
		    }
		     
		    logger.info("Ingresa setSamEstudios array000: " + Lista_celda_temporal );
		    
		    Lista_Datos_Celda.add(Lista_celda_temporal);
		 
		   }
		 
		  } catch (Exception e) {
		 
		   e.printStackTrace();
		 
		  }
		 
		 }
		 
		 private void setSamEstudios(ArrayList<ArrayList<String>> arrayDatosExcel){
			 ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
			 AccessCliente cliente = (AccessCliente) context.getBean("AccessCliente");
			 AccessEstudio estudio = (AccessEstudio) context.getBean("AccessEstudio");
			 AccessCotizacion coti = (AccessCotizacion) context.getBean("AccessCotizacion");
			 AccessComboBox combo = (AccessComboBox) context.getBean("AccessComboBox");
			 SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
             SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
             
             LoginAccess logins = (LoginAccess) context.getBean("LoginAccess");
             SecurityContext securityContext = SecurityContextHolder.getContext();
     	     Authentication authentication = securityContext.getAuthentication();
     		 ObjLoginUser user = logins.getUserByLogin(authentication.getName()); 
     	   
     	  

			
			 int r = 0;
			 
			 logger.info("Ingresa setSamEstudios array111: "+arrayDatosExcel.size() );
			 
			 try {
				 
				 for ( int factor = 1; factor < arrayDatosExcel.size() ; factor ++ ) {
					 //logger.info("VALOERES0: " + arrayDatosExcel.get(factor) );
					 
					 ArrayList<String> arrayDatosExcelDetalle = arrayDatosExcel.get(factor);
					 
					 //for ( int factor1 = 0; factor1 < arrayDatosExcelDetalle.size() ; factor1 ++ ) {
						 
		
						 logger.info("VALOERES0: &&&&&&&&&&&   INSERTANDO"  );
						 
						 
						    ObjEstudio est = new ObjEstudio();
				    		ObjEstudioDetalle det = new ObjEstudioDetalle();
				    		ObjEstudioProducto prod = new ObjEstudioProducto();
				    		
//				    		est.setCod_sam(arrayDatosExcelDetalle.get(1));
//				    		est.setTipo_sam(arrayDatosExcelDetalle.get(2));
//				    		est.setNombre_operacion(arrayDatosExcelDetalle.get(3));
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final1"  );
//			    		    est.setId_cliente(cliente.getIdClienteByName(arrayDatosExcelDetalle.get(4)));
//			    		    logger.info("VALOERES0: &&&&&&&&&&&   final2"  );
//				    		est.setCola_operacion(1);
//			    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final21" + arrayDatosExcelDetalle.get(7)  );
//				    						    		
//				    		
//				    		if(arrayDatosExcelDetalle.get(7) != null){
//				    			
//				    			Date date = formatter.parse(arrayDatosExcelDetalle.get(7));
//								String aux_dateini = formatter1.format(date);
//				    			est.setFcreacion_proyectom(aux_dateini);
//				    		
//				    		}
//				    		
//				    		logger.info("VALOERES0: &&&&&&&&&&&   final211" + est.getFcreacion_proyectom()  );
//				    		
//				    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final3" + arrayDatosExcelDetalle.get(8) );
//				    		
//				    		
//				    		est.setScreacion_proyectom(3); //Creacion automatica por workflow
//				    		
//				    		if(arrayDatosExcelDetalle.get(8) != null){
//				    			
//				    			Date date1 = formatter.parse(arrayDatosExcelDetalle.get(8));
//				    			String aux_dateE =  formatter1.format(date1);
//				    			det.setDate_prob_entre_est_op(aux_dateE);
//				    		}
//				    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final4" + arrayDatosExcelDetalle.get(9)  );
//				    		
//				    		
//				    		if(arrayDatosExcelDetalle.get(9) != ""){
//				    			Date date2 = formatter.parse(arrayDatosExcelDetalle.get(9));
//				    			String aux_dateC =  formatter1.format(date2);
//				    			det.setDdate_pres_clie_op(aux_dateC);
//				    		}
//				    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final5"  );
//				    		
//				    		if(arrayDatosExcelDetalle.get(10) != ""){
//				    			Date date3 = formatter.parse(arrayDatosExcelDetalle.get(10));
//				    			String aux_dateEn =   formatter1.format(date3);
//				    			est.setFcom_entrega(aux_dateEn);
//				    		}
//				    		
//				    	
//				    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final7"  );
//				    		
//				    		
//				    		
//				    		if(arrayDatosExcelDetalle.get(11) != null){
//				    			Date date4 = formatter.parse(arrayDatosExcelDetalle.get(11));
//				    			String aux_dateEn2 =   formatter1.format(date4);
//				    			est.setFcom_ini_campo(aux_dateEn2);
//				    		}
//				    		
//				    		
//				    		
//				    		
//				    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final9"  );
//				    		
//
//				    		if(arrayDatosExcelDetalle.get(23) != null){
//				    			//Producto
//				    			String[] numerosComoArray = arrayDatosExcelDetalle.get(23).split(" ");
//				    			prod.setId_producto(Integer.parseInt(numerosComoArray[0]));
//				    			prod.setStr_producto(numerosComoArray[1]);
//				    		}
//				    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final10"  );
//				    		
//				    		
//				    		
//				    		if(arrayDatosExcelDetalle.get(24) != null){
//				    			//tipo estudio
//				    			String[] numerosComoArray = arrayDatosExcelDetalle.get(24).split(" ");
//				    			det.setTipo_estudio(numerosComoArray[0]);
//				    		}
//				    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final11"  );
//				    		
//				    		if(arrayDatosExcelDetalle.get(25) != null){
//				    			//Geo
//				    			String[] numerosComoArray = arrayDatosExcelDetalle.get(25).split(" ");
//				    			det.setId_geografia(numerosComoArray[0]);
//				    		}
//				    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final12"  );
//				    		
//				    		if(arrayDatosExcelDetalle.get(26) != null){
//				    			//Digital
//				    			String[] numerosComoArray = arrayDatosExcelDetalle.get(26).split(" ");
//				    			det.setDigital_op(numerosComoArray[0]);
//				    		}
//				    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final13"  );
//				    		
//				    		if(arrayDatosExcelDetalle.get(27) != null){
//				    			//Digital
//				    			String[] numerosComoArray = arrayDatosExcelDetalle.get(27).split(" ");
//				    			det.setDigital_op(numerosComoArray[0]);
//				    		}
//				    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final14"  );
//				    		
//				    		
//				    		if(arrayDatosExcelDetalle.get(30) != null){
//				    			//Tipo entrevista
//				    			est.setId_tipo_entrevista(combo.getValueComboSelected(arrayDatosExcelDetalle.get(30)));
//				    		}
//				    		
//				    		 logger.info("VALOERES0: &&&&&&&&&&&   final15"  );
//				    		
//				    		
//				    		det.setId_clie_facturar(cliente.getIdClienteByName(arrayDatosExcelDetalle.get(5)));
//				    		det.setPrecio_venta(Float.parseFloat(arrayDatosExcelDetalle.get(6)));
//				    		
//				    		logger.info("VALOERES0: &&&&&&&&&&&   final16"  );
//				    		
//				    		
//				    		
//				    		ArrayList<ObjEstudioProducto> lprod = new ArrayList<ObjEstudioProducto>();
//	 			    		lprod.add(prod);
//				    		
//	 			    		logger.info("VALOERES0: &&&&&&&&&&&   final17"  );
//				    		
//				    		est.setDetalle(det);
//				    		est.setProductos(lprod);
				    		
				    		
				           String cod_cotizacion = arrayDatosExcelDetalle.get(1) + arrayDatosExcelDetalle.get(2);
				           String nombre_cotizacion = arrayDatosExcelDetalle.get(3);
				           String f_creacion= "";
				           
				           if(arrayDatosExcelDetalle.get(7) != null){
			    			  Date date = formatter.parse(arrayDatosExcelDetalle.get(7));
							  f_creacion = formatter1.format(date);
				    	   }
				           
				         
				           
				           
				    	   int id_cotizacion=0;
				    	   int id_manager_principal=0;
				    	   int id_manager_medicion=0;
				    	   String Ddate_pres_clie_op = "";
				    	   String Date_prob_entre_est_op = "";
				    	   
				    	
				    	   
				    	   if(arrayDatosExcelDetalle.get(8) != null){
				    			Date date1 = formatter.parse(arrayDatosExcelDetalle.get(8));
				    			Date_prob_entre_est_op =  formatter1.format(date1);
				    			det.setDate_prob_entre_est_op(Date_prob_entre_est_op);
				    		}
				    	   
				    	   if(arrayDatosExcelDetalle.get(9) != ""){
				    			Date date2 = formatter.parse(arrayDatosExcelDetalle.get(9));
				    			Ddate_pres_clie_op =  formatter1.format(date2);
				    			det.setDdate_pres_clie_op(Ddate_pres_clie_op);
				    		}
				    	   
				    	   
				    	   
				    	   id_cotizacion = estudio.setCotizacion(cod_cotizacion, nombre_cotizacion);	
				    	   id_manager_principal = estudio.setPrincipal(f_creacion, nombre_cotizacion);	
				    	  
				    	   
				    	   est.setId_cotizacion(id_cotizacion);
				    	   est.setId_proyectom(id_manager_principal);
				    	   est.setCanal_venta(1);
				    	   est.setCod_sam(arrayDatosExcelDetalle.get(1));
        			       est.setTipo_sam(arrayDatosExcelDetalle.get(2));
        			       est.setCod_operacion(0);
        			       est.setCodigo_cotizacion("1");
        			       est.setUid_operacion(cod_cotizacion);
        			       est.setId_cliente(cliente.getIdClienteByName(arrayDatosExcelDetalle.get(4)));
        			       est.setArea_medicion(0);
        			       if(arrayDatosExcelDetalle.get(21) != null){
				    			String aux_sector =  arrayDatosExcelDetalle.get(21);
				    			int aux = 0;
				    			if(aux_sector.equals("CE")){
				    				aux = 1;
				    			}else if(aux_sector.equals("CC")){
				    				aux = 2;
				    			}
				    			
				    			est.setSector_medicion(aux);
				    		}
        			       est.setIndustria_medicion(148);
        			       est.setId_tipo_entrevista(combo.getValueComboSelected(arrayDatosExcelDetalle.get(30)));
        			       est.setNombre_operacion(nombre_cotizacion);
        			       est.setEstado_medicion(10);
        			       est.setOrden_medicion(1);
        			       est.setCola_operacion(1);
        			       est.setPriori_operacion(100);
        			       est.setActiva_operacion(0);
        			       est.setFcreacion_proyectom(f_creacion);
        			       est.setScreacion_proyectom(2);/////////
        			       est.setFmod_proyectom(f_creacion);
        			       est.setSmod_proyectom(2);
        			       est.setElimina_proyectom(0);

        			       id_manager_medicion = estudio.setOperacion(est);	
        			       
        			       estudio.setManager(id_manager_medicion, user.getId_user(), 0, 0,Date_prob_entre_est_op,Ddate_pres_clie_op);

						   estudio.setDigitacion(id_manager_medicion);
						   
						   estudio.setTabulacion(id_manager_medicion);
						   
						   estudio.setAnalisis(id_manager_medicion);
						   
						   estudio.setbussness_case(id_manager_medicion);
						 
					 //}
					 
				 }
				 

			    //for (String[] next : arrayDatosExcel) {
			    	
			    	//logger.info("Ingresa setSamEstudios array111: " + arrayDatosExcel.size() );
			    	
			    	//logger.info("Array Row: " + r++ + " -> ");
			    	
//			    	if(r >= 2){
//			    		
//			    		ObjEstudio est = new ObjEstudio();
//			    		ObjEstudioDetalle det = new ObjEstudioDetalle();
//			    		ObjEstudioProducto prod = new ObjEstudioProducto();
//			    		
//			    		est.setCod_sam(next[1]);
//			    		est.setTipo_sam(next[2]);
//			    		est.setNombre_operacion(next[3]);
//			    		est.setId_cliente(cliente.getIdClienteByName(next[4]));
//			    		
//			    		est.setCola_operacion(1);
//			    		
//			    		
//			    		if(next[7] != null){
//			    		
//			    			String aux_dateini =  format3.format(next[7]);
//			    			est.setFcreacion_proyectom(aux_dateini);
//			    		}
//			    		
//			    			est.setScreacion_proyectom(3); //Creacion automatica por workflow
//			    		
//			    		if(next[8] != null){
//			    			String aux_dateE =  format3.format(next[8]);
//			    			det.setDate_prob_entre_est_op(aux_dateE);
//			    		}
//			    		
//			    		
//			    		if(next[9] != null){
//			    			String aux_dateC =  format3.format(next[9]);
//			    			det.setDdate_pres_clie_op(aux_dateC);
//			    		}
//			    		
//			    		if(next[10] != null){
//			    			String aux_dateEn =  format3.format(next[10]);
//			    			est.setFcom_entrega(aux_dateEn);
//			    		}
//			    		
//			    		if(next[10] != null){
//			    			String aux_dateEn =  format3.format(next[10]);
//			    			est.setFcom_entrega(aux_dateEn);
//			    		}
//			    		
//			    		if(next[11] != null){
//			    			String aux_dateEn2 =  format3.format(next[11]);
//			    			est.setFcom_ini_campo(aux_dateEn2);
//			    		}
//			    		
//			    		
//			    		if(next[21] != null){
//			    			String aux_sector =  format3.format(next[21]);
//			    			int aux = 0;
//			    			if(aux_sector.equals("CE")){
//			    				aux = 1;
//			    			}else if(aux_sector.equals("CC")){
//			    				aux = 2;
//			    			}
//			    			
//			    			est.setSector_medicion(aux);
//			    		}
//			    		
//			    		if(next[23] != null){
//			    			//Producto
//			    			String[] numerosComoArray = next[23].split(" ");
//			    			prod.setId_producto(Integer.parseInt(numerosComoArray[0]));
//			    			prod.setStr_producto(numerosComoArray[1]);
//			    		}
//			    		
//			    		if(next[24] != null){
//			    			//tipo estudio
//			    			String[] numerosComoArray = next[24].split(" ");
//			    			det.setTipo_estudio(numerosComoArray[0]);
//			    		}
//			    		
//			    		if(next[25] != null){
//			    			//Geo
//			    			String[] numerosComoArray = next[25].split(" ");
//			    			det.setId_geografia(numerosComoArray[0]);
//			    		}
//			    		
//			    		if(next[26] != null){
//			    			//Digital
//			    			String[] numerosComoArray = next[26].split(" ");
//			    			det.setDigital_op(numerosComoArray[0]);
//			    		}
//			    		
//			    		if(next[27] != null){
//			    			//Digital
//			    			String[] numerosComoArray = next[27].split(" ");
//			    			det.setDigital_op(numerosComoArray[0]);
//			    		}
//			    		
//			    		if(next[30] != null){
//			    			//Tipo entrevista
//			    			est.setId_tipo_entrevista(combo.getValueComboSelected(next[30]));
//			    		}
//			    		
//			    		det.setId_clie_facturar(cliente.getIdClienteByName(next[5]));
//			    		det.setPrecio_venta(Float.parseFloat(next[6]));
//			    		
//			    		
//			    		ArrayList<ObjEstudioProducto> lprod = new ArrayList<ObjEstudioProducto>();
// 			    		lprod.add(prod);
//			    		
// 			    		
//			    		
//			    		est.setDetalle(det);
//			    		est.setProductos(lprod);
//			    		
	//	    		ObjResultCreaCotOp res = estudio.setOperacion(est);	
//			    		
//			    	}
			    	
			    	//r = r + 1;
			    	
			    //}
			    
			 } catch (Exception e) {
				 
				 logger.info("Ingresa setSamEstudios array22: " + e );
				 
			} 
			    
			    
			    
		}


}
