package run.sage.shark.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面控制器
 *
 * @author Sage
 * @date 2023/02/24
 */
@Controller
public class PageController {

    @GetMapping({"/", "/index.html"})
    public String index() {
        return "index";
    }

}
