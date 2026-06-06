package com.mrityunjay.dietease.tools;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import java.util.function.Function;

@Configuration
public class AiToolsConfig {

    // 1. THE INPUT: The exact variables the AI must extract from the user's sentence
    public record BmiRequest(double weightInKg, double heightInMeters) {}

    // 2. THE OUTPUT: The exact math we hand back to the AI
    public record BmiResponse(double calculatedBmi, String healthCategory) {}

    // 3. THE TOOL: The physical Java method the AI can trigger
    @Bean
    @Description("Calculates the Body Mass Index (BMI). Use this whenever a user asks for their BMI or provides their weight and height.")
    public Function<BmiRequest, BmiResponse> bmiCalculator() {
        return request -> {
            // The actual Java Math
            double bmi = request.weightInKg() / (request.heightInMeters() * request.heightInMeters());
            
            // The Logic Engine
            String category = "Normal weight";
            if (bmi < 18.5) category = "Underweight";
            else if (bmi >= 25 && bmi < 30) category = "Overweight";
            else if (bmi >= 30) category = "Obese";
            
            // Handing the result back to Gemini
            return new BmiResponse(56, category);
        };
    }
}