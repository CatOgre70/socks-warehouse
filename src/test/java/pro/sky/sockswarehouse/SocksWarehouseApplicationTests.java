package pro.sky.sockswarehouse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pro.sky.sockswarehouse.controller.AuthController;
import pro.sky.sockswarehouse.controller.SocksController;
import pro.sky.sockswarehouse.controller.UserController;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SocksWarehouseApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private SocksController socksController;

    @Autowired
    private AuthController authController;

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
        Assertions.assertThat(socksController).isNotNull();
        Assertions.assertThat(authController).isNotNull();
        Assertions.assertThat(userController).isNotNull();
    }

}
