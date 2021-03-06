package cl.nexo.manager.controller.proyecto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cl.nexo.manager.access.general.tools.AccessGeneralTools;
import cl.nexo.manager.access.login.AccessPerfil;
import cl.nexo.manager.access.login.LoginAccess;
import cl.nexo.manager.access.menu.AccessMenu;
import cl.nexo.manager.obj.login.ObjLoginUser;
import cl.nexo.manager.obj.login.ObjPerfilLogin;
import cl.nexo.manager.obj.menu.ObjMenuManager;
import cl.nexo.manager.obj.tools.ObjConfigTools;


@Controller
@RequestMapping("/cotizacionService")
public class CotizacionController {
	private static final Logger logger = Logger.getLogger(CotizacionController.class);
	DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
	DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
	DateFormat format3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	@RequestMapping(value = { "/Mantenedor" }, method = RequestMethod.GET)
	public ModelAndView  manProy() {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		LoginAccess logins = (LoginAccess) context.getBean("LoginAccess");
		AccessPerfil perfils = (AccessPerfil) context.getBean("AccessPerfil");
		AccessMenu menus = (AccessMenu) context.getBean("AccessMenu");
		AccessGeneralTools tools = (AccessGeneralTools) context.getBean("AccessGeneralTools");
		
		int fechaAhora = Integer.parseInt(format1.format(new Date()));
		String horaAhora = format2.format(new Date());
		SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    ObjLoginUser user = logins.getUserByLogin(authentication.getName()); 
	    ObjPerfilLogin perfil = perfils.getPerfilById(user.getId_perfil());
	    int permisoModulo = perfils.getPermisoPerfilModulo(perfil.getId_perfil(), 3);
	    ObjMenuManager menu = menus.getMenuSistema(1, user.getId_cliente(), perfil.getId_perfil());
	    ObjConfigTools tol = new ObjConfigTools();
	    
	    tol.setRefresco_table(Integer.parseInt(tools.getValorConfigById(36)));
	    tol.setRefersco_dashboard(Integer.parseInt(tools.getValorConfigById(38)));
	    tol.setTipo_menu(Integer.parseInt(tools.getValorConfigById(37)));
	    tol.setLang(user.getLang_user());
		
		ModelAndView model = new ModelAndView();
		
		if(user.getOtp_user() == 1 && permisoModulo != 0){
			model.addObject("login", user);
		    model.addObject("perfil", perfil);
		    model.addObject("menu", menu);
		    model.addObject("unidad_access", "4");
		    model.addObject("modulo_access", "16");
		    model.addObject("permisoModulo", Integer.toString(permisoModulo));
		    model.addObject("tol",tol);
			
			model.setViewName("/modulos/cotizacion/modulo");
		}else{
	    	
	    	model.setViewName("login");
	    }
		return model;
	
	}
	@RequestMapping(value = { "/New" }, method = RequestMethod.GET)
	public ModelAndView  newProy() {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		LoginAccess logins = (LoginAccess) context.getBean("LoginAccess");
		AccessPerfil perfils = (AccessPerfil) context.getBean("AccessPerfil");
		AccessMenu menus = (AccessMenu) context.getBean("AccessMenu");
		AccessGeneralTools tools = (AccessGeneralTools) context.getBean("AccessGeneralTools");
		
		int fechaAhora = Integer.parseInt(format1.format(new Date()));
		String horaAhora = format2.format(new Date());
		SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    ObjLoginUser user = logins.getUserByLogin(authentication.getName()); 
	    ObjPerfilLogin perfil = perfils.getPerfilById(user.getId_perfil());
	    int permisoModulo = perfils.getPermisoPerfilModulo(perfil.getId_perfil(), 3);
	    ObjMenuManager menu = menus.getMenuSistema(1, user.getId_cliente(), perfil.getId_perfil());
	    ObjConfigTools tol = new ObjConfigTools();
	    
	    tol.setRefresco_table(Integer.parseInt(tools.getValorConfigById(36)));
	    tol.setRefersco_dashboard(Integer.parseInt(tools.getValorConfigById(38)));
	    tol.setTipo_menu(Integer.parseInt(tools.getValorConfigById(37)));
	    tol.setLang(user.getLang_user());
		
		ModelAndView model = new ModelAndView();
		
		if(user.getOtp_user() == 1 && (permisoModulo == 1 || permisoModulo == 2 )){
			model.addObject("login", user);
		    model.addObject("perfil", perfil);
		    model.addObject("menu", menu);
		    model.addObject("unidad_access", "4");
		    model.addObject("modulo_access", "16");
		    model.addObject("lang",user.getLang_user());
		    model.addObject("moneda", tools.getValorConfigById(31));
		    model.addObject("legal_entity", tools.getValorConfigById(32));
		    model.addObject("geografia", tools.getValorConfigById(33));
		    model.addObject("sector", tools.getValorConfigById(34));
		    //model.addObject("title_plataform", tools.getValorConfigById(35));
		    model.addObject("permisoModulo", Integer.toString(permisoModulo));
		    model.addObject("tol",tol);
			model.setViewName("/modulos/cotizacion/nuevo");
		}else{
	    	
	    	model.setViewName("login");
	    }
		return model;
	
	}
}
