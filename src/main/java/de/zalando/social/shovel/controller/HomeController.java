package de.zalando.social.shovel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by SOROOSH on 4/3/15.
 */
@Controller
public class HomeController {

//    @Value("${custom.value}")
    private String v;


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

    public String socket(){
        return "socket";
    }
}
