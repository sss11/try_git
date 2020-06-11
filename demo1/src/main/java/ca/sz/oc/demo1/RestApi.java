package ca.sz.oc.demo1;

// import org.springframework.hateoas.ResourceSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.client.RestTemplate;

// import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@Api(value="/hello",description="helloworld",produces ="application/text")
@RequestMapping(value = "/hello")
public class RestApi {

    @Value("${hello.world.from}")
    String from;

    @Value("${hello.world.from2}")
    String from2;

    @ApiOperation(value="get response",response=String.class)
    @ApiResponses(value={
        @ApiResponse(code=200,message="good",response=String.class),
        @ApiResponse(code=500,message="Internal Server Error"),
        @ApiResponse(code=404,message="not found")
    })

    @GetMapping(value = "/world") //produces="application/text" ==> triggers download of text file
    public String helloWorld() {
        return "hello world spring boot";
    }

    @GetMapping(value = "/world/from") //produces="application/text" ==> triggers download of text file
    public String helloWorldfrom() {
        String ret = "hello world spring boot ... from:" + from;

        if (!from2.equalsIgnoreCase("NONE"))
            ret += "        |       " + from2;

        return ret;
    }

    @GetMapping(value = "/world/from/content") //produces="application/text" ==> triggers download of text file
    public String helloWorldfromcontent() {
        RestTemplate restTemplate = new RestTemplate();

        String content = restTemplate.getForObject(from, String.class);
        String ret = "hello world spring boot ... from:" + from + " content:" + content;

        if (!from2.equalsIgnoreCase("NONE")) {
            String content2 = restTemplate.getForObject(from2, String.class);
            ret += "        |       " + from2  + " content:" + content2;
        }
        return ret;
    }

    // @GetMapping("/crud")
    // public ResourceSupport index() {
    //     ResourceSupport index = new ResourceSupport();
    //     index.add(linkTo(CrudController.class).withRel("crud"));
    //     return index;
    // }
}