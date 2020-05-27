package qiangyt.springboot_example.server.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import qiangyt.springboot_example.api.OrderAPI;
import qiangyt.springboot_example.api.rnr.CreateOrderReq;
import qiangyt.springboot_example.api.vo.Account;
import qiangyt.springboot_example.api.vo.Order;
import qiangyt.springboot_example.api.vo.OrderDetail;
import qiangyt.springboot_example.api.vo.Product;
import qiangyt.springboot_example.common.json.JsonHelper;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.UUID;

@ContextConfiguration(classes={OrderRestController.class, SecurityTestConfiguration.class})
@WebMvcTest(OrderRestController.class)
public class OrderRestControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	OrderAPI orderAPI;

	@Test
	@WithMockUser(roles="customer")
	public void createOrder() throws Exception {

		CreateOrderReq createOrder = new CreateOrderReq();
		createOrder.setAmount(2);
		createOrder.setCustomerAccountId(UUID.randomUUID());
		createOrder.setProductId(UUID.randomUUID());

		Order order = new Order();
		order.setAmount(createOrder.getAmount());
		order.setCustomerName("n");
		order.setId(UUID.randomUUID());
		order.setProductName("p");

		given(this.orderAPI.createOrder(createOrder)).willReturn(order);

		MockHttpServletRequestBuilder reqBuilder = post("/api/orders")
						.content(JsonHelper.to(createOrder))
						.contentType(MediaType.APPLICATION_JSON_UTF8);

		mvc.perform(reqBuilder)
			.andExpect(status().isCreated())
			.andExpect(content().json(JsonHelper.to(order)));
	}


	@Test
	public void findOrdersByCustomerAccountId() throws Exception {
		UUID customerAccountId = UUID.randomUUID();

		Order order = new Order();
		order.setAmount(2);
		order.setCustomerName("n");
		order.setId(UUID.randomUUID());
		order.setProductName("p");

		Order[] orders = new Order[]{order};

		given(this.orderAPI.findOrdersByCustomerAccountId(customerAccountId)).willReturn(orders);

		mvc.perform(get("/api/orders/query/byCustomerAccountId?customerAccountId={customerAccountId}", customerAccountId))
            .andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().json(JsonHelper.to(Arrays.asList(order))));
	}


	@Test
	public void deleteOrder() throws Exception {
		UUID orderId = UUID.randomUUID();

		Mockito.doNothing().when(this.orderAPI).deleteOrder(orderId);

		mvc.perform(delete("/api/orders/{orderId}", orderId))
            .andExpect(status().isNoContent());
	}

	@Test
	public void getOrder() throws Exception {
		UUID orderId = UUID.randomUUID();

		Order order = new Order();
		order.setAmount(6);
		order.setCustomerName("n");
		order.setId(orderId);
		order.setProductName("p");

		given(this.orderAPI.getOrder(orderId)).willReturn(order);

		MockHttpServletRequestBuilder reqBuilder = get("/api/orders/{orderId}", orderId);

		mvc.perform(reqBuilder)
			.andExpect(status().isOk())
			.andExpect(content().json(JsonHelper.to(order)));
	}

	@Test
	public void getOrderDetail() throws Exception {
		UUID orderId = UUID.randomUUID();

		Account customerAccount = new Account();
		customerAccount.setId(UUID.randomUUID());
		customerAccount.setAddress("a");
		customerAccount.setFirstName("f");
		customerAccount.setSecondName("s");

		Product product = new Product();
		product.setId(UUID.randomUUID());
		product.setAmount(8);
		product.setName("p");

		OrderDetail order = new OrderDetail();
		order.setAmount(6);
		order.setId(orderId);
		order.setCustomerAccount(customerAccount);
		order.setProduct(product);

		given(this.orderAPI.getOrderDetail(orderId)).willReturn(order);

		MockHttpServletRequestBuilder reqBuilder = get("/api/orders/detail/{orderId}", orderId);

		mvc.perform(reqBuilder)
			.andExpect(status().isOk())
			.andExpect(content().json(JsonHelper.to(order)));
	}

	@Test
	public void findAllOrders() throws Exception {
		Order order1 = new Order();
		order1.setAmount(1);
		order1.setCustomerName("n1");
		order1.setId(UUID.randomUUID());
		order1.setProductName("p1");

		Order order2 = new Order();
		order2.setAmount(2);
		order2.setCustomerName("n2");
		order2.setId(UUID.randomUUID());
		order2.setProductName("p2");

		Order[] orders = new Order[]{order1, order2};

		given(this.orderAPI.findAllOrders()).willReturn(orders);

		MockHttpServletRequestBuilder reqBuilder = get("/api/orders/all");

		mvc.perform(reqBuilder)
			.andExpect(status().isOk())
			.andExpect(content().json(JsonHelper.to(orders)));
	}

}
