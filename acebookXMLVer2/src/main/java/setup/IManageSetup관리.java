package setup;

import com.lec206.acebook.util.BusinessResult;

public interface IManageSetup관리 {
	
	//return : notting
	BusinessResult setup();

	//retrun : anyObject
	BusinessResult setup(Object obj);
	
	//retrun : Class : ({key : value},{key : value})
	BusinessResult hashmap(Object obj);
	

}
