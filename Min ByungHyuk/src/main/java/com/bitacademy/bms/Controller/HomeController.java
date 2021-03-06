package com.bitacademy.bms.Controller;


import com.bitacademy.bms.Service.Stock.StockSerivce;
import com.bitacademy.bms.model.CompletionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Date;
import java.util.List;


@Controller
@CrossOrigin("*")
public class HomeController {
    @Autowired
    private StockSerivce stockSerivce;

    /**
     * MainPage
     */
    @GetMapping(value = "/")
    public String index(Model model) {

        List<CompletionEntity> completionEntityList = stockSerivce.getHomeList();
        model.addAttribute("list", completionEntityList);
        return "index";
    }
    /**
     * View More page
     */
    @GetMapping(value = "/view")
    public String view(Model model) {

        List<CompletionEntity> completionEntityList = stockSerivce.getFullList();

        model.addAttribute("list", completionEntityList);

        return "view";
    }

    /**
     * 발표용 Page
     */
    @GetMapping(value = "/devpro")
    public String devProcess() {


        return "devprocess";
    }
    /**
     *  graph Page
     *  Model -> 사용자가 클릭한 주식
     *  similarList 사용자가 클릭한 주식과 유사한  리스트
     */
    @GetMapping(value = "/get")
    public String get(@RequestParam(value = "code", required = false) int code, Model model) {

        List<CompletionEntity> completionEntityList= stockSerivce.getFullList();
        CompletionEntity searchNameModel =  stockSerivce.findCompletionEntityByComCode(code,completionEntityList);
        String predictDate = stockSerivce.getPredictDay(searchNameModel.getTod());
        List<CompletionEntity> SimilarPlusList = stockSerivce.getSimilarList(searchNameModel.getCom_name());
        model.addAttribute("model", searchNameModel);
        model.addAttribute("predictDate",predictDate);
        model.addAttribute("list", SimilarPlusList);

        return "get";
    }



}
