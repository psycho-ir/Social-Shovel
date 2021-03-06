package de.zalando.social.shovel.web.controller;

import de.zalando.social.shovel.service.criteria.AggregateCriteria;
import de.zalando.social.shovel.service.messaging.Message;
import de.zalando.social.shovel.service.messaging.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by SOROOSH on 4/3/15.
 */
@Controller
public class HomeController {

//    @Value("${custom.value}")
    private String v;

    @Autowired
    private MessageRepository repository;



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(Model model) {
        System.out.println(v);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("name", "Soroosh");
        return modelAndView;

    }

    @RequestMapping("/h")
    public String another(Model model) {
        TwitterTemplate t = new TwitterTemplate("EYqDP7xAZPvfvh7Jz3vNBqrKe", "mHSTM7DTiSNWJFg3m8hMtDEMQmSXjjU2gp7EC75iqN3oytvDE4");
        SearchResults search = t.searchOperations().search("#zalando");
        System.out.println(search);
        System.out.println("Number of tweets: "+ search.getTweets().size());
        for (Tweet tw :search.getTweets()){
            System.out.println(tw.getText());
        }
        model.addAttribute("name", "hamed");
        return "index";
    }

    @RequestMapping("/socket")
    public String socket(){
        return "socket";
    }

    @RequestMapping(value="/aggregation/{criteria}",method= RequestMethod.GET)
    @ResponseBody
    public Map<String, ?> aggregation(@PathVariable String criteria,HttpServletRequest request, HttpServletResponse response) {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");

        AggregateCriteria crit = AggregateCriteria.valueOf(criteria.toUpperCase());
        return repository.aggrCount(crit);
    }

    @RequestMapping(value="/messages/{limit}",method= RequestMethod.GET)
    @ResponseBody
    public List<Message> Messages(@PathVariable int limit,HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");

        // TODO: refactor this, do not fetch all
        return repository.findAll().subList(0,limit);
    }
}
