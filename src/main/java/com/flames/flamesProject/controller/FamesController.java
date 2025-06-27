package com.flames.flamesProject.controller;

import com.flames.flamesProject.model.FlamesRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
class FlamesController {

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("flamesRequest", new FlamesRequest());
        return "index";
    }

    @PostMapping("/result")
    public String getResult(@ModelAttribute FlamesRequest flamesRequest, Model model) {
        String name1 = flamesRequest.getName1().toLowerCase().replace(" ", "");
        String name2 = flamesRequest.getName2().toLowerCase().replace(" ", "");

        model.addAttribute("flamesRequest", flamesRequest);


        if (name1.equals(name2)) {
            model.addAttribute("result", "Both names are the same! Please enter different names.");
            model.addAttribute("bgImage", "valentine.jpg");
            return "index";
        }

        int count = countUniqueChars(name1, name2);
        if (count == 0) {
            model.addAttribute("result", "You are made for each other Because you both have Same Letters ! ");
            model.addAttribute("bgImage", "valentine.jpg");
            return "index";
        }

        String result = getFlamesResult(count);
        model.addAttribute("result", result);
        model.addAttribute("bgImage", result.toLowerCase() + ".jpg");
        return "index";
    }

    public int countUniqueChars(String s1, String s2) {
        String specialChars = "!@#$%^&*()| ";
        Map<Character, Integer> map = new HashMap<>();

        for (char ch : s1.toCharArray()) {
            if (specialChars.indexOf(ch) == -1) {
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }
        }

        for (char ch : s2.toCharArray()) {
            if (specialChars.indexOf(ch) == -1) {
                map.put(ch, map.getOrDefault(ch, 0) - 1);
            }
        }

        int count = 0;

        for (int val : map.values()) {
            count += Math.abs(val);
        }

        return count;
    }

    public String getFlamesResult(int count) {
        List<Character> flames = new ArrayList<>(Arrays.asList('F', 'L', 'A', 'M', 'E', 'S'));
        int index = 0;
        while (flames.size() > 1) {
            index = (index + count - 1) % flames.size();
            flames.remove(index);
        }

        switch (flames.get(0)) {
            case 'F': return "Friends";
            case 'L': return "Love";
            case 'A': return "Affection";
            case 'M': return "Marriage";
            case 'E': return "Enemy";
            case 'S': return "Siblings";
            default: return "Unknown";
        }
    }
}