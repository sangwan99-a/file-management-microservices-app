package com.example.search.controller;

import com.example.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public String search(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String dateFrom,
                         @RequestParam(required = false) String dateTo,
                         @RequestParam(required = false) Long minSize,
                         @RequestParam(required = false) Long maxSize) {
        return searchService.search(name, dateFrom, dateTo, minSize, maxSize);
    }
}