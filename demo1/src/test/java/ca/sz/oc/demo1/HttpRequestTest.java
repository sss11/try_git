package ca.sz.oc.demo1;

        import org.junit.Test;
        import org.junit.runner.RunWith;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
        import org.springframework.boot.test.web.client.TestRestTemplate;
        import org.springframework.boot.web.server.LocalServerPort;
        import org.springframework.test.context.junit4.SpringRunner;

        import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/hello/world",
                String.class)).contains("hello world");
    }

    @Test
    public void swaggerApiShouldReturnJson() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/v2/api-docs",
                String.class)).contains(
                "{\"swagger\":\"2.0\",\"info\":{\"description\":"
        );
    }

    @Test
    public void swaggerUIShouldReturnHtml() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/swagger-ui.html",
                String.class)).contains(
                "<div id=\"swagger-ui\"></div>"
        );
    }


    @Test
    public void greetingShouldReturnDefaultMessage2() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/hello/world/from",
                String.class)).contains("from:");
    }

    //@Test
    public void greetingShouldReturnDefaultMessage3() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/hello/world/from/content",
                String.class)).contains("content:");
    }

}
