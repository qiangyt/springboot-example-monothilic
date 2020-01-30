package qiangyt.springboot_example.server.controller;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


@WebMvcTest(OrderRestControllerTeat.class)
public class OrderRestControllerTeat {

	//@MockBean
	//protected OperatorAPI API;

	@Test
	public void submitFunc_happy() throws Exception {
		/*var resp = new Func();

		var req = new CreateFuncReq();ß

		when(this.API.submitFunc(req)).thenReturn(resp);

		post("/api/v1/operator/" + Paths.submitFunc).perform(req).expectOk().expectContent(resp);*/
	}

}
