package com.example.batchprocessing.itemProcessors;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContextHolder {

    public List<String> context = new ArrayList<>();

}