package setup;

import org.springframework.web.servlet.ModelAndView;

//@Controller
public class controller_setup{

//@Autowired ISetup관리 setup관리자;
	
	//@RequestMapping(value="setup", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView requestMapping() {
			
			//1.요청페이지(.jsp)
			ModelAndView mv = new ModelAndView();
			
			//2.업무
				
				//BusinessResult br = 
				
				//비정상코드입력시
				//if(br.getCode()!=ERRORCODE.NOMAL) {}
				System.out.println("RequestMapping");
									
			//3.경로지정
			
			mv.setViewName("");
			//mv.addObject("key", value);
			//System.out.println(mv.getViewName()+"으로 이동중");
			
		return mv;
		
	}


	//@RequestMapping(value="setupGET", method = RequestMethod.GET)
	public String getSetupurl() {
			
			//1.요청페이지(.jsp)
			
			//2.업무
				
				//BusinessResult br = 
				
				//비정상코드입력시
				//if(br.getCode()!=ERRORCODE.NOMAL) {}
				System.out.println("get");
									
			//3.경로지정

			//System.out.println("viewname 으로 이동중");
			
		return "setupurl";
		
	}

	//@RequestMapping(value="setupPOST", method = RequestMethod.POST)
	public ModelAndView postSetupurl() {
			
			//1.요청페이지(.jsp)
			ModelAndView mv = new ModelAndView();
			
			//2.업무
				
				//BusinessResult br = 
				
				//비정상코드입력시
				//if(br.getCode()!=ERRORCODE.NOMAL) {}
				System.out.println("post");
									
			//3.경로지정
			
			mv.setViewName("setupurl");
			//mv.addObject("key", value);
			//System.out.println(mv.getViewName()+"으로 이동중");
			
		return mv;
		
	}

}