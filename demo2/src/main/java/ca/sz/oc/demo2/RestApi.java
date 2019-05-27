package ca.sz.oc.demo2;

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
@RequestMapping(value = "/hello2")
public class RestApi {

    @ApiOperation(value="get response",response=String.class)
    @ApiResponses(value={
        @ApiResponse(code=200,message="good",response=String.class),
        @ApiResponse(code=500,message="Internal Server Error"),
        @ApiResponse(code=404,message="not found")
    })

    @GetMapping(value = "/world2") //produces="application/text" ==> triggers download of text file
    public String helloWorld() {
        return "hello2 world2 spring boot";
    }

    // @GetMapping("/crud")
    // public ResourceSupport index() {
    //     ResourceSupport index = new ResourceSupport();
    //     index.add(linkTo(CrudController.class).withRel("crud"));
    //     return index;
    // }
}