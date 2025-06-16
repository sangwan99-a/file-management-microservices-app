package com.example.search.service;

import org.springframework.stereotype.Service;

@Service
public class SearchService {

    public String search(String name, String dateFrom, String dateTo, Long minSize, Long maxSize) {
        // Dummy implementation, replace with actual DB search logic
        return "Searching files with filters - Name: " + name + ", Date: " + dateFrom + " to " + dateTo + ", Size: " + minSize + "-" + maxSize;
    }
}