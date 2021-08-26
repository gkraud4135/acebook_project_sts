package setup;

import com.lec206.acebook.util.BusinessResult;

//@Service
public class ManageSetup관리자 implements IManageSetup관리 {

	//@Autowired ISetupDAO setupDAO;
	
	@Override
	public BusinessResult setup() {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부()
		
		return new BusinessResult();

	}

	@Override
	public BusinessResult setup(Object obj) {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부()
		
		return new BusinessResult(obj);

	}

	@Override
	public BusinessResult hashmap(Object obj) {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부()
		
		return new BusinessResult();
	}

}